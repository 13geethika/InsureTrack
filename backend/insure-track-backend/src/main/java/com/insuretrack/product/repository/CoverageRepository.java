package com.insuretrack.product.repository;

import com.insuretrack.product.entity.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage,Long> {
    List<Coverage> findByProduct_ProductId(Long productId);

}
