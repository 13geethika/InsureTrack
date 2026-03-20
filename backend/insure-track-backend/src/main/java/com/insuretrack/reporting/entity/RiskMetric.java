package com.insuretrack.reporting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "riskmetric")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;
    private String scope;
    @Column(columnDefinition = "TEXT")
    private String metrics;
    private LocalDate asOfDate;

}
