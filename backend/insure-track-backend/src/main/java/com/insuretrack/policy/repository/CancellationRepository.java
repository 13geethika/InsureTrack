package com.insuretrack.policy.repository;

import com.insuretrack.policy.entity.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancellationRepository extends JpaRepository<Cancellation,Long> {
    List<Cancellation> findByPolicyPolicyId(Long policyId);
}
