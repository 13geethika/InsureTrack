package com.insuretrack.policy.controller;

import com.insuretrack.policy.dto.CancellationRequestDTO;
import com.insuretrack.policy.dto.CancellationResponseDTO;
import com.insuretrack.policy.service.CancellationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/cancellation")
@RequiredArgsConstructor
public class CancellationController {
    private final CancellationService cancellationService;
    @PostMapping
    public ResponseEntity<CancellationResponseDTO> cancel(@RequestBody CancellationRequestDTO requestDTO){
        return  ResponseEntity.ok(cancellationService.cancel(requestDTO));
    }
    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<CancellationResponseDTO>> getByPolicy(@PathVariable Long policyId){
        return  ResponseEntity.ok(cancellationService.getByPolicy(policyId));
    }
}
