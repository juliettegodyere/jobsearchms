package com.queencoder.jobms.job;

import com.queencoder.jobms.job.dto.JobDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/jobs")
@AllArgsConstructor
@Slf4j
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs(){
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@RequestBody Job job, @PathVariable Long id){
        boolean isUpdated = jobService.updateJob(job, id);
        if(isUpdated){
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean isDeleted = jobService.deleteJob(id);
        if(isDeleted){
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponseDto>> getJobsByTitleAndLocation(
        @RequestParam(required = false) String title, @RequestParam(required = false) String location){
        return new ResponseEntity<List<JobResponseDto>>(jobService.getJobsByTitleAndLocation(title, location), HttpStatus.OK);
    }

    // @PostMapping("/uploadCSV")
    // public ResponseEntity<Company> createJobCSV(){
    //     return new ResponseEntity<Company>(companyService.createCompany(companyDto), HttpStatus.CREATED);
    // }
}
