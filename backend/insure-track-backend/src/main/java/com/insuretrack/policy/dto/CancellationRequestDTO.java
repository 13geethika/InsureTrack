package com.insuretrack.policy.dto;

import lombok.Data;

@Data
public class CancellationRequestDTO {
    private Long policyId;
    private String reason;
}
