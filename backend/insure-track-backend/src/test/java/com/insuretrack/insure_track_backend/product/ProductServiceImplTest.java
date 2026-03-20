package com.insuretrack.insure_track_backend.product;

import com.insuretrack.common.enums.CoverageType;
import com.insuretrack.common.enums.Status;
import com.insuretrack.product.dto.*;
import com.insuretrack.product.entity.Coverage;
import com.insuretrack.product.entity.Product;
import com.insuretrack.product.entity.RatingRule;
import com.insuretrack.product.repository.CoverageRepository;
import com.insuretrack.product.repository.ProductRepository;
import com.insuretrack.product.repository.RatingRuleRepository;
import com.insuretrack.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @Mock private CoverageRepository coverageRepository;
    @Mock private RatingRuleRepository ratingRuleRepository;

    @InjectMocks private ProductServiceImpl productService;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = Product.builder()
                .productId(1L)
                .name("Health Plan")
                .description("Basic Health")
                .status(Status.INACTIVE)
                .coverages(new ArrayList<>())
                .ratingRules(new ArrayList<>())
                .build();
    }

    // --- ORIGINAL TEST CASES ---

    @Test
    @DisplayName("Should create product with INACTIVE status")
    void createProduct_Success() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Health Plan");
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        ProductResponseDTO result = productService.createProduct(request);
        assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    @DisplayName("Should change product status to ACTIVE")
    void activateProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        ProductResponseDTO result = productService.activateProduct(1L);
        assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    @DisplayName("Should add coverage and verify mapping")
    void addCoverage_Success() {
        CoverageRequestDTO covRequest = new CoverageRequestDTO();
        covRequest.setCoverageType(CoverageType.HOSPITALIZATION);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        ProductResponseDTO result = productService.addCoverage(1L, covRequest);
        assertThat(result.getCoverages()).hasSize(1);
    }

    // --- 4 NEW TEST CASES ---

    @Test
    @DisplayName("NEW: Should change product status to INACTIVE")
    void deactivateProduct_Success() {
        existingProduct.setStatus(Status.ACTIVE); // Start as Active
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductResponseDTO result = productService.deactivateProduct(1L);

        assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
        verify(productRepository).save(existingProduct);
    }

    @Test
    @DisplayName("NEW: Should throw Exception when adding coverage to missing product")
    void addCoverage_ProductNotFound() {
        CoverageRequestDTO covRequest = new CoverageRequestDTO();
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                productService.addCoverage(999L, covRequest)
        );
        verify(coverageRepository, never()).save(any());
    }

    @Test
    @DisplayName("NEW: Should return empty lists in DTO when Product has no coverages or rules")
    void mapToDTO_HandlesNullCollections() {
        // Create a product where collections are null instead of empty lists
        Product nullCollProduct = Product.builder()
                .productId(2L)
                .coverages(null)
                .ratingRules(null)
                .build();

        when(productRepository.findAll()).thenReturn(List.of(nullCollProduct));

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertThat(result.get(0).getCoverages()).isNotNull().isEmpty();
        assertThat(result.get(0).getRatingRules()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("NEW: Should verify multiple products are returned correctly")
    void getAllProducts_MultipleEntries() {
        Product p2 = Product.builder().productId(2L).name("Life Plan").coverages(new ArrayList<>()).build();
        when(productRepository.findAll()).thenReturn(List.of(existingProduct, p2));

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(ProductResponseDTO::getName)
                .containsExactlyInAnyOrder("Health Plan", "Life Plan");
    }

    // --- REMAINDER OF PREVIOUS TESTS ---

    @Test
    @DisplayName("Should add rating rule successfully")
    void addRatingRule_Success() {
        RatingRuleRequestDTO ruleReq = new RatingRuleRequestDTO();
        ruleReq.setFactor("age");
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        ProductResponseDTO result = productService.addRatingRule(1L, ruleReq);
        assertThat(result.getRatingRules()).hasSize(1);
    }
}