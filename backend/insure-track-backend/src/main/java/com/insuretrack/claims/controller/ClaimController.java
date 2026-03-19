package com.insuretrack.claims.controller;

import com.insuretrack.claims.dto.*;
import com.insuretrack.claims.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjuster/claims")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;
    private final ReserveService reserveService;
    private final SettlementService settlementService;
    private final EvidenceService evidenceService;
    private final AssignmentService assignmentService;

    @PutMapping("/{id}/review")
    public ClaimResponseDTO review(@PathVariable Long id) {
        return claimService.moveToReview(id);
    }

    @PostMapping("/{id}/assign")
    public AssignmentResponseDTO assignAdjuster(
            @PathVariable Long id,
            @RequestBody AssignmentRequestDTO dto) {
        return assignmentService.assignAdjuster(id, dto);
    }

    @PostMapping("/{id}/reserve")
    public ReserveResponseDTO createReserve(
            @PathVariable Long id,
            @RequestBody ReserveRequestDTO dto) {
        return reserveService.createReserve(id, dto);
    }

    @PutMapping("/{id}/approve")
    public ClaimResponseDTO approve(@PathVariable Long id) {
        return claimService.approveClaim(id);
    }

    @PutMapping("/{id}/reject")
    public ClaimResponseDTO reject(@PathVariable Long id) {
        return claimService.rejectClaim(id);
    }

    @GetMapping("/{id}")
    public ClaimResponseDTO get(@PathVariable Long id) {
        return claimService.getClaim(id);
    }

    @PostMapping("/{id}/settlement")
    public SettlementResponseDTO createSettlement(
            @PathVariable Long id,
            @RequestBody SettlementRequestDTO dto) {
        return settlementService.createSettlement(id, dto);
    }

    @GetMapping("/{id}/reserves")
    public List<ReserveResponseDTO> getReserves(@PathVariable Long id) {
        return reserveService.getReservesByClaim(id);
    }

    @GetMapping("/{id}/settlement")
    public SettlementResponseDTO getSettlement(@PathVariable Long id) {
        return settlementService.getSettlement(id);
    }

    @GetMapping("/{id}/evidence")
    public List<EvidenceResponseDTO> getEvidence(@PathVariable Long id) {
        return evidenceService.getEvidenceByClaim(id);
    }

    @GetMapping("/{id}/assignment")
    public AssignmentResponseDTO getAssignment(@PathVariable Long id) {
        return assignmentService.getByClaimId(id);
    }
    @GetMapping
    public List<ClaimResponseDTO> getAll() {
        return claimService.getAllClaims();
    }

    @GetMapping("/status/{status}")
    public List<ClaimResponseDTO> getByStatus(@PathVariable String status) {
        return claimService.getClaimsByStatus(status);
    }

}
