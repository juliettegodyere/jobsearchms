package com.queencoder.jobms.job.external;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private String title;
    private String description;
    private double rating;
}
