package com.queencoder.jobms.job.mapper;

import com.queencoder.jobms.job.Job;
import com.queencoder.jobms.job.dto.JobDTO;
import com.queencoder.jobms.job.external.Company;
import com.queencoder.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(
            Job job,
            Company company,
            List<Review> review
    ){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setJobType(job.getJobType());
        jobDTO.setJobReference(job.getJobReference());
        jobDTO.setJobDescription(job.getJobDescription());
        jobDTO.setFavorite(job.isFavorite());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setPostingDate(job.getPostingDate());
        jobDTO.setCompany(company);
        jobDTO.setReview(review);

        return jobDTO;
    }
}
