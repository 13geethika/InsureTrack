//package com.insuretrack.claims.repository;
//
//import com.insuretrack.claims.entity.Evidence;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface EvidenceRepository extends JpaRepository<Evidence,Long> {
//
//}
package com.insuretrack.claims.repository;

import com.insuretrack.claims.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    List<Evidence> findByClaimClaimId(Long claimId);
}