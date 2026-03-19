package com.insuretrack.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingRuleResponseDTO {
    private Long ruleId;
    private Long productId;
    private String factor;
    private Double weight;
    private String expression;

}
