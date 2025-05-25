package com.ak.rnd.springevents.document.persistence;

import com.ak.rnd.springevents.document.model.Document;

import java.util.List;
import java.util.Optional;

public interface StorageStrategy {
    Document save(Document document);
    Optional<Document> findById(String id);
    void delete(String id);
    List<Document> findAll();
}
