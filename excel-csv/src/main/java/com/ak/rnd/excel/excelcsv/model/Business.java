package com.ak.rnd.excel.excelcsv.model;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    private String creditCardType;
    private String creditCardNumber;
    private String creditCardExpiry;
}
