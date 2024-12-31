package com.queencoder.jobms.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    /**
     * SELECT * 
        FROM job_search_db.jobs 
        WHERE 
        (title IS NULL OR title LIKE CONCAT('%', 'title', '%')) AND 
        (location IS NULL OR location LIKE CONCAT('%', 'location', '%'));
     */
    @Query(value = "SELECT j.posting_date AS postingDate, " + //
            "    j.job_description AS jobDescription, " + //
            "    j.job_reference AS jobReference, " + //
            "    j.location, " + //
            "    j.max_salary AS maxSalary, " + //
            "    j.min_salary AS minSalary, " + //
            "    j.salary, " + //
            "    j.title, " + //
            "    j.job_type AS jobType, " + //
            "    j.is_favorite AS isFavorite, " + //
            "    c.company_name AS companyName, " + //
            "    c.company_description AS companyDescription " + //
            "FROM " + //
            "    jobs j " + //
            "LEFT JOIN " + //
            "    company c ON c.id = j.company_id " + // Added a space after j.company_id
            "WHERE (:title IS NULL OR j.title LIKE %:title%) " + //
            "AND (:location IS NULL OR j.location LIKE %:location%) " + //
            "ORDER BY " + //
            "    j.posting_date ", nativeQuery = true)
    List<JobResponseDto> findJobsByTitleAndLocation(String title, String location);

    @Query(value = "SELECT j.posting_date AS postingDate, j.job_description AS jobDescription, j.job_reference AS jobReference, j.location, j.max_salary AS maxSalary, j.min_salary AS minSalary, j.salary, j.title, j.job_type AS jobType, j.is_favorite AS isFavorite, c.company_name AS companyName, c.company_description AS companyDescription FROM jobs j LEFT JOIN company c ON c.id = j.company_id ORDER BY j.posting_date", nativeQuery = true)
    List<JobResponseDto> findAllJobs();
    
}
