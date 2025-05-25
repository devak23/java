package com.ak.rnd.springevents.document.persistence.storage;

import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import com.ak.rnd.springevents.document.persistence.JPADocumentRepository;
import com.ak.rnd.springevents.document.persistence.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DatabaseStorageStrategy implements StorageStrategy {
    private JPADocumentRepository jpaRepository;

    public DatabaseStorageStrategy(JPADocumentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Document save(Document document) {
        DocumentEntity entity = convertToEntity(document);
        entity = jpaRepository.save(entity);
        return convertToDocument(entity);
    }

    @Override
    public Optional<Document> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Document> findAll() {
        return List.of();
    }

    private DocumentEntity convertToEntity(Document document) {
        return DocumentEntity.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .state(document.getState())
                .lastModified(document.getLastModified())
                .build();
    }

    private Document convertToDocument(DocumentEntity entity) {
        return Document.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .state(entity.getState())
                .lastModified(entity.getLastModified())
                .build();

    }
}
