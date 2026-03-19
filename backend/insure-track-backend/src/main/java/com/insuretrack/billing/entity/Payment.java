package com.insuretrack.billing.entity;

import com.insuretrack.common.enums.PaymentMethod;
import com.insuretrack.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long paymentId;
    @ManyToOne
    @JoinColumn(name="invoiceId")
    private Invoice invoice;
    private Double amount;
    private LocalDate paidDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
