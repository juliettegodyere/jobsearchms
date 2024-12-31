package com.queencoder.jobms.clients;

import com.queencoder.jobms.job.external.Company;
import com.queencoder.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", url="${review-service.url}")
public interface ReviewClient {

    @GetMapping("/api/v1/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
