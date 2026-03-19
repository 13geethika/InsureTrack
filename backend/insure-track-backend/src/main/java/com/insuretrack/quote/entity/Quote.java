package com.insuretrack.quote.entity;

import com.insuretrack.common.enums.QuoteStatus;
import com.insuretrack.customer.entity.Customer;
import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="quote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;
    @ManyToOne
    @JoinColumn(name="customerId",nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="productId",nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name="objectId")
    private InsuredObject insuredObject;
    @Column(columnDefinition = "TEXT")
    private String coveragesJSON;
    private Double premium;
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

}
