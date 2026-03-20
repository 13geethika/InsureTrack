package com.insuretrack.customer.controller;

import com.insuretrack.customer.dto.*;
import com.insuretrack.customer.service.CustomerService;
import com.insuretrack.product.service.RatingRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final RatingRuleService ratingRuleService;
    @PostMapping("/{userId}")
    public void createCustomer(@PathVariable Long userId, @RequestBody CustomerRequestDTO dto){
        customerService.createCustomer(userId,dto);
    }
    @PostMapping("/{customerId}/beneficiaries")
    public void addBeneficiary(@PathVariable Long customerId, @RequestBody BeneficiaryRequestDTO dto){
        customerService.addBeneficiary(customerId,dto);
    }
    @PostMapping("/{customerId}/insuredobjects")
    public void addInsuredObject(@PathVariable Long customerId, @RequestBody InsuredObjectRequestDTO dto){
        customerService.addInsuredObject(customerId,dto);
    }
    @GetMapping("/{customerId}")
    public CustomerResponseDTO getCustomer(@PathVariable Long customerId){
        return customerService.getCustomer(customerId);
    }
    @PutMapping("/update/{customerId}")
    public void updateCustomer(@PathVariable Long customerId,@RequestBody CustomerRequestDTO dto){
        customerService.updateCustomer(customerId,dto);
    }
    @PatchMapping("/{customerId}/deactivate")
    public void deactivateCustomer(@PathVariable Long customerId){
        customerService.deactivateCustomer(customerId);
    }
    @PutMapping("/updatebeneficiary/{beneficiaryId}")
    public void updateBeneficiary(@PathVariable Long beneficiaryId,@RequestBody BeneficiaryRequestDTO dto){
        customerService.updateBeneficiary(beneficiaryId,dto);
    }
    @DeleteMapping("del/beneficiary/{beneficiaryId}")
    public void removeBeneficiary(@PathVariable Long beneficiaryId){
        customerService.removeBeneficiary(beneficiaryId);
    }
    @PutMapping("/update/insuredobj/{objectId}")
    public void updateInsuredObject(@PathVariable Long objectId,@RequestBody RiskAssessmentRequestDTO dto){
        customerService.updateInsuredObject(objectId,dto);
    }
    @PatchMapping("/insuredobj/{objectId}/deactivate")
    public void deactivateInsuredObject(@PathVariable Long objectId){
        customerService.deactivateInsuredObject(objectId);
    }
}
