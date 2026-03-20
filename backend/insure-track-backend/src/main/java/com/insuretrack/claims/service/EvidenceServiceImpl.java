package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.EvidenceRequestDTO;
import com.insuretrack.claims.dto.EvidenceResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Evidence;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.EvidenceRepository;
import com.insuretrack.common.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class EvidenceServiceImpl implements EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final ClaimRepository claimRepository;

    private final String uploadDir = System.getProperty("user.dir") + "/docs/";

    @Override
    public EvidenceResponseDTO uploadEvidence(Long claimId,
                                              EvidenceRequestDTO metadata,
                                              MultipartFile file) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() == ClaimStatus.CLOSED)
            throw new RuntimeException("Cannot upload evidence to a closed claim");

        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // FIX: UUID is now actually used in the filename
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + filename;
            file.transferTo(new File(filePath));

            Evidence evidence = Evidence.builder()
                    .claim(claim)
                    .type(metadata.getType())
                    .uri(filePath)
                    .uploadedDate(LocalDate.now())
                    .build();

            evidenceRepository.save(evidence);
            return mapToResponse(evidence);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public List<EvidenceResponseDTO> getEvidenceByClaim(Long claimId) {
        // FIX: use proper repository query instead of findAll() + filter
        return evidenceRepository.findByClaimClaimId(claimId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EvidenceResponseDTO mapToResponse(Evidence evidence) {
        return EvidenceResponseDTO.builder()
                .evidenceId(evidence.getEvidenceId())
                .claimId(evidence.getClaim().getClaimId())
                .type(evidence.getType())
                .uri(evidence.getUri())
                .uploadedDate(evidence.getUploadedDate())
                .build();
    }
}

//package com.insuretrack.claims.service;
//
//import com.insuretrack.claims.dto.EvidenceRequestDTO;
//import com.insuretrack.claims.dto.EvidenceResponseDTO;
//import com.insuretrack.claims.entity.Claim;
//import com.insuretrack.claims.entity.Evidence;
//import com.insuretrack.claims.repository.ClaimRepository;
//import com.insuretrack.claims.repository.EvidenceRepository;
//import com.insuretrack.common.enums.ClaimStatus;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class EvidenceServiceImpl implements EvidenceService {
//
//    private final EvidenceRepository evidenceRepository;
//    private final ClaimRepository claimRepository;
//
//    private final String uploadDir = System.getProperty("user.dir")+"/docs/";
//
//    @Override
//    public EvidenceResponseDTO uploadEvidence(
//            Long claimId,
//            EvidenceRequestDTO metadata,
//            MultipartFile file) {
//
//        Claim claim = claimRepository.findById(claimId)
//                .orElseThrow(() -> new RuntimeException("Claim not found"));
//
//        if (claim.getStatus() == ClaimStatus.CLOSED) {
//            throw new RuntimeException("Cannot upload evidence to closed claim");
//        }
//
//        try {
//
//
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//            String filename= UUID.randomUUID()+"_"+file.getOriginalFilename();
//            String filePath = uploadDir + file.getOriginalFilename();
//            file.transferTo(new File(filePath));
//
//            Evidence evidence = Evidence.builder()
//                    .claim(claim)
//                    .type(metadata.getType())
//                    .uri(filePath)
//                    .uploadedDate(LocalDate.now())
//                    .build();
//
//            evidenceRepository.save(evidence);
//
//            return mapToResponse(evidence);
//
//        } catch (IOException e) {
//            throw new RuntimeException("File upload failed "+e.getMessage());
//        }
//    }
//
//    @Override
//    public List<EvidenceResponseDTO> getEvidenceByClaim(Long claimId) {
//
//        return evidenceRepository.findAll()
//                .stream()
//                .filter(e -> e.getClaim().getClaimId().equals(claimId))
//                .map(this::mapToResponse)
//                .toList();
//    }
//
//    private EvidenceResponseDTO mapToResponse(Evidence evidence) {
//
//        return EvidenceResponseDTO.builder()
//                .evidenceId(evidence.getEvidenceId())
//                .claimId(evidence.getClaim().getClaimId())
//                .type(evidence.getType())
//                .uri(evidence.getUri())
//                .uploadedDate(evidence.getUploadedDate())
//                .build();
//    }
//}
