package com.queencoder.reviewms.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;
}
