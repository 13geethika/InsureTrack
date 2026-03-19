package com.insuretrack.billing.service;

import com.insuretrack.billing.dto.RefundRequestDTO;
import com.insuretrack.billing.dto.RefundResponseDTO;
import com.insuretrack.billing.entity.Payment;
import com.insuretrack.billing.entity.Refund;
import com.insuretrack.billing.repository.PaymentRepository;
import com.insuretrack.billing.repository.RefundRepository;
import com.insuretrack.common.enums.PaymentStatus;
import com.insuretrack.common.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RefundServiceImpl implements RefundService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    @Override
    public RefundResponseDTO initiateRefund(
            Long paymentId,
            RefundRequestDTO dto) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getStatus().equals(PaymentStatus.COMPLETED)) {
            throw new RuntimeException("Refund allowed only for completed payments");
        }
        // Refund amount must be positive and not exceed payment amount
        if (dto.getAmount() > payment.getAmount()) {
            throw new RuntimeException("Refund exceeds payment amount");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new RuntimeException("Refund amount must be positive.");
        }
        // Validation 3: Reason must not be null or empty
        if (dto.getReason() == null || dto.getReason().trim().isEmpty()) {
            throw new RuntimeException("Refund reason cannot be empty.");
        }
        // Validation 4: Processed date must be after payment date
        LocalDate processedDate = LocalDate.now();
        if (processedDate.isBefore(payment.getPaidDate())) {
            throw new RuntimeException("Refund processed date must be after payment date.");
        }

        Refund refund = Refund.builder()
                .payment(payment)
                .amount(dto.getAmount())
                .reason(dto.getReason())
                .processedDate(LocalDate.now())
                .status(RefundStatus.COMPLETED)
                .build();

        refundRepository.save(refund);

        return mapToResponse(refund);
    }

    private RefundResponseDTO mapToResponse(Refund refund) {

        return RefundResponseDTO.builder()
                .refundId(refund.getRefundId())
                .paymentId(refund.getPayment().getPaymentId())
                .amount(refund.getAmount())
                .processedDate(refund.getProcessedDate())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .build();

    }

    @Override
    public List<RefundResponseDTO> getAllRefundsWithCustomer() {
        return refundRepository.findAllRefundsWithCustomer();
    }

}
