package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.ClaimRequestDTO;
import com.insuretrack.claims.dto.ClaimResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.ClaimAssignment;
import com.insuretrack.claims.entity.Reserve;
import com.insuretrack.claims.entity.Settlement;
import com.insuretrack.claims.repository.ClaimAssignmentRepository;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.ReserveRepository;
import com.insuretrack.claims.repository.SettlementRepository;
import com.insuretrack.common.enums.ClaimStatus;
import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.notification.service.NotificationService;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.PolicyRepository;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final NotificationService notificationService;

    @Override
    public ClaimResponseDTO createClaim(ClaimRequestDTO dto) {

        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Claim claim = Claim.builder()
                .policy(policy)
                .incidentDate(dto.getIncidentDate())
                .reportedDate(LocalDate.now())
                .claimType(dto.getClaimType())
                .description(dto.getDescription())
                .status(ClaimStatus.OPEN)
                .build();

        claimRepository.save(claim);
        notificationService.createNotification(
                policy.getQuote().getCustomer().getCustomerId(),
                "Claim generated for Policy " + policy.getPolicyNumber(),
                NotificationCategory.CLAIM);
        notificationService.createNotification(
                5L,
                "New FNOL filed: " + claim.getClaimId() + ". Needs triage.",
                NotificationCategory.ASSIGNMENT
        );
        return mapToResponse(claim);
    }

    @Override
    public ClaimResponseDTO moveToReview(Long id) {
        Claim claim = getClaimEntity(id);
        validate(claim, ClaimStatus.OPEN);
        claim.setStatus(ClaimStatus.INVESTIGATING);
        return mapToResponse(claim);
    }

    @Override
    public ClaimResponseDTO approveClaim(Long id) {
        Claim claim = getClaimEntity(id);
        validate(claim, ClaimStatus.INVESTIGATING);
        claim.setStatus(ClaimStatus.SETTLED);
        return mapToResponse(claim);
    }

//    @Override
//    public ClaimResponseDTO rejectClaim(Long id) {
//        Claim claim = getClaimEntity(id);
//        validate(claim, ClaimStatus.INVESTIGATING);
//        claim.setStatus(ClaimStatus.DENIED);
//        return mapToResponse(claim);
//    }

    @Override
    public ClaimResponseDTO rejectClaim(Long id) {
        Claim claim = getClaimEntity(id);

        // ALLOW rejection if status is OPEN OR INVESTIGATING
        if (claim.getStatus() != ClaimStatus.OPEN && claim.getStatus() != ClaimStatus.INVESTIGATING) {
            throw new RuntimeException("Invalid status transition: Cannot reject claim in " + claim.getStatus() + " status");
        }

        claim.setStatus(ClaimStatus.DENIED);
        return mapToResponse(claim);
    }

    @Override
    public ClaimResponseDTO closeClaim(Long id) {
        Claim claim = getClaimEntity(id);
        validate(claim, ClaimStatus.SETTLED);
        claim.setStatus(ClaimStatus.CLOSED);
        return mapToResponse(claim);
    }

    @Override
    public ClaimResponseDTO getClaim(Long id) {
        return mapToResponse(getClaimEntity(id));
    }

    @Override
    public List<ClaimResponseDTO> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ClaimResponseDTO> getClaimsByStatus(String status) {
        ClaimStatus claimStatus = ClaimStatus.valueOf(status.toUpperCase());
        return claimRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() == claimStatus)
                .map(this::mapToResponse)
                .toList();
    }


    private Claim getClaimEntity(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    private void validate(Claim claim, ClaimStatus expected) {
        if (claim.getStatus() != expected)
            throw new RuntimeException("Invalid status transition");
    }

    private ClaimResponseDTO mapToResponse(Claim claim) {
        return ClaimResponseDTO.builder()
                .claimId(claim.getClaimId())
                .policyId(claim.getPolicy().getPolicyId())
                .incidentDate(claim.getIncidentDate())
                .reportedDate(claim.getReportedDate())
                .claimType(claim.getClaimType())
                .description(claim.getDescription())
                .status(claim.getStatus().name())
                .build();
    }
}