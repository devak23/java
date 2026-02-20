package com.ak.rnd.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class YamlValidatorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File baseDirectory;

    @Parameter(property = "yaml.includes", defaultValue = "**/*.yaml,**/*.yml")
    private String[] includes;

    @Parameter(property = "yaml.excludes", defaultValue = "target/**")
    private String[] excludes;

    @Parameter(property = "yaml.failOnError", defaultValue = "true")
    private boolean failOnError;

    public void execute() throws MojoExecutionException {
        getLog().info("Validating YAML files in: " + baseDirectory.getAbsolutePath());

        List<ValidationError> errors = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(baseDirectory.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(this::isYamlFile)
                    .filter(this::shouldInclude)
                    .forEach(path -> validateFile(path, errors));
        } catch (IOException e) {
            throw new MojoExecutionException("Error scanning directory", e);
        }

        if (!errors.isEmpty()) {
            getLog().error("Found " + errors.size() + " invalid YAML file(s):");
            for (ValidationError error : errors) {
                getLog().error("  " + error.getFile() + ": " + error.getMessage());
            }

            if (failOnError) {
                throw new MojoExecutionException("YAML validation failed");
            }
        } else {
            getLog().info("All YAML files are valid ✓");
        }
    }

    private boolean isYamlFile(Path path) {
        String fileName = path.toString().toLowerCase();
        return fileName.endsWith(".yaml") || fileName.endsWith(".yml");
    }

    private boolean shouldInclude(Path path) {
        String relativePath = baseDirectory.toPath().relativize(path).toString();

        // Check excludes
        for (String exclude : excludes) {
            if (matchesPattern(relativePath, exclude)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesPattern(String path, String pattern) {
        // Simple pattern matching - can be enhanced
        String regex = pattern.replace("**", ".*").replace("*", "[^/]*");
        return path.matches(regex);
    }

    private void validateFile(Path path, List<ValidationError> errors) {
        // Configure stricter validation
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false); // Fail on duplicate keys
        loaderOptions.setMaxAliasesForCollections(50); // Prevent billion laughs attack
        loaderOptions.setAllowRecursiveKeys(false); // Prevent recursive key definitions
        loaderOptions.setProcessComments(true); // Process comments

        Yaml yaml = new Yaml(loaderOptions);

        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            Iterable<Object> documents = yaml.loadAll(fis);

            int docCount = 0;
            for (Object doc : documents) {
                docCount++;

                if (doc == null) {
                    getLog().warn("Empty document in: " + path);
                    continue;
                }

                // Recursively validate the structure
                validateStructure(doc, path.toString(), errors);
            }

            if (docCount == 0) {
                errors.add(new ValidationError(path.toString(), "File is empty"));
            }

            getLog().debug("Valid: " + path + " (" + docCount + " document(s))");

        } catch (ScannerException e) {
            errors.add(new ValidationError(
                    path.toString(),
                    formatScannerError(e)
            ));
        } catch (ParserException e) {
            errors.add(new ValidationError(
                    path.toString(),
                    formatParserError(e)
            ));
        } catch (IOException e) {
            errors.add(new ValidationError(
                    path.toString(),
                    "Cannot read file: " + e.getMessage()
            ));
        } catch (Exception e) {
            errors.add(new ValidationError(
                    path.toString(),
                    "Unexpected error: " + e.getMessage()
            ));
        }
    }

    private void validateStructure(Object obj, String filePath, List<ValidationError> errors) {
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                // Validate keys are not null
                if (entry.getKey() == null) {
                    errors.add(new ValidationError(filePath, "Found null key in map"));
                }
                // Recursively validate nested structures
                if (entry.getValue() != null) {
                    validateStructure(entry.getValue(), filePath, errors);
                }
            }
        } else if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object item : list) {
                if (item != null) {
                    validateStructure(item, filePath, errors);
                }
            }
        }
        // Primitives (String, Number, Boolean) are always valid
    }

    private String formatScannerError(ScannerException e) {
        StringBuilder sb = new StringBuilder("Syntax error");
        if (e.getProblemMark() != null) {
            sb.append(" at line ").append(e.getProblemMark().getLine() + 1);
            sb.append(", column ").append(e.getProblemMark().getColumn() + 1);
        }
        if (e.getProblem() != null) {
            sb.append(": ").append(e.getProblem());
        }
        return sb.toString();
    }

    private String formatParserError(ParserException e) {
        StringBuilder sb = new StringBuilder("Structure error");
        if (e.getProblemMark() != null) {
            sb.append(" at line ").append(e.getProblemMark().getLine() + 1);
            sb.append(", column ").append(e.getProblemMark().getColumn() + 1);
        }
        if (e.getProblem() != null) {
            sb.append(": ").append(e.getProblem());
        }
        return sb.toString();
    }

    private static class ValidationError {
        private final String file;
        private final String message;

        public ValidationError(String file, String message) {
            this.file = file;
            this.message = message;
        }

        public String getFile() { return file; }
        public String getMessage() { return message; }
    }
}