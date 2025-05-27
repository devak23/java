package com.ak.rnd.springevents.document.util;

import com.ak.rnd.springevents.document.model.Document;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;

public final class DocumentUtil {

    private DocumentUtil() {
        // intentional
    }


    public static DocumentEntity convertToEntity(Document document) {
        return DocumentEntity.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .state(document.getState())
                .lastModified(document.getLastModified())
                .build();
    }

    public static Document convertToDocument(DocumentEntity entity) {
        return Document.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .state(entity.getState())
                .lastModified(entity.getLastModified())
                .build();

    }
}
