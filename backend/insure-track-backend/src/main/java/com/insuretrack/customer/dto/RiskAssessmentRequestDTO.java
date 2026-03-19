package com.insuretrack.customer.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RiskAssessmentRequestDTO {
    public Double valuation;
    public Integer riskScore;
}
