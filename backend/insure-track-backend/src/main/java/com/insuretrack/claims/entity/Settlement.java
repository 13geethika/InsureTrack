package com.insuretrack.claims.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="settlement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;
    @OneToOne
    @JoinColumn(name="claimId")
    private Claim claim;
    private Double settlementAmount;
    private LocalDate settlementDate;
    private String paymentReference;
    private String status;

}
