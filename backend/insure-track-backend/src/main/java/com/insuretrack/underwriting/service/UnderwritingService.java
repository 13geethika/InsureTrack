package com.insuretrack.underwriting.service;

import com.insuretrack.underwriting.dto.UnderwritingDecisionDTO;
import com.insuretrack.underwriting.dto.UnderwritingRequestDTO;
import com.insuretrack.underwriting.dto.UnderwritingResponseDTO;

import java.util.List;
import java.util.Map;

public interface UnderwritingService {
    UnderwritingResponseDTO createCase(Long quoteId);
    List<UnderwritingResponseDTO> getPendingCases();
    UnderwritingResponseDTO getCase(Long caseId);
    UnderwritingResponseDTO makeDecision(Long caseId, UnderwritingDecisionDTO decisionDTO);
    List<UnderwritingResponseDTO> getAllCases();
    Map<String, String> getUnderwriterProfile();
    void updateProfile(Map<String, String> updates);
}
