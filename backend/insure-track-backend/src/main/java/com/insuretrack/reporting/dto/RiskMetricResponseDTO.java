package com.insuretrack.reporting.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RiskMetricResponseDTO {

    private Long metricId;
    private String scope;
    private String metrics; // JSON string
    private LocalDate asOfDate;
}
