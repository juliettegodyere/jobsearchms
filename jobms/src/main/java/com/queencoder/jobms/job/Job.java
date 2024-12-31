package com.queencoder.jobms.job;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
@EntityListeners(AuditingEntityListener.class)
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "job_description")
    private String jobDescription;
    @Column(name = "min_salary")
    private String minSalary;
    @Column(name = "max_salary")
    private String maxSalary;
    @Column(name = "location")
    private String location;
    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posting_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date postingDate;

    @Column(name = "salary")
    private String salary;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobTypeEnum jobType;

    @Column(name = "job_reference", nullable = false, unique = true)
    private String jobReference;

    // @JsonIgnoreProperties("jobs") 
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "company_id")
    private Long companyId;
}
