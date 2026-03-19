package com.insuretrack.claims.entity;

import com.insuretrack.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "claimassignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;
    @ManyToOne
    @JoinColumn(name="claimId")
    private Claim claim;
    @ManyToOne
    @JoinColumn(name = "adjusterId")
    private User adjuster;
    private LocalDate assignmentDate;
    private String priority;
}
