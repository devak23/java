package com.ak.rnd.springevents.document.persistence.storage;

import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import com.ak.rnd.springevents.document.persistence.StorageStrategy;
import com.ak.rnd.springevents.document.service.spec.TitleSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ak.rnd.springevents.document.util.DocumentUtil.convertToEntity;

@Component
@Slf4j
public class FileSystemStorageStrategy implements StorageStrategy {
    private final Path storageLocation;
    private ObjectMapper objectMapper;

    public FileSystemStorageStrategy(@Value("${app.document.storage.location}") String location) {
        this.storageLocation = Paths.get(location);
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        initializeStorage();
    }

    private void initializeStorage() {
        try {
            if (!storageLocation.toFile().exists()) {
                log.info("Creating storage location: {}", storageLocation);
                Files.createDirectories(storageLocation);
            }
        } catch (Exception e) {
            log.error("Error creating storage location: {}", storageLocation, e);
        }
    }

    @Override
    public Document save(Document document) {
        Path filePath = storageLocation.resolve(document.getId() + ".json");
        try {
            objectMapper.writeValue(filePath.toFile(), document);
            log.info("Saved document: {}", document);
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Document> findById(String id) {
        Path filePath = storageLocation.resolve(id + ".json");
        if (Files.exists(filePath)) {
            try {
                Document document = objectMapper.readValue(filePath.toFile(), Document.class);
                log.info("Found document: {}", document);
                return Optional.of(document);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        Path filePath = storageLocation.resolve(id + ".json");
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            log.info("Document {} deleted: {}", id, deleted);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Document> findAll() {
        try {
            return Files.list(storageLocation)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), Document.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DocumentEntity> findAll(Specification<DocumentEntity> spec) {
        try {
            return Files.list(storageLocation)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            Document document = objectMapper.readValue(path.toFile(), Document.class);
                            return convertToEntity(document);
                        } catch (IOException e) {
                            log.error("Error reading file: {}", path, e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(documentEntity -> evaluateSpecification(spec, documentEntity))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean evaluateSpecification(Specification<DocumentEntity> spec, DocumentEntity documentEntity) {
        if (spec == null) {
            return true;
        }

        if (spec instanceof TitleSpecification) {
            String specTitle = ((TitleSpecification) spec).getTitle();
            return documentEntity.getTitle().contains(specTitle);
        }

        return true;
    }
}
