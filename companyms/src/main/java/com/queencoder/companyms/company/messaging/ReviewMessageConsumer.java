package com.queencoder.companyms.company.messaging;

import com.queencoder.companyms.company.CompanyService;
import com.queencoder.companyms.company.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }

}
