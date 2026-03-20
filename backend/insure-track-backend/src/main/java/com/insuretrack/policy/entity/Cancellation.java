package com.insuretrack.policy.entity;

import com.insuretrack.common.enums.CancellationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="cancellation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancellationId;
    @ManyToOne
    @JoinColumn(name = "policyId")
    private Policy policy;
    private String reason;
    private LocalDate effectiveDate;
    private Double refundAmount;
    @Enumerated(EnumType.STRING)
    private CancellationStatus status;
}
