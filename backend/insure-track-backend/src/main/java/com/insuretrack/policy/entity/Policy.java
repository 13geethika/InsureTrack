package com.insuretrack.policy.entity;

import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.quote.entity.Quote;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;
    @OneToOne
    @JoinColumn(name="quoteId")
    private Quote quote;
    @Column(unique = true)
    private String policyNumber;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Double premium;
    @Enumerated(EnumType.STRING)
    private PolicyStatus status;

}
