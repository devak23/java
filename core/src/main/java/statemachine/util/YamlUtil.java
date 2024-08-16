package statemachine.util;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;
import yaml.YamlMain;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;

public final class YamlUtil {

    private YamlUtil() {}

    // Load a given Yaml to the desired model object.
    public static <T> T loadYaml(String file, Class<T> clazz) {
        // loading the global Tag
        var loaderOptions = new LoaderOptions();
        TagInspector tagInspector = tag -> tag.getClassName().equalsIgnoreCase(clazz.getName());
        loaderOptions.setTagInspector(tagInspector);
        Constructor yamlConfigConstructor = new Constructor(clazz, loaderOptions);
        Yaml yaml = new Yaml(yamlConfigConstructor);

        URI uri;
        try {
            uri = YamlMain.class.getClassLoader().getResource(file).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream fis = new FileInputStream(uri.getPath())) {
            return yaml.loadAs(fis, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
