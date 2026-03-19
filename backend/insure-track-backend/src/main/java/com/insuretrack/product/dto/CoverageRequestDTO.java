package com.insuretrack.product.dto;

import com.insuretrack.common.enums.CoverageType;
import lombok.Data;

@Data
public class CoverageRequestDTO {
    private CoverageType coverageType;
    private Double coverageLimit;
    private Double deductible;
}
