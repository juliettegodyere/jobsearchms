package com.queencoder.companyms.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ReviewMessage {
    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;
}
