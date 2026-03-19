package com.insuretrack.customer.entity;

import com.insuretrack.common.enums.ObjectType;
import com.insuretrack.common.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="insuredobject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuredObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerId",nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private ObjectType objectType;
    @Column(columnDefinition = "TEXT")
    private String detailsJson;
    private double valuation;
    private Integer riskScore;
    @Enumerated(EnumType.STRING)
    private Status status;


}
