package com.ak.rnd.springevents.document.model;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class DocumentStateChangeEvent extends ApplicationEvent {
    private final String documentId;
    private final DocumentState oldState;
    private final DocumentState newState;
    private final LocalDateTime timestamp;

    public DocumentStateChangeEvent(Object source, String documentId, DocumentState oldState, DocumentState newState) {
        super(source);
        this.documentId = documentId;
        this.oldState = oldState;
        this.newState = newState;
        this.timestamp = LocalDateTime.now();
    }

}
