package com.ak.rnd.springevents.document.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DocumentSearchCriteria {
    private DocumentState state;
    private String searchText;
    private LocalDateTime modifiedAfter;
    private String createdBy;
}
