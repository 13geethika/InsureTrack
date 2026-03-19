package com.insuretrack.claims.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDTO {
    private Long assignmentId;
    private Long claimId;
    private Long adjusterId;
    private String priority;
    private LocalDate assignmentDate;
}