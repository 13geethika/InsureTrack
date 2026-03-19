package com.insuretrack.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratingrule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRule {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    @ManyToOne
    @JoinColumn(name="productId",nullable = false)
    private Product product;
    private String factor;
    private Double weight;
    private String expression;
}
