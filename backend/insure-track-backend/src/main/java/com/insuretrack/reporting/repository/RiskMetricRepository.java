package com.insuretrack.reporting.repository;

import com.insuretrack.reporting.entity.RiskMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskMetricRepository
        extends JpaRepository<RiskMetric, Long> {
}
