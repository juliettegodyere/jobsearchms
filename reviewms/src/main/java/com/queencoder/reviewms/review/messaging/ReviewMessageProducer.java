package com.queencoder.reviewms.review.messaging;

import com.queencoder.reviewms.review.Review;
import com.queencoder.reviewms.review.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Review review){
        ReviewMessage reviewMessage = ReviewMessage.builder()
                .id(review.getId())
                .title(review.getTitle())
                .companyId(review.getCompanyId())
                .description(review.getDescription())
                .rating(review.getRating())
                .build();

        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);

    }
}
