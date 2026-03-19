package com.insuretrack.product.service;

import com.insuretrack.product.dto.*;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO request);
    ProductResponseDTO activateProduct(Long productId);
    ProductResponseDTO deactivateProduct(Long productId);
    ProductResponseDTO addCoverage(Long productId, CoverageRequestDTO request);
    ProductResponseDTO addRatingRule(Long productId, RatingRuleRequestDTO request);
    List<ProductResponseDTO> getAllProducts();

    List<CoverageResponseDTO> getAllCoverages();

    List<RatingRuleResponseDTO> getAllRatingRules();
}
