package com.insuretrack.claims.repository;

import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.ClaimAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim,Long> {

    //Optional<ClaimAssignment> findByClaimClaimId(Long claimId);
}
