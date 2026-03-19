package com.insuretrack.underwriting.dto;

import com.insuretrack.common.enums.UnderwritingDecision;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingDecisionDTO {
    //private String riskAssessment;
    private String decision;
    private String notes; //only for conditional
}
