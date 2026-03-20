package com.insuretrack.claims.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="reserve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;
    @ManyToOne
    @JoinColumn(name="claimId")
    private Claim claim;
    private Double amount;
    private LocalDate setDate;
    private LocalDate updatedDate;
    private String status;
}
