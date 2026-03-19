package com.insuretrack.reporting.service;

import com.insuretrack.common.enums.ReportStatus;
import com.insuretrack.reporting.dto.RegulatoryReportRequestDTO;
import com.insuretrack.reporting.dto.RegulatoryReportResponseDTO;
import com.insuretrack.reporting.entity.RegulatoryReport;
import com.insuretrack.reporting.repository.RegulatoryReportRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional
public class RegulatoryReportServiceImpl
        implements RegulatoryReportService {

    private final RegulatoryReportRepository reportRepository;

    @Override
    public RegulatoryReportResponseDTO generateReport(
            RegulatoryReportRequestDTO dto) {

        RegulatoryReport report = RegulatoryReport.builder()
                .reportType(dto.getReportType())
                .period(dto.getPeriod())
                .generatedDate(LocalDate.now())
                .status(ReportStatus.DRAFT)
                .build();

        reportRepository.save(report);

        return mapToResponse(report);
    }

    @Override
    public RegulatoryReportResponseDTO submitReport(Long reportId) {

        RegulatoryReport report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));

        report.setStatus(ReportStatus.SUBMITTED);

        return mapToResponse(report);
    }

    private RegulatoryReportResponseDTO mapToResponse(
            RegulatoryReport report) {

        return RegulatoryReportResponseDTO.builder()
                .reportId(report.getReportId())
                .reportType(report.getReportType())
                .period(report.getPeriod())
                .generatedDate(report.getGeneratedDate())
                .status(report.getStatus())
                .build();
    }
}
