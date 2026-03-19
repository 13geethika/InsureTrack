package com.insuretrack.policy.service;

import com.insuretrack.common.enums.ChangeType;
import com.insuretrack.common.enums.EndorsementStatus;
import com.insuretrack.policy.dto.EndorsementRequestDTO;
import com.insuretrack.policy.dto.EndorsementResponseDTO;
import com.insuretrack.policy.entity.Endorsement;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.EndorsementRepository;
import com.insuretrack.policy.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class EndorsementServiceImpl implements EndorsementService {
    private final EndorsementRepository endorsementRepository;
    private final PolicyRepository policyRepository;

    @Override
    public EndorsementResponseDTO create(EndorsementRequestDTO requestDTO) {
        Policy policy = policyRepository.findById(requestDTO.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Endorsement endorsement = Endorsement.builder()
                .policy(policy)
                .changeType(ChangeType.valueOf(requestDTO.getChangeType()))
                .premiumDelta(requestDTO.getPremiumDelta())
                .effectiveDate(LocalDate.now())
                .status(EndorsementStatus.PENDING)
                .build();

        endorsementRepository.save(endorsement);
        return mapToDTO(endorsement);
    }

    @Override
    public List<EndorsementResponseDTO> getAllEndorsements() {
        return endorsementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EndorsementResponseDTO approveEndorsement(Long endorsementId) {
        // 1. Fetch Endorsement with its Policy
        Endorsement endorsement = endorsementRepository.findById(endorsementId)
                .orElseThrow(() -> new RuntimeException("Endorsement not found"));

        // 2. Validate Endorsement Status
        if (endorsement.getStatus() != EndorsementStatus.PENDING) {
            throw new RuntimeException("Only PENDING endorsements can be approved. Current status: " + endorsement.getStatus());
        }

        // 3. Validate Policy Status (Business Rule: Policy must be ACTIVE)
        Policy policy = endorsement.getPolicy();
        if (!"ACTIVE".equalsIgnoreCase(String.valueOf(policy.getStatus()))) {
            throw new RuntimeException("Cannot approve endorsement for a policy that is not ACTIVE.");
        }

        // 4. Update Policy Premium
        double currentPremium = (policy.getPremium() != null) ? policy.getPremium() : 0.0;
        double deltaValue = (endorsement.getPremiumDelta() != null) ? endorsement.getPremiumDelta() : 0.0;

        policy.setPremium(currentPremium + deltaValue);

        // 5. Update Endorsement Status
        endorsement.setStatus(EndorsementStatus.APPROVED);

        // 6. Persist changes (Optional with @Transactional, but good for clarity)
        policyRepository.save(policy);
        endorsementRepository.save(endorsement);

        return mapToDTO(endorsement);
    }

    private EndorsementResponseDTO mapToDTO(Endorsement endorsement) {
        return EndorsementResponseDTO.builder()
                .endorsementId(endorsement.getEnodrsementId())
                .policyId(endorsement.getPolicy().getPolicyId())
                .changeType(endorsement.getChangeType().name())
                .premiumDelta(endorsement.getPremiumDelta())
                .effectiveDate(endorsement.getEffectiveDate())
                .status(endorsement.getStatus().name())
                .build();
    }
}