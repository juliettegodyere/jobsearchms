package com.queencoder.jobms.job.dto;

import com.queencoder.jobms.job.JobTypeEnum;
import com.queencoder.jobms.job.external.Company;
import com.queencoder.jobms.job.external.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private String jobDescription;
    private String minSalary;
    private String maxSalary;
    private String location;
    private boolean isFavorite;
    private Date postingDate;
    private String salary;
    private JobTypeEnum jobType;
    private String jobReference;

    private Company company;
    private List<Review> review;

}
