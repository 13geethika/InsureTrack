package com.insuretrack.billing.entity;

import com.insuretrack.common.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="refund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundId;
    @ManyToOne
    @JoinColumn(name="paymentId")
    private Payment payment;
    private Double amount;
    private LocalDate processedDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private RefundStatus status;
}
