package com.insuretrack.claims.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="evidence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evidenceId;
    @ManyToOne
    @JoinColumn(name="claimId")
    private Claim claim ;
    private String type;
    private String uri;
    private LocalDate uploadedDate;

}
