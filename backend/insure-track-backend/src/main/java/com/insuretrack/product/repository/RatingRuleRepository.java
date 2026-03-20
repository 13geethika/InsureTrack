package com.insuretrack.product.repository;

import com.insuretrack.product.entity.RatingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRuleRepository extends JpaRepository<RatingRule,Long> {
    List<RatingRule> findByProduct_ProductId(Long productId);
}
