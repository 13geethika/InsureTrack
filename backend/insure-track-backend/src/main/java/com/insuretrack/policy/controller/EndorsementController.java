package com.insuretrack.policy.controller;

import com.insuretrack.policy.dto.EndorsementRequestDTO;
import com.insuretrack.policy.dto.EndorsementResponseDTO;
import com.insuretrack.policy.service.EndorsementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/endorsements")
@RequiredArgsConstructor
public class EndorsementController {
    private final EndorsementService endorsementService;
    @PostMapping
    public ResponseEntity<EndorsementResponseDTO> create(@RequestBody EndorsementRequestDTO requestDTO){
        return ResponseEntity.ok(endorsementService.create(requestDTO));
    }
    @GetMapping
    public ResponseEntity<List<EndorsementResponseDTO>> getAll() {
        return ResponseEntity.ok(endorsementService.getAllEndorsements());
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<EndorsementResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(endorsementService.approveEndorsement(id));
    }
}
