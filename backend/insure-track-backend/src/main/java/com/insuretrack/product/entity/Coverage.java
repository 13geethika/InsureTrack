package com.insuretrack.product.entity;

import com.insuretrack.common.enums.CoverageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="coverage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageId;
    @ManyToOne
    @JoinColumn(name="productId",nullable = false)
    private Product product;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoverageType coverageType;
    private Double coverageLimit;
    private Double deductible;

}
