package com.insuretrack.policy.repository;

import com.insuretrack.policy.entity.Renewal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalRepository extends JpaRepository<Renewal,Long> {
    List<Renewal> findByPolicyPolicyId(Long policyId);
}
