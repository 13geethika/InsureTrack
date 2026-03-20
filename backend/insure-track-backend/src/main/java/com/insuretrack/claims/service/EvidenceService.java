package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.EvidenceRequestDTO;
import com.insuretrack.claims.dto.EvidenceResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvidenceService {

    EvidenceResponseDTO uploadEvidence(
            Long claimId,
            EvidenceRequestDTO metadata,
            MultipartFile file);


    List<EvidenceResponseDTO> getEvidenceByClaim(Long claimId);
}