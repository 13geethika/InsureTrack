package com.insuretrack.insure_track_backend.billing;
import com.insuretrack.billing.dto.RefundRequestDTO;
import com.insuretrack.billing.dto.RefundResponseDTO;
import com.insuretrack.billing.entity.Payment;
import com.insuretrack.billing.repository.PaymentRepository;
import com.insuretrack.billing.repository.RefundRepository;
import com.insuretrack.billing.service.RefundServiceImpl;
import com.insuretrack.common.enums.PaymentStatus;
import com.insuretrack.common.enums.RefundStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RefundServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RefundRepository refundRepository;

    @InjectMocks
    private RefundServiceImpl refundService;

    @Test
    void testInitiateRefund_validRefund() {
        Payment payment = Payment.builder()
                .paymentId(1L)
                .amount(5000.0)
                .paidDate(LocalDate.now().minusDays(2))
                .status(PaymentStatus.COMPLETED)
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        RefundRequestDTO dto = RefundRequestDTO.builder()
                .amount(2000.0)
                .reason("Customer request")
                .build();

        RefundResponseDTO response = refundService.initiateRefund(1L, dto);

        assertEquals(1L, response.getPaymentId());
        assertEquals(2000.0, response.getAmount());
        assertEquals(RefundStatus.COMPLETED, response.getStatus());
    }

    @Test
    void testInitiateRefund_invalidPaymentStatus() {
        Payment payment = Payment.builder()
                .paymentId(1L)
                .amount(5000.0)
                .paidDate(LocalDate.now().minusDays(2))
                .status(PaymentStatus.PENDING)
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        RefundRequestDTO dto = RefundRequestDTO.builder()
                .amount(2000.0)
                .reason("Customer request")
                .build();

        assertThrows(RuntimeException.class, () -> refundService.initiateRefund(1L, dto));
    }
}

