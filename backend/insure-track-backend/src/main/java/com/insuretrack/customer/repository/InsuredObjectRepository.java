package com.insuretrack.customer.repository;

import com.insuretrack.customer.entity.InsuredObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuredObjectRepository extends JpaRepository<InsuredObject,Long> {
    List<InsuredObject> findByCustomer_CustomerId(Long customerId);
}
