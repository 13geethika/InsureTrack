package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor       // ✅ generates a no-args constructor
//@AllArgsConstructor      // ✅ generates an all-args constructor
public class InvoiceResponseDTO {
    private Long invoiceId;
    private Long policyId;
    private Double amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private InvoiceStatus status;

    private String customerName;

    public InvoiceResponseDTO(Long invoiceId, Long policyId, Double amount,
                              LocalDate issueDate, LocalDate dueDate,
                              InvoiceStatus status, String customerName) {
        this.invoiceId = invoiceId;
        this.policyId = policyId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
        this.customerName = customerName;
    }
}
