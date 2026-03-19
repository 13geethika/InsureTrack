package com.insuretrack.policy.service;

import com.insuretrack.policy.dto.CancellationRequestDTO;
import com.insuretrack.policy.dto.CancellationResponseDTO;

import java.util.List;

public interface CancellationService {
    CancellationResponseDTO cancel(CancellationRequestDTO request);
    List<CancellationResponseDTO> getByPolicy(Long policyId);
}
