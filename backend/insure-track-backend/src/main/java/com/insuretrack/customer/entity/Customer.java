package com.insuretrack.customer.entity;

import com.insuretrack.common.enums.Status;
import com.insuretrack.common.enums.Segment;
import com.insuretrack.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",nullable = false)
    private User user;
    private String name;
    private LocalDate dob;
    private String contactInfo;
    @Enumerated(EnumType.STRING)
    private Segment segment;
    @Enumerated(EnumType.STRING)
    private Status status;
}
