package com.ak.rnd.springevents.document.persistence.storage;

import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import com.ak.rnd.springevents.document.persistence.JPADocumentRepository;
import com.ak.rnd.springevents.document.persistence.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.ak.rnd.springevents.document.util.DocumentUtil.convertToDocument;
import static com.ak.rnd.springevents.document.util.DocumentUtil.convertToEntity;

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

    @Override
    public List<DocumentEntity> findAll(Specification<DocumentEntity> spec) {
        // The findAll(spec) method is part of JpaSpecificationExecutor interface which your repository should extend. It accepts a object that defines the criteria for filtering the results.
        // Key points about using Specifications:
        // 1. Specifications are type-safe criteria API for defining queries
        // 2. You can combine multiple specifications using and(), or(), and not() methods
        // 3. Each specification defines a predicate using the JPA Criteria API
        // 4. The specification lambda takes three parameters:
        //    - root: Represents the entity being queried
        //    - query: The query itself
        //    - builder: CriteriaBuilder for creating predicates builder

        return jpaRepository.findAll(spec);
    }
}
