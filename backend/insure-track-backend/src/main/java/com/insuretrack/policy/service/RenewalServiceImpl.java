package com.insuretrack.policy.service;

import com.insuretrack.common.enums.RenewalStatus;
import com.insuretrack.policy.dto.RenewalRequestDTO;
import com.insuretrack.policy.dto.RenewalResponseDTO;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.entity.Renewal;
import com.insuretrack.policy.repository.PolicyRepository;
import com.insuretrack.policy.repository.RenewalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RenewalServiceImpl implements RenewalService{
    private final RenewalRepository renewalRepository;
    private final PolicyRepository policyRepository;
    public RenewalResponseDTO createOffer(RenewalRequestDTO request){
        Policy policy=policyRepository.findById(request.getPolicyId())
                .orElseThrow(()->new RuntimeException("Policy not found"));
        Renewal renewal= Renewal.builder()
                .policy(policy)
                .proposedPremium(request.getProposedPremium())
                .offerDate(LocalDate.now())
                .status(RenewalStatus.OFFERED)
                .build();
        renewalRepository.save(renewal);
        return RenewalResponseDTO.builder()
                .renewalId(renewal.getRenewalId())
                .policyId(policy.getPolicyId())
                .proposedPremium(renewal.getProposedPremium())
                .offerDate(renewal.getOfferDate())
                .status(renewal.getStatus().name())
                .build();
    }

    @Override
    public List<RenewalResponseDTO> getByPolicy(Long policyId) {
        return renewalRepository.findByPolicyPolicyId(policyId)
                .stream().map(r->RenewalResponseDTO.builder()
                        .renewalId(r.getRenewalId())
                        .policyId(r.getPolicy().getPolicyId())
                        .proposedPremium(r.getProposedPremium())
                        .offerDate(r.getOfferDate())
                        .status(r.getStatus().name())
                        .build()).toList();
    }
}
