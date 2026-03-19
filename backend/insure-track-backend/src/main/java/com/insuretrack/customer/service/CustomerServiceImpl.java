package com.insuretrack.customer.service;

import com.insuretrack.user.entity.AuditLog;
import com.insuretrack.user.repository.AuditLogRepository;
import com.insuretrack.common.enums.Status;
import com.insuretrack.common.exception.ResourceNotFoundException;
import com.insuretrack.customer.dto.*;
import com.insuretrack.customer.entity.Beneficiary;
import com.insuretrack.customer.entity.Customer;
import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.customer.repository.BeneficiaryRepository;
import com.insuretrack.customer.repository.CustomerRepository;
import com.insuretrack.customer.repository.InsuredObjectRepository;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final InsuredObjectRepository insuredObjectRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;
    @Override
    public void createCustomer(Long userId, CustomerRequestDTO dto) {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Customer customer=Customer.builder().user(user)
                .name(dto.name())
                .dob(dto.dob())
                .contactInfo(dto.contactInfo())
                .segment(dto.segment())
                .status(Status.ACTIVE)
                .build();
        customerRepository.save(customer);
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action("REGISTER")
                .resource("CUSTOMER")
                .timestamp(LocalDateTime.now())
                .metadata("Customer successfully registered")
                .build());
    }

    @Override
    public CustomerResponseDTO getCustomer(Long customerId) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        return new CustomerResponseDTO(customer.getCustomerId(),
                                    customer.getName(),
                                    customer.getDob(),
                                    customer.getContactInfo(),
                                    customer.getSegment(),
                                    customer.getStatus());
    }

    @Override
    public void updateCustomer(Long customerId, CustomerRequestDTO dto) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        customer.setName(dto.name());
        customer.setDob(dto.dob());
        customer.setContactInfo(dto.contactInfo());
        customer.setSegment(dto.segment());
        customer.setStatus(Status.ACTIVE);
        customerRepository.save(customer);

    }

    @Override
    public void deactivateCustomer(Long customerId) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        customer.setStatus(Status.INACTIVE);
        customerRepository.save(customer);
    }

    @Override
    public void addBeneficiary(Long customerId, BeneficiaryRequestDTO dto) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Customer not found"));
        Beneficiary beneficiary=Beneficiary.builder().customer(customer)
                .name(dto.name())
                .relationship(dto.relationShip())
                .percentageShare(dto.percentageShare())
                .build();
        beneficiaryRepository.save(beneficiary);

    }
    private void validateBeneficiaryShare(Long customerId, BigDecimal newShare){
        List<Beneficiary> beneficiaries=beneficiaryRepository.findByCustomer_CustomerId(customerId);
        BigDecimal total=beneficiaries.stream()
                .map(Beneficiary::getPercentageShare)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        if(total.add(newShare).compareTo(new BigDecimal("100"))>0){
            throw new RuntimeException("Total beneficiary share cannot exceed 100%");
        }
    }
    @Override
    public void updateBeneficiary(Long beneficiaryId, BeneficiaryRequestDTO dto) {
        Beneficiary beneficiary=beneficiaryRepository.findById(beneficiaryId).orElseThrow(()->new ResourceNotFoundException("Beneficiary not found"));
        beneficiary.setName(dto.name());
        beneficiary.setRelationship(dto.relationShip());
        beneficiary.setPercentageShare(dto.percentageShare());
        beneficiaryRepository.save(beneficiary);
    }

    @Override
    public void removeBeneficiary(Long beneficiaryId) {
        Beneficiary beneficiary=beneficiaryRepository.findById(beneficiaryId).orElseThrow(()->new ResourceNotFoundException("Beneficiary not found"));
        beneficiaryRepository.delete(beneficiary);
    }

    @Override
    public void addInsuredObject(Long customerId, InsuredObjectRequestDTO dto) {
        String json=objectMapper.writeValueAsString(dto.detailsJson());
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Customer not found"));
        InsuredObject insuredObject=InsuredObject.builder().customer(customer)
                .objectType(dto.objectType())
                .detailsJson(json)
                .status(Status.ACTIVE)
                .build();
        insuredObjectRepository.save(insuredObject);

    }

    @Override
    public void updateInsuredObject(Long objectId, RiskAssessmentRequestDTO dto) {
        InsuredObject object=insuredObjectRepository.findById(objectId).orElseThrow(()->new ResourceNotFoundException("Object not found"));
        object.setValuation(dto.getValuation());
        object.setRiskScore(dto.getRiskScore());
        insuredObjectRepository.save(object);
    }

    @Override
    public void deactivateInsuredObject(Long objectId) {
        InsuredObject object=insuredObjectRepository.findById(objectId).orElseThrow(()->new ResourceNotFoundException("Object not found"));
        object.setStatus(Status.INACTIVE);
        insuredObjectRepository.save(object);

    }
}
