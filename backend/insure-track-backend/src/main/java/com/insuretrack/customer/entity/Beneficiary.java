package com.insuretrack.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="beneficiary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerId",nullable = false)
    private Customer customer;
    private String name;
    private String relationship;
    private BigDecimal percentageShare;

}
