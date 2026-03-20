package com.insuretrack.policy.service;

import com.insuretrack.policy.dto.RenewalRequestDTO;
import com.insuretrack.policy.dto.RenewalResponseDTO;

import java.util.List;

public interface RenewalService {
    RenewalResponseDTO createOffer(RenewalRequestDTO requestDTO);
    List<RenewalResponseDTO> getByPolicy(Long policyId);
}
