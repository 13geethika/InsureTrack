package com.insuretrack.claims.repository;

import com.insuretrack.claims.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement,Long> {
    Optional<Settlement> findByClaimClaimId(Long claimId);
}
