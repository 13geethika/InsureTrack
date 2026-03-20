package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.RefundStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class RefundResponseDTO {
    private Long refundId;
    private Long paymentId;
    private Double amount;
    private LocalDate processedDate;
    private String reason;
    private RefundStatus status;

    private String customerName;
    public RefundResponseDTO(Long refundId,
                             Long paymentId,
                             Double amount,
                             LocalDate processedDate,
                             String reason,
                             RefundStatus status,
                             String customerName) {
        this.refundId = refundId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.processedDate = processedDate;
        this.reason = reason;
        this.status = status;
        this.customerName = customerName;
    }



}
