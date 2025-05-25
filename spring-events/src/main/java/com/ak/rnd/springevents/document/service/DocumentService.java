package com.ak.rnd.springevents.document.service;

import com.ak.rnd.springevents.document.exception.DocumentNotFoundException;
import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.DocumentState;
import com.ak.rnd.springevents.document.model.DocumentStateChangeEvent;
import com.ak.rnd.springevents.document.persistence.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void changeDocumentState(String documentId, DocumentState newState) {
        Document document = repository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException(documentId));

        DocumentState oldState = document.getState();
        document.setState(newState);
        document.setLastModified(LocalDateTime.now());

        repository.save(document);

        publisher.publishEvent(new DocumentStateChangeEvent(this, documentId, oldState, newState));
    }
}
