package com.ak.rnd.springevents.document.service.spec;

import com.ak.rnd.springevents.document.model.DocumentState;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DocumentSpecifications {

    public static Specification<DocumentEntity> hasState(DocumentState state) {
        return (root, query, cb) -> {
            if (state == null) {
                return null;
            }

            return cb.equal(root.get("state"), state);
        };
    }


    public static Specification<DocumentEntity> titleContains(String text) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(text)) {
                return null;
            }

            return cb.like(root.get("title"), "%" + text + "%");
        };
    }

    public static Specification<DocumentEntity> modifiedAfter(LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("lastModified"), date);
        };
    }
}
