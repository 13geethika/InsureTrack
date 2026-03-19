package com.insuretrack.policy.repository;

import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.policy.dto.PolicyResponseDTO;
import com.insuretrack.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy,Long> {
    Optional<Policy> findByQuoteQuoteId(Long quoteId);
    List<Policy> findByStatus(PolicyStatus status);

}
