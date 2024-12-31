package com.queencoder.companyms.company;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    private Long id;
    private String name;
    private String description;
}

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// class JobDto {
//     private Long id;
//     private String title;
//     private String description;
//     private String minSalary;
//     private String maxSalary;
//     private String location;

//     // Getters and setters
// }
