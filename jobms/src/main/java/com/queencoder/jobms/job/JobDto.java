package com.queencoder.jobms.job;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDto {
    private String title;
    private String jobDescription;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Date posting_date;
    private String salary;
    private String jobType;
    private String jobReference;
    private boolean isFavorite;
    private Long companyId;
}
