package com.insuretrack.policy.entity;

import com.insuretrack.common.enums.RenewalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="renewal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long renewalId;
    @ManyToOne
    @JoinColumn(name = "policyId")
    private Policy policy;
    private Double proposedPremium;
    @Column(columnDefinition = "TEXT")
    private String proposedCoveragesJSON;
    private LocalDate offerDate;
    @Enumerated(EnumType.STRING)
    private RenewalStatus status;
}
