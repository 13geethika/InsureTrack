package com.insuretrack.reporting.entity;

import com.insuretrack.common.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "regulatoryreport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegulatoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportType;
    private String period;

    private LocalDate generatedDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;
}
