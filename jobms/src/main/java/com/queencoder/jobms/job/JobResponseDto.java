package com.queencoder.jobms.job;

import java.util.Date;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
public interface JobResponseDto {
    // private String title;
    // private String jobDescription; // Matches the alias used in the query
    // private String minSalary;
    // private String maxSalary;
    // private String location;
    // private Date postingDate;
    // private String salary;
    // private String jobType;
    // private String jobReference;
    // private boolean isFavorite;
    // private String companyName; // Changed from 'name' to 'companyName' to match the alias used in the query
    // private String companyDescription; // Matches the alias used in the query

    String getTitle();
    String getJobDescription(); 
    String getMinSalary();
    String getMaxSalary();
    String getLocation();
    Date getPostingDate();
    String getSalary();
    String getJobType();
    String getJobReference();
    boolean getIsFavorite();
    String getCompanyName();
    String getCompanyDescription(); 

}
