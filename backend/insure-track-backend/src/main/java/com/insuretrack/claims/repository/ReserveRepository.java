//package com.insuretrack.claims.repository;
//
//import com.insuretrack.claims.entity.Reserve;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface ReserveRepository extends JpaRepository<Reserve,Long> {
//
//}
package com.insuretrack.claims.repository;

import com.insuretrack.claims.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByClaimClaimId(Long claimId);
}