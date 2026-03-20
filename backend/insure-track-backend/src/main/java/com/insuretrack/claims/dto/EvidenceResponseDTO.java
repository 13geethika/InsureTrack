package com.insuretrack.claims.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceResponseDTO {

    private Long evidenceId;
    private Long claimId;
    private String type;
    private String uri;
    private LocalDate uploadedDate;
}
