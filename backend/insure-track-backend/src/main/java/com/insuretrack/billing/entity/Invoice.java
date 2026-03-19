package com.insuretrack.billing.entity;

import com.insuretrack.common.enums.InvoiceStatus;
import com.insuretrack.policy.entity.Policy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    @ManyToOne
    @JoinColumn(name="policyId")
    private Policy policy;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
