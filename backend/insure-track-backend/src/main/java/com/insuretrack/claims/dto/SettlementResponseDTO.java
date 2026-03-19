package com.insuretrack.claims.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementResponseDTO {

    private Long settlementId;
    private Long claimId;
    private Double settlementAmount;
    private LocalDate settlementDate;
    private String status;
}
