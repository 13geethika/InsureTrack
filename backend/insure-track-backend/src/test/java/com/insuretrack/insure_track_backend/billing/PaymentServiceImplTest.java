package com.insuretrack.insure_track_backend.billing;

import com.insuretrack.billing.dto.PaymentRequestDTO;
import com.insuretrack.billing.dto.PaymentResponseDTO;
import com.insuretrack.billing.entity.Invoice;
import com.insuretrack.billing.entity.Payment;
import com.insuretrack.billing.repository.InvoiceRepository;
import com.insuretrack.billing.repository.PaymentRepository;
import com.insuretrack.billing.service.PaymentServiceImpl;
import com.insuretrack.common.enums.InvoiceStatus;
import com.insuretrack.common.enums.PaymentMethod;
import com.insuretrack.common.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    public PaymentServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakePayment_validPayment() {
        Invoice invoice = Invoice.builder()
                .invoiceId(1L)
                .amount(5000.0)
                .status(InvoiceStatus.OPEN)
                .build();

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(paymentRepository.findByInvoiceInvoiceId(1L)).thenReturn(List.of());

        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .amount(5000.0)
                .method(PaymentMethod.CARD)
                .build();

        PaymentResponseDTO response = paymentService.makePayment(1L, dto);

        assertEquals(1L, response.getInvoiceId());
        assertEquals(5000.0, response.getAmount());
        assertEquals(PaymentStatus.COMPLETED, response.getStatus());
        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }

    @Test
    void testMakePayment_overPaymentShouldFail() {
        Invoice invoice = Invoice.builder()
                .invoiceId(1L)
                .amount(5000.0)
                .status(InvoiceStatus.OPEN)
                .build();

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(paymentRepository.findByInvoiceInvoiceId(1L))
                .thenReturn(List.of(Payment.builder().amount(4000.0).build()));

        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .amount(2000.0)
                .method(PaymentMethod.UPI)
                .build();

        assertThrows(RuntimeException.class, () -> paymentService.makePayment(1L, dto));
    }

    @Test
    void testMakePayment_nullMethodShouldFail() {
        Invoice invoice = Invoice.builder()
                .invoiceId(1L)
                .amount(5000.0)
                .status(InvoiceStatus.OPEN)
                .build();

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(paymentRepository.findByInvoiceInvoiceId(1L)).thenReturn(List.of());

        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .amount(3000.0)
                .method(null) // invalid
                .build();

        assertThrows(RuntimeException.class, () -> paymentService.makePayment(1L, dto));
    }

    @Test
    void testMakePayment_negativeAmountShouldFail() {
        Invoice invoice = Invoice.builder()
                .invoiceId(1L)
                .amount(5000.0)
                .status(InvoiceStatus.OPEN)
                .build();

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(paymentRepository.findByInvoiceInvoiceId(1L)).thenReturn(List.of());

        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .amount(-100.0) // invalid
                .method(PaymentMethod.CARD)
                .build();

        assertThrows(RuntimeException.class, () -> paymentService.makePayment(1L, dto));
    }
}
