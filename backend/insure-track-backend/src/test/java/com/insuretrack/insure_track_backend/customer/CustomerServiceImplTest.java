package com.insuretrack.insure_track_backend.customer;
import com.insuretrack.user.repository.AuditLogRepository;
import com.insuretrack.common.enums.ObjectType;
import com.insuretrack.common.enums.Segment;
import com.insuretrack.common.enums.Status;
import com.insuretrack.common.exception.ResourceNotFoundException;
import com.insuretrack.customer.dto.*;
import com.insuretrack.customer.entity.Customer;
import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.customer.repository.BeneficiaryRepository;
import com.insuretrack.customer.repository.CustomerRepository;
import com.insuretrack.customer.repository.InsuredObjectRepository;
import com.insuretrack.customer.service.CustomerServiceImpl;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private BeneficiaryRepository beneficiaryRepository;
    @Mock private InsuredObjectRepository insuredObjectRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuditLogRepository auditLogRepository;
    @Mock private ObjectMapper objectMapper;

    @InjectMocks private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CustomerRequestDTO randomCustomer() {
        String name = "Customer-" + UUID.randomUUID().toString().substring(0, 5);
        LocalDate dob = LocalDate.of(
                ThreadLocalRandom.current().nextInt(1960, 2005),
                ThreadLocalRandom.current().nextInt(1, 12),
                ThreadLocalRandom.current().nextInt(1, 28)
        );
        String contact = String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L));
        Segment segment = Segment.values()[ThreadLocalRandom.current().nextInt(Segment.values().length)];
        return new CustomerRequestDTO(name, dob, contact, segment, Status.ACTIVE);
    }

    private InsuredObjectRequestDTO randomInsuredObject() {
        ObjectType type = ThreadLocalRandom.current().nextBoolean() ? ObjectType.BUS : ObjectType.CAR;
        java.util.Map<String,Object> details = java.util.Map.of(
                "id", UUID.randomUUID().toString(),
                "value", ThreadLocalRandom.current().nextInt(1000, 100000)
        );
        return new InsuredObjectRequestDTO(type, details);
    }

    private RiskAssessmentRequestDTO randomRiskAssessment() {
        RiskAssessmentRequestDTO dto = new RiskAssessmentRequestDTO();
        dto.valuation = ThreadLocalRandom.current().nextDouble(10000, 500000);
        dto.riskScore = ThreadLocalRandom.current().nextInt(1, 10);
        return dto;
    }


    @Test
    void testCreateCustomer_WithRandomValues() {
        User user = User.builder().userId(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        CustomerRequestDTO dto = randomCustomer();

        customerService.createCustomer(1L, dto);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());

        Customer saved = captor.getValue();
        assertEquals(dto.name(), saved.getName());
        assertEquals(dto.contactInfo(), saved.getContactInfo());
        assertEquals(dto.segment(), saved.getSegment());
        assertEquals(Status.ACTIVE, saved.getStatus());
    }


    @Test
    void testAddInsuredObject_WithRandomValues() throws Exception {
        Customer customer = Customer.builder().customerId(1L).build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doReturn("{\"dynamic\":\"json\"}").when(objectMapper).writeValueAsString(any());

        InsuredObjectRequestDTO dto = new InsuredObjectRequestDTO(ObjectType.BUS, Map.of("age",5,"vehicaltype","BUS"));

        customerService.addInsuredObject(1L, dto);

        ArgumentCaptor<InsuredObject> captor = ArgumentCaptor.forClass(InsuredObject.class);
        verify(insuredObjectRepository).save(captor.capture());

        InsuredObject saved = captor.getValue();
        assertEquals(dto.objectType(), saved.getObjectType());
        assertEquals(Status.ACTIVE, saved.getStatus());
        assertEquals("{\"dynamic\":\"json\"}", saved.getDetailsJson());
    }


    @Test
    void testUpdateInsuredObject_WithRandomValues() {
        InsuredObject object = InsuredObject.builder().objectId(1L).build();
        when(insuredObjectRepository.findById(1L)).thenReturn(Optional.of(object));

        RiskAssessmentRequestDTO dto = randomRiskAssessment();

        customerService.updateInsuredObject(1L, dto);

        assertEquals(dto.valuation, object.getValuation());
        assertEquals(dto.riskScore, object.getRiskScore());
        verify(insuredObjectRepository).save(object);
    }

    @Test
    void testUpdateInsuredObject_NotFound() {
        when(insuredObjectRepository.findById(99L)).thenReturn(Optional.empty());
        RiskAssessmentRequestDTO dto = randomRiskAssessment();
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateInsuredObject(99L, dto));
    }
}
