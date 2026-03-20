package com.insuretrack.policy.service;

import com.insuretrack.policy.dto.PolicyResponseDTO;

import java.util.List;

public interface PolicyService {
    PolicyResponseDTO issuePolicy(Long quoteId);
    PolicyResponseDTO getPolicy(Long policyId);
    List<PolicyResponseDTO> getAllPolicies();
}
