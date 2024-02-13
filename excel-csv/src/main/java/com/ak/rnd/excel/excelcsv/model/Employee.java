package com.ak.rnd.excel.excelcsv.model;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String firstName;
    private String lastName;
    private String fullName;
    private String bloodGroup;
    private String title;
    private String userName;
}
