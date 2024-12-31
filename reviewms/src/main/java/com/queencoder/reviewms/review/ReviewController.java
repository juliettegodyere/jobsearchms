package com.queencoder.reviewms.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import com.queencoder.reviewms.review.messaging.ReviewMessageProducer;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<List<Review>>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId, @RequestBody Review review){
        boolean isReviewSaved = reviewService.addReview(companyId, review);
        if(isReviewSaved){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review
                                                ){
            boolean isReviewUpdated = reviewService.updateReview(reviewId, review);

            if(isReviewUpdated){
                return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Review Not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){

        boolean isDeleted = reviewService.deleteReview(reviewId);

        if(isDeleted){
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);

        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

}


