package com.insuretrack.customer.service;

import com.insuretrack.customer.dto.*;
import com.insuretrack.customer.entity.Customer;

public interface CustomerService {
    void createCustomer(Long userId, CustomerRequestDTO dto);
    CustomerResponseDTO getCustomer(Long customerId);
    void updateCustomer(Long customerId, CustomerRequestDTO dto);
    void deactivateCustomer(Long customerId);
    void addBeneficiary(Long customerId, BeneficiaryRequestDTO dto);
    void updateBeneficiary(Long beneficiaryId,BeneficiaryRequestDTO dto);
    void removeBeneficiary(Long beneficiaryId);
    void addInsuredObject(Long customerId, InsuredObjectRequestDTO dto);
    void updateInsuredObject(Long objectId, RiskAssessmentRequestDTO dto);
    void deactivateInsuredObject(Long objectId);
}
