package com.queencoder.companyms.company;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "company",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"company_name"})})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String name;
    @Column(name = "company_description")
    private String companyDescription;
    private Double rating;

}
