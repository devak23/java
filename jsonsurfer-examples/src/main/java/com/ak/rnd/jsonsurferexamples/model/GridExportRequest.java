package com.ak.rnd.jsonsurferexamples.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GridExportRequest {
    protected String requestId;
    protected int timeoutSeconds;
}
