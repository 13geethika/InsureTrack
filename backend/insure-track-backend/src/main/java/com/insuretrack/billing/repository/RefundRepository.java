package com.insuretrack.billing.repository;

import com.insuretrack.billing.dto.RefundResponseDTO;
import com.insuretrack.billing.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,Long> {
    @Query("SELECT new com.insuretrack.billing.dto.RefundResponseDTO(" +
            "r.refundId, p.paymentId, r.amount, r.processedDate, r.reason, r.status, c.name) " +
            "FROM Refund r " +
            "JOIN r.payment p " +
            "JOIN p.invoice i " +
            "JOIN i.policy pol " +
            "JOIN pol.quote q " +
            "JOIN q.customer c")
    List<RefundResponseDTO> findAllRefundsWithCustomer();



}
