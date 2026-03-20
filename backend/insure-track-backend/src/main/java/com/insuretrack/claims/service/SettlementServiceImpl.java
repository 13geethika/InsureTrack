package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.SettlementRequestDTO;
import com.insuretrack.claims.dto.SettlementResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Settlement;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.SettlementRepository;
import com.insuretrack.common.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final ClaimRepository claimRepository;

    @Override
    public SettlementResponseDTO createSettlement(Long claimId, SettlementRequestDTO dto) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() != ClaimStatus.SETTLED)
            throw new RuntimeException("Settlement allowed only after claim is APPROVED/SETTLED");

        Settlement settlement = Settlement.builder()
                .claim(claim)
                .settlementAmount(dto.getSettlementAmount())
                .settlementDate(LocalDate.now())
                .status("PAID")
                .build();

        settlementRepository.save(settlement);

        // FIX: advance status to CLOSED after settlement is created
        claim.setStatus(ClaimStatus.CLOSED);

        return mapToResponse(settlement);
    }

    @Override
    public SettlementResponseDTO getSettlement(Long claimId) {
        Settlement settlement = settlementRepository.findByClaimClaimId(claimId)
                .orElseThrow(() -> new RuntimeException("Settlement not found"));
        return mapToResponse(settlement);
    }

    private SettlementResponseDTO mapToResponse(Settlement settlement) {
        return SettlementResponseDTO.builder()
                .settlementId(settlement.getSettlementId())
                .claimId(settlement.getClaim().getClaimId())
                .settlementAmount(settlement.getSettlementAmount())
                .settlementDate(settlement.getSettlementDate())
                .status(settlement.getStatus())
                .build();
    }
}

//package com.insuretrack.claims.service;
//
//import com.insuretrack.claims.dto.SettlementRequestDTO;
//import com.insuretrack.claims.dto.SettlementResponseDTO;
//import com.insuretrack.claims.entity.Claim;
//import com.insuretrack.claims.entity.Settlement;
//import com.insuretrack.claims.repository.ClaimRepository;
//import com.insuretrack.claims.repository.SettlementRepository;
//import com.insuretrack.common.enums.ClaimStatus;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class SettlementServiceImpl implements SettlementService {
//
//    private final SettlementRepository settlementRepository;
//    private final ClaimRepository claimRepository;
//
//    @Override
//    public SettlementResponseDTO createSettlement(
//            Long claimId,
//            SettlementRequestDTO dto) {
//
//        Claim claim = claimRepository.findById(claimId)
//                .orElseThrow(() -> new RuntimeException("Claim not found"));
//
//        if (claim.getStatus() != ClaimStatus.SETTLED) {
//            throw new RuntimeException(
//                    "Settlement allowed only after APPROVAL");
//        }
//
//        Settlement settlement = Settlement.builder()
//                .claim(claim)
//                .settlementAmount(dto.getSettlementAmount())
//                .settlementDate(LocalDate.now())
//                .status("COMPLETED")
//                .build();
//
//        settlementRepository.save(settlement);
//
//        claim.setStatus(ClaimStatus.SETTLED);
//
//        return mapToResponse(settlement);
//    }
//
//    @Override
//    public SettlementResponseDTO getSettlement(Long claimId) {
//
//        Settlement settlement = settlementRepository.findByClaimClaimId(claimId)
//                .orElseThrow(() -> new RuntimeException("Settlement not found"));
//
//        return mapToResponse(settlement);
//    }
//
//    private SettlementResponseDTO mapToResponse(Settlement settlement) {
//
//        return SettlementResponseDTO.builder()
//                .settlementId(settlement.getSettlementId())
//                .claimId(settlement.getClaim().getClaimId())
//                .settlementAmount(settlement.getSettlementAmount())
//                .settlementDate(settlement.getSettlementDate())
//                .status(settlement.getStatus())
//                .build();
//    }
//}