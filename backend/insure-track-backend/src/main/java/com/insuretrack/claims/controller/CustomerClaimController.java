package com.insuretrack.claims.controller;

import com.insuretrack.claims.dto.ClaimRequestDTO;
import com.insuretrack.claims.dto.ClaimResponseDTO;
import com.insuretrack.claims.dto.EvidenceRequestDTO;
import com.insuretrack.claims.dto.EvidenceResponseDTO;
import com.insuretrack.claims.service.ClaimService;
import com.insuretrack.claims.service.EvidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customer/claims")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CustomerClaimController {

    private final ClaimService claimService;
    private final EvidenceService evidenceService;
    private final ObjectMapper objectMapper;

    // Customer files FNOL — creates claim with status OPEN
    @PostMapping
    public ClaimResponseDTO fileClaim(@RequestBody ClaimRequestDTO dto) {
        return claimService.createClaim(dto);
    }

    // Customer uploads evidence for their claim
    @PostMapping(value = "/{id}/evidence",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EvidenceResponseDTO uploadEvidence(
            @PathVariable Long id,
            @RequestPart("metadata") String metadataJson,
            @RequestPart("file") MultipartFile file) throws Exception {
        EvidenceRequestDTO metadata = objectMapper.readValue(metadataJson, EvidenceRequestDTO.class);
        return evidenceService.uploadEvidence(id, metadata, file);
    }
}