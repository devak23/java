package com.ak.rnd.springevents.document.exception;

public class DocumentNotFoundException extends RuntimeException {
    private String documentId;

    public DocumentNotFoundException(String documentId) {
        this.documentId = documentId;
    }
}
