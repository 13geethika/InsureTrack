package com.insuretrack.policy.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CancellationResponseDTO {
    private Long cancellationId;
    private Long policyId;
    private String reason;
    private LocalDate effectiveDate;
}
