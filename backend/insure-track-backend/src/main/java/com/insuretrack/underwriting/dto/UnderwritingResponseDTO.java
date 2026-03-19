package com.insuretrack.underwriting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingResponseDTO {
    private Long uwCaseId;
    private Long quoteId;
    private Integer riskScore;
    private String decision;
    private String policyType; // <--- This is the field we need for the chart
    private String customerName;
    private Double coverageAmount;
    private LocalDateTime decisionDate;
}