package com.ak.rnd.jsonsurferexamples.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GridExportResponse extends GridExportRequest {
    private String documentId;
    private String message;
    private String status;
}
