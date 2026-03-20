package com.insuretrack.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RatingRuleRequestDTO {
    private String factor;
    private Double weight;
    private String expression;
}
