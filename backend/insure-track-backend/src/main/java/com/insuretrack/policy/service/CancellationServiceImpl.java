package com.insuretrack.policy.service;

import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.policy.dto.CancellationRequestDTO;
import com.insuretrack.policy.dto.CancellationResponseDTO;
import com.insuretrack.policy.entity.Cancellation;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.CancellationRepository;
import com.insuretrack.policy.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CancellationServiceImpl implements CancellationService{
    private final CancellationRepository cancellationRepository;
    private final PolicyRepository policyRepository;

    @Override
    public CancellationResponseDTO cancel(CancellationRequestDTO request) {
        Policy policy=policyRepository.findById(request.getPolicyId()).orElseThrow(()->new RuntimeException("Policy not found"));
        policy.setStatus(PolicyStatus.CANCELLED);
        Cancellation cancellation=Cancellation.builder()
                .policy(policy)
                .reason(request.getReason())
                .effectiveDate(LocalDate.now())
                .build();
        cancellationRepository.save(cancellation);
        return CancellationResponseDTO.builder()
                .cancellationId(cancellation.getCancellationId())
                .policyId(policy.getPolicyId())
                .reason(cancellation.getReason())
                .effectiveDate(cancellation.getEffectiveDate())
                .build();
    }

    @Override
    public List<CancellationResponseDTO> getByPolicy(Long policyId) {
        return cancellationRepository.findByPolicyPolicyId(policyId)
                .stream()
                .map(c->CancellationResponseDTO.builder()
                        .cancellationId(c.getCancellationId())
                        .policyId(c.getPolicy().getPolicyId())
                        .reason(c.getReason())
                        .effectiveDate(c.getEffectiveDate())
                        .build()).toList();
    }
}
