package com.queencoder.jobms.job.impl;

import com.queencoder.jobms.clients.CompanyClient;
import com.queencoder.jobms.clients.ReviewClient;
import com.queencoder.jobms.job.*;
import com.queencoder.jobms.job.dto.JobDTO;
import com.queencoder.jobms.job.external.Company;

import com.queencoder.jobms.job.external.Review;
import com.queencoder.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImp implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    @Autowired
    RestTemplate restTemplate;

    int attempt = 0;

    @Override
    public void createJob(Job job){
        jobRepository.save(job);
    }

    @Override
//    @CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    //@Retry(name="companyBreaker")
    @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> getAllJobs(){
        System.out.println("Attempt:" +  ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    private JobDTO convertToDto(Job job){
//        Company company = restTemplate.getForObject(
//                "http://COMPANY-SERVICE:8081/api/v1/companies/" + job.getCompanyId(),
//                Company.class);

        Company company = companyClient.getCompany(job.getCompanyId());

//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/api/v1/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {}
//        );
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
//        List<Review> reviews = reviewResponse.getBody();

        return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
    }

    @Override
    public JobDTO getJobById(Long id){
        Job job = jobRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Given id does not exist " + id));
        return convertToDto(job);
    }
    @Override
    public boolean updateJob(Job updatedJob, Long id){
        Job existingjob = jobRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Given id does not exist " + id));
        
        existingjob.setJobDescription(updatedJob.getJobDescription());
        existingjob.setLocation(updatedJob.getLocation());
        existingjob.setMaxSalary(updatedJob.getMaxSalary());
        existingjob.setMinSalary(updatedJob.getMinSalary());
        existingjob.setJobReference(updatedJob.getJobReference());
        existingjob.setJobType(updatedJob.getJobType());
        existingjob.setSalary(updatedJob.getSalary());
        existingjob.setTitle(updatedJob.getTitle());
        existingjob.setFavorite(updatedJob.isFavorite());
        jobRepository.save(existingjob);
        return true;
    }
    @Override
    public boolean deleteJob(Long id){
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<JobResponseDto> getJobsByTitleAndLocation(String title, String location) {
        List<JobResponseDto> jobResponseDto =  jobRepository.findJobsByTitleAndLocation(title, location);
        return jobResponseDto;
       //return jobResponseDto.stream().map((job) -> mapJobToDto(job)).collect(Collectors.toList());
    }

}
