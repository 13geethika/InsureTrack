package com.insuretrack.reporting.service;

import com.insuretrack.reporting.dto.RiskMetricResponseDTO;

public interface RiskMetricService {

    RiskMetricResponseDTO generatePortfolioMetrics();
}
