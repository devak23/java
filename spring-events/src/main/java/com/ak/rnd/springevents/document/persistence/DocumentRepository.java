package com.ak.rnd.springevents.document.persistence;

import com.ak.rnd.springevents.document.model.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class DocumentRepository {
    private final StorageStrategy storageStrategy;

    public DocumentRepository(@Value("${app.document.storage.type}") String storageType,
                              List<StorageStrategy> availalbeStrategies) {

        this.storageStrategy = availalbeStrategies.stream()
                .filter(s -> s.getClass().getSimpleName()
                        .toLowerCase().startsWith(storageType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Storage type: " + storageType + " is not supported"));
    }

    public Document save(Document document) {
        if (document.getId() == null) {
            document.setId(generateId());
        }

        return storageStrategy.save(document);
    }

    public Optional<Document> findById(String id) {
        return storageStrategy.findById(id);
    }

    public void delete(String id) {
        storageStrategy.delete(id);
    }

    public List<Document> findAll() {
        return storageStrategy.findAll();
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
