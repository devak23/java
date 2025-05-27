package com.ak.rnd.springevents.document.service.spec;

import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public class TitleSpecification implements Specification<DocumentEntity> {
    private final String title;

    public TitleSpecification(String title) {
        this.title = title;
    }

    @Override
    public Specification<DocumentEntity> and(Specification<DocumentEntity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<DocumentEntity> or(Specification<DocumentEntity> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<DocumentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null; // not used in the FileSystem implementation
    }
}
