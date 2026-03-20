package com.insuretrack.product.dto;

import com.insuretrack.common.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.swing.plaf.nimbus.State;
import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private Status status;
    private List<CoverageResponseDTO> coverages;
    private List<RatingRuleResponseDTO> ratingRules;
}
