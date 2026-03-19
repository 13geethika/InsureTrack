package com.insuretrack.product.dto;

import com.insuretrack.common.enums.CoverageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoverageResponseDTO {
    private Long coverageId;
    private Long productId;
    private CoverageType coverageType;
    private Double coverageLimit;
    private Double deductible;
}
