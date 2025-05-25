package com.ak.rnd.springevents.document.model.entity;

import com.ak.rnd.springevents.document.model.DocumentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(length = 10000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentState state;
    @Column(nullable = false)
    private LocalDateTime lastModified;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private String lasModifiedBy;
    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.lastModified = created;
    }
    @PreUpdate
    protected void onUpdate() {
        this.lastModified = LocalDateTime.now();
    }
}
