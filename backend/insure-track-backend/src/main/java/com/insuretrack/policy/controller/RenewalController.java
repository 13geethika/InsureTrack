package com.insuretrack.policy.controller;

import com.insuretrack.policy.dto.RenewalRequestDTO;
import com.insuretrack.policy.dto.RenewalResponseDTO;
import com.insuretrack.policy.service.RenewalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/renewal")
@RequiredArgsConstructor
public class RenewalController {
    private final RenewalService renewalService;
    @PostMapping
    public RenewalResponseDTO createOffer(@RequestBody RenewalRequestDTO requestDTO){
        ResponseEntity.ok();
        return renewalService.createOffer(requestDTO);
    }
    @GetMapping("/policy/{policyId}")
    public
            List<RenewalResponseDTO> getByPolicy(@PathVariable Long policyId){
        ResponseEntity.ok();
        return renewalService.getByPolicy(policyId);
    }
}
