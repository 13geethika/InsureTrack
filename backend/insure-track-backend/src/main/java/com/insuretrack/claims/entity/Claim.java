package com.insuretrack.claims.entity;

import com.insuretrack.common.enums.ClaimStatus;
import com.insuretrack.policy.entity.Policy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="claim")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;
    @ManyToOne
    @JoinColumn(name="policyId",nullable = false)
    private Policy policy;
    private LocalDate incidentDate;
    private LocalDate reportedDate;
    private String claimType;
    private String description;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
}
