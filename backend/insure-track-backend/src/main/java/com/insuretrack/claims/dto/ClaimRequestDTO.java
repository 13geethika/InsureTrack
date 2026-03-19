package com.insuretrack.claims.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClaimRequestDTO {
    private Long policyId;
    private LocalDate incidentDate;
    private String claimType;
    private String description;

}
