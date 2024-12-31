package com.queencoder.reviewms.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    public final ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews(Long companyId) { return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if (companyId != null && review != null) {
            log.info("Before Setting {}", review);
            review.setCompanyId(companyId);
            log.info("After Setting Setting {}", review);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setDescription(updatedReview.getDescription());
            review.setCompanyId(updatedReview.getCompanyId());
            review.setTitle(updatedReview.getTitle());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
                reviewRepository.delete(review);
                return true;

        }
        return false;
    }

}
