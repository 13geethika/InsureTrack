package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.ReserveRequestDTO;
import com.insuretrack.claims.dto.ReserveResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Reserve;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.ReserveRepository;
import com.insuretrack.common.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final ClaimRepository claimRepository;

    @Override
    public ReserveResponseDTO createReserve(Long claimId, ReserveRequestDTO dto) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() != ClaimStatus.INVESTIGATING)
            throw new RuntimeException("Reserve can only be created in INVESTIGATING stage");

        Reserve reserve = Reserve.builder()
                .claim(claim)
                .amount(dto.getAmount())
                .setDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .status("OPEN")
                .build();

        reserveRepository.save(reserve);
        return mapToResponse(reserve);
    }

    @Override
    public List<ReserveResponseDTO> getReservesByClaim(Long claimId) {
        // FIX: use proper repository query instead of findAll() + filter
        return reserveRepository.findByClaimClaimId(claimId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReserveResponseDTO mapToResponse(Reserve reserve) {
        return ReserveResponseDTO.builder()
                .reserveId(reserve.getReserveId())
                .claimId(reserve.getClaim().getClaimId())
                .amount(reserve.getAmount())
                .status(reserve.getStatus())
                .createdDate(reserve.getSetDate())
                .build();
    }
}

//package com.insuretrack.claims.service;
//
//import com.insuretrack.claims.dto.ReserveRequestDTO;
//import com.insuretrack.claims.dto.ReserveResponseDTO;
//import com.insuretrack.claims.entity.Claim;
//import com.insuretrack.claims.entity.Reserve;
//import com.insuretrack.claims.repository.ClaimRepository;
//import com.insuretrack.claims.repository.ReserveRepository;
//import com.insuretrack.common.enums.ClaimStatus;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class ReserveServiceImpl implements ReserveService {
//
//    private final ReserveRepository reserveRepository;
//    private final ClaimRepository claimRepository;
//
//    @Override
//    public ReserveResponseDTO createReserve(Long claimId,
//                                            ReserveRequestDTO dto) {
//
//        Claim claim = claimRepository.findById(claimId)
//                .orElseThrow(() -> new RuntimeException("Claim not found"));
//
//        if (claim.getStatus() != ClaimStatus.INVESTIGATING) {
//            throw new RuntimeException(
//                    "Reserve allowed only in UNDER_REVIEW stage");
//        }
//
//        Reserve reserve = Reserve.builder()
//                .claim(claim)
//                .amount(dto.getAmount())
//                .setDate(LocalDate.now())
//                .status("OPEN")
//                .build();
//
//        reserveRepository.save(reserve);
//
//        return mapToResponse(reserve);
//    }
//
//    @Override
//    public List<ReserveResponseDTO> getReservesByClaim(Long claimId) {
//
//        return reserveRepository.findAll()
//                .stream()
//                .filter(r -> r.getClaim().getClaimId().equals(claimId))
//                .map(this::mapToResponse)
//                .toList();
//    }
//
//    private ReserveResponseDTO mapToResponse(Reserve reserve) {
//
//        return ReserveResponseDTO.builder()
//                .reserveId(reserve.getReserveId())
//                .claimId(reserve.getClaim().getClaimId())
//                .amount(reserve.getAmount())
//                .status(reserve.getStatus())
//                .createdDate(reserve.getSetDate())
//                .build();
//    }
//}