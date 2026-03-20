package com.insuretrack.billing.service;

import com.insuretrack.billing.dto.InvoiceRequestDTO;
import com.insuretrack.billing.dto.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(Long policyId, InvoiceRequestDTO dto);

    List<InvoiceResponseDTO> getInvoiceByPolicy(Long policyId);

    // ✅ new method
    List<InvoiceResponseDTO> getInvoicesWithCustomer(Long policyId);

    // ✅ new method
    List<InvoiceResponseDTO> getAllInvoicesWithCustomer();
}
