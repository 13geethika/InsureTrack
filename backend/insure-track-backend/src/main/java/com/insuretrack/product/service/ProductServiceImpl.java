package com.insuretrack.product.service;

//import com.insuretrack.common.annotation.Auditable;
import com.insuretrack.common.annotation.Auditable;
import com.insuretrack.common.enums.Status;
import com.insuretrack.product.dto.*;
import com.insuretrack.product.entity.Coverage;
import com.insuretrack.product.entity.Product;
import com.insuretrack.product.entity.RatingRule;
import com.insuretrack.product.repository.CoverageRepository;
import com.insuretrack.product.repository.ProductRepository;
import com.insuretrack.product.repository.RatingRuleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CoverageRepository coverageRepository;
    private final RatingRuleRepository ratingRuleRepository;
    @Override
    @Auditable(action = "CREATE_PRODUCT")
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Status.INACTIVE)
                .build();

        return mapToDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO activateProduct(Long productId) {
        Product product = getProduct(productId);
        product.setStatus(Status.ACTIVE);
        return mapToDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO deactivateProduct(Long productId) {
        Product product = getProduct(productId);
        product.setStatus(Status.INACTIVE);
        return mapToDTO(productRepository.save(product));
    }

    @Override
//    @Auditable(action = "ADD_COVERAGE")
    public ProductResponseDTO addCoverage(Long productId, CoverageRequestDTO request) {
        Product product = getProduct(productId);

        Coverage coverage = Coverage.builder()
                .product(product)
                .coverageType(request.getCoverageType())
                .coverageLimit(request.getCoverageLimit())
                .deductible(request.getDeductible())
                .build();

        coverageRepository.save(coverage);
        product.getCoverages().add(coverage);
        return mapToDTO(product);
    }
    @Override
    public ProductResponseDTO addRatingRule(Long productId, RatingRuleRequestDTO request) {

        Product product = getProduct(productId);

        RatingRule rule = RatingRule.builder()
                .product(product)
                .factor(request.getFactor())
                .weight(request.getWeight())
                .expression(request.getExpression())
                .build();

        ratingRuleRepository.save(rule);
        product.getRatingRules().add(rule);
        return mapToDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<CoverageResponseDTO> getAllCoverages() {
        return coverageRepository.findAll().stream()
                .map(coverage -> CoverageResponseDTO.builder()
                        .coverageId(coverage.getCoverageId())
                        .productId(coverage.getProduct().getProductId())
                        .coverageType(coverage.getCoverageType())
                        .coverageLimit(coverage.getCoverageLimit())
                        .deductible(coverage.getDeductible())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingRuleResponseDTO> getAllRatingRules() {
        return ratingRuleRepository.findAll().stream()
                .map(rule -> RatingRuleResponseDTO.builder()
                        .ruleId(rule.getRuleId())
                        .productId(rule.getProduct().getProductId())
                        .factor(rule.getFactor())
                        .weight(rule.getWeight())
                        .expression(rule.getExpression())

                        .build())
                .collect(Collectors.toList());
    }


    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private ProductResponseDTO mapToDTO(Product product) {

        List<CoverageResponseDTO> coverageDTOs =
                product.getCoverages() == null ? List.of() :
                        product.getCoverages().stream()
                                .map(c -> CoverageResponseDTO.builder()
                                        .coverageId(c.getCoverageId())
                                        .coverageType(c.getCoverageType())
                                        .coverageLimit(c.getCoverageLimit())
                                        .deductible(c.getDeductible())
                                        .build())
                                .toList();

        List<RatingRuleResponseDTO> ruleDTOs =
                product.getRatingRules() == null ? List.of() :
                        product.getRatingRules().stream()
                                .map(r -> RatingRuleResponseDTO.builder()
                                        .ruleId(r.getRuleId())
                                        .factor(r.getFactor())
                                        .weight(r.getWeight())
                                        .expression(r.getExpression())
                                        .build())
                                .toList();

        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .coverages(coverageDTOs)
                .ratingRules(ruleDTOs)
                .build();
    }
}
