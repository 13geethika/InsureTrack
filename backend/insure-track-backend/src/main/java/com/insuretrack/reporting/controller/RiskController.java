package com.insuretrack.reporting.controller;

import com.insuretrack.reporting.dto.RegulatoryReportRequestDTO;
import com.insuretrack.reporting.dto.RegulatoryReportResponseDTO;
import com.insuretrack.reporting.dto.RiskMetricResponseDTO;
import com.insuretrack.reporting.service.RegulatoryReportService;
import com.insuretrack.reporting.service.RiskMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskMetricService riskMetricService;
    private final RegulatoryReportService reportService;

    @PostMapping("/metrics/portfolio")
    public RiskMetricResponseDTO generateMetrics() {
        return riskMetricService.generatePortfolioMetrics();
    }

    @PostMapping("/reports")
    public RegulatoryReportResponseDTO generateReport(
            @RequestBody RegulatoryReportRequestDTO dto) {

        return reportService.generateReport(dto);
    }

    @PutMapping("/reports/{id}/submit")
    public RegulatoryReportResponseDTO submitReport(
            @PathVariable Long id) {

        return reportService.submitReport(id);
    }
}

