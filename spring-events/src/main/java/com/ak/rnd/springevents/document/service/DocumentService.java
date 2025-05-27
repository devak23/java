package com.ak.rnd.springevents.document.service;

import com.ak.rnd.springevents.document.exception.DocumentNotFoundException;
import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.DocumentSearchCriteria;
import com.ak.rnd.springevents.document.model.DocumentState;
import com.ak.rnd.springevents.document.model.DocumentStateChangeEvent;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import com.ak.rnd.springevents.document.persistence.DocumentRepository;
import com.ak.rnd.springevents.document.service.spec.DocumentSpecifications;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DocumentService {
    private final ApplicationEventPublisher publisher;
    private final DocumentRepository repository;

    @Autowired
    public DocumentService(ApplicationEventPublisher publisher, DocumentRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @Transactional
    public void changeDocumentState(String documentId, DocumentState newState) {
        Document document = repository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException(documentId));

        DocumentState oldState = document.getState();
        document.setState(newState);
        document.setLastModified(LocalDateTime.now());

        repository.save(document);

        publisher.publishEvent(new DocumentStateChangeEvent(this, documentId, oldState, newState));
    }

    public List<DocumentEntity> searchDocuments(DocumentSearchCriteria criteria) {
        Specification<DocumentEntity> spec = Specification.where(null);

        if (criteria.getState() != null) {
            spec = spec.and(DocumentSpecifications.hasState(criteria.getState()));
        }

        if (criteria.getSearchText() != null) {
            spec = spec.and(DocumentSpecifications.titleContains(criteria.getSearchText()));
        }

        if (criteria.getModifiedAfter() != null) {
            spec= spec.and(DocumentSpecifications.modifiedAfter(criteria.getModifiedAfter()));
        }

        return repository.findAll(spec);
    }
}
