package com.insuretrack.billing.service;

import com.insuretrack.billing.dto.InvoiceRequestDTO;
import com.insuretrack.billing.dto.InvoiceResponseDTO;
import com.insuretrack.billing.entity.Invoice;
import com.insuretrack.billing.repository.InvoiceRepository;
import com.insuretrack.common.enums.InvoiceStatus;
import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PolicyRepository policyRepository;

    @Override
    public InvoiceResponseDTO createInvoice(
            Long policyId,
            InvoiceRequestDTO dto) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        // policy active or not
        if (!policy.getStatus().equals(PolicyStatus.ACTIVE)) {
            throw new RuntimeException("Invoice allowed only for ACTIVE policy");
        }
        if (dto.getAmount() == null || dto.getAmount() < 1000) {
            throw new RuntimeException("Invoice amount must be greater than 1000.");
        }
        if (dto.getDueDate() == null) {
            throw new RuntimeException("Due date cannot be null.");
        }

//        if (dto.getIssueDate() != null && dto.getIssueDate().isAfter(dto.getDueDate())) {
//            throw new RuntimeException("Issue date must be before due date.");
//        }

        if (dto.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Due date must be in the future.");
        }



        Invoice invoice = Invoice.builder()
                .policy(policy)
                .amount(dto.getAmount())
                .issueDate(LocalDate.now())
                .dueDate(dto.getDueDate())
                .status(InvoiceStatus.OPEN)
                .build();

        invoiceRepository.save(invoice);

        return mapToResponse(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> getInvoiceByPolicy(Long policyId) {

        return invoiceRepository.findByPolicyPolicyId(policyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private InvoiceResponseDTO mapToResponse(Invoice invoice) {

        return InvoiceResponseDTO.builder()
                .invoiceId(invoice.getInvoiceId())
                .policyId(invoice.getPolicy().getPolicyId())
                .amount(invoice.getAmount())
                .issueDate(invoice.getIssueDate())
                .dueDate(invoice.getDueDate())
                .status(invoice.getStatus())
                .build();
    }



    @Override
    public List<InvoiceResponseDTO> getInvoicesWithCustomer(Long policyId) {
        return invoiceRepository.findInvoicesWithCustomer(policyId);
    }


    @Override
    public List<InvoiceResponseDTO> getAllInvoicesWithCustomer() {
        return invoiceRepository.findAllInvoicesWithCustomer();
    }

}