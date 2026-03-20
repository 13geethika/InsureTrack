package com.insuretrack.insure_track_backend.billing;
import com.insuretrack.billing.dto.InvoiceRequestDTO;
import com.insuretrack.billing.dto.InvoiceResponseDTO;
import com.insuretrack.billing.entity.Invoice;
import com.insuretrack.billing.repository.InvoiceRepository;
import com.insuretrack.billing.service.InvoiceServiceImpl;
import com.insuretrack.common.enums.InvoiceStatus;
import com.insuretrack.common.enums.PolicyStatus;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.PolicyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    public InvoiceServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInvoice_validPolicyAndData() {
        // Arrange
        Policy policy = Policy.builder()
                .policyId(1L)
                .status(PolicyStatus.ACTIVE)
                .build();

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        InvoiceRequestDTO dto = InvoiceRequestDTO.builder()
                .amount(5000.0)
                .dueDate(LocalDate.now().plusDays(10))
                .build();

        // Act
        InvoiceResponseDTO response = invoiceService.createInvoice(1L, dto);

        // Assert
        assertEquals(1L, response.getPolicyId());
        assertEquals(5000.0, response.getAmount());
        assertEquals(InvoiceStatus.OPEN, response.getStatus());
    }

    @Test
    void testCreateInvoice_invalidPolicyStatus() {
        Policy policy = Policy.builder()
                .policyId(1L)
                .status(PolicyStatus.LAPSED)
                .build();

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        InvoiceRequestDTO dto = InvoiceRequestDTO.builder()
                .amount(5000.0)
                .dueDate(LocalDate.now().plusDays(10))
                .build();

        assertThrows(RuntimeException.class, () -> invoiceService.createInvoice(1L, dto));
    }

    @Test
    void testCreateInvoice_dueDateInPastShouldFail() {
        Policy policy = Policy.builder()
                .policyId(1L)
                .status(PolicyStatus.ACTIVE)
                .build();

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        InvoiceRequestDTO dto = InvoiceRequestDTO.builder()
                .amount(5000.0)
                .dueDate(LocalDate.now().minusDays(1)) // past date
                .build();

        assertThrows(RuntimeException.class, () -> invoiceService.createInvoice(1L, dto));
    }

    @Test
    void testCreateInvoice_amountTooLowShouldFail() {
        Policy policy = Policy.builder()
                .policyId(1L)
                .status(PolicyStatus.ACTIVE)
                .build();

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        InvoiceRequestDTO dto = InvoiceRequestDTO.builder()
                .amount(500.0) // too low
                .dueDate(LocalDate.now().plusDays(10))
                .build();

        assertThrows(RuntimeException.class, () -> invoiceService.createInvoice(1L, dto));
    }
}
