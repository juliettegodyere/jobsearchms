package com.queencoder.jobms.job;

import com.queencoder.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService{
    public void createJob(Job job);
    public List<JobDTO> getAllJobs();
    public JobDTO getJobById(Long id);
    public boolean updateJob(Job updatedjob, Long id);
    public boolean deleteJob(Long id);
    public List<JobResponseDto> getJobsByTitleAndLocation(String title, String location);
}

