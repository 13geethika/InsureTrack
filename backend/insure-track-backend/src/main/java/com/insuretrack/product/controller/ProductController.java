package com.insuretrack.product.controller;

import com.insuretrack.product.dto.*;
import com.insuretrack.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/products")

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}/activate")
    public ProductResponseDTO activate(@PathVariable Long id) {
        return productService.activateProduct(id);
    }

    @PutMapping("/{id}/deactivate")
    public ProductResponseDTO deactivate(@PathVariable Long id) {
        return productService.deactivateProduct(id);
    }

    @PostMapping("/{id}/coverages")
    public ProductResponseDTO addCoverage(@PathVariable Long id,
                                          @RequestBody CoverageRequestDTO request) {
        return productService.addCoverage(id, request);
    }

    @PostMapping("/{id}/rating-rules")
    public ProductResponseDTO addRule(@PathVariable Long id,
                                      @RequestBody RatingRuleRequestDTO request) {
        return productService.addRatingRule(id, request);
    }

    @GetMapping()
    public
    List<ProductResponseDTO> getAll() {
        return productService.getAllProducts();
    }
    @GetMapping("/coverages")
    public List<CoverageResponseDTO> getAllCoverages() {
        return productService.getAllCoverages();
    }

    @GetMapping("/rating-rules")
    public List<RatingRuleResponseDTO> getAllRatingRules() {
        return productService.getAllRatingRules();
    }

}