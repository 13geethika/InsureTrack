package com.insuretrack.reporting.service;

import com.insuretrack.reporting.dto.RegulatoryReportRequestDTO;
import com.insuretrack.reporting.dto.RegulatoryReportResponseDTO;

public interface RegulatoryReportService {

    RegulatoryReportResponseDTO generateReport(
            RegulatoryReportRequestDTO dto);

    RegulatoryReportResponseDTO submitReport(Long reportId);
}
