package com.ak.rnd.springevents.document.persistence;

import com.ak.rnd.springevents.document.model.DocumentState;
import com.ak.rnd.springevents.document.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JPADocumentRepository extends JpaRepository<DocumentEntity, String> {

    // Find documents by state
    List<DocumentEntity> findByState(DocumentState state);

    // Find documents modified after a certain date
    List<DocumentEntity> findByLastModifiedAfter(LocalDateTime date);

    // Find documents by title containing text (case-insensitive)
    @Query("SELECT d FROM DocumentEntity d WHERE LOWER(d.title) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<DocumentEntity> findByTitleContainingIgnoreCase(String text);

    // Find documents by state and last modified date
    List<DocumentEntity> findByStateAndLastModifiedAfter(DocumentState state, LocalDateTime date);

    // Count of documents by state
    long countByState(DocumentState state);

    // Custom query to find documents with specific states
    @Query("SELECT d FROM DocumentEntity d WHERE d.state IN :states ORDER BY d.lastModified DESC")
    List<DocumentEntity> findByStateIn(List<DocumentState> states);
}
