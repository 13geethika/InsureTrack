package com.insuretrack.policy.service;

import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.policy.dto.PolicyResponseDTO;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.PolicyRepository;
import com.insuretrack.quote.entity.Quote;
import com.insuretrack.quote.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final QuoteRepository quoteRepository;

    @Override
    public PolicyResponseDTO issuePolicy(Long quoteId) {

        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        Policy policy = Policy.builder()
                .quote(quote)
                .policyNumber("POL-" + System.currentTimeMillis())
                .effectiveDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusYears(1))
                .premium(quote.getPremium())
                .status(PolicyStatus.ACTIVE)
                .build();

        policyRepository.save(policy);

        return map(policy);
    }


    @Override
    public PolicyResponseDTO getPolicy(Long policyId) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        return map(policy);
    }

    public List<PolicyResponseDTO> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::map) // Uses your existing map() method
                .toList();
    }


    private PolicyResponseDTO map(Policy policy) {
        return PolicyResponseDTO.builder()
                .policyId(policy.getPolicyId())
                .policyNumber(policy.getPolicyNumber())
                // Add null check for the Quote relationship
                .quoteId(policy.getQuote() != null ? policy.getQuote().getQuoteId() : null)
                .effectiveDate(policy.getEffectiveDate())
                .expiryDate(policy.getExpiryDate())
                .premium(policy.getPremium())
                .status(policy.getStatus() != null ? policy.getStatus().name() : "UNKNOWN")
                .build();
    }
}