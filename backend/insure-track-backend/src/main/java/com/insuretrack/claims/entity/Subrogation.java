package com.insuretrack.claims.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="subrogation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subrogation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subrogationId;
    @OneToOne
    @JoinColumn(name="claimId")
    private Claim claim;
    private String counterParty;
    private Double amountSought;
    private String status;
}
