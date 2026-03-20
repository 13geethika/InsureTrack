package com.insuretrack.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="auditlog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable=false)
    private User user;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
    private String metadata;
}
