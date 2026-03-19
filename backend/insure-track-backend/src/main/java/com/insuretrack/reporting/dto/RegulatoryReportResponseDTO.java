package com.insuretrack.reporting.dto;

import com.insuretrack.common.enums.ReportStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegulatoryReportResponseDTO {

    private Long reportId;
    private String reportType;
    private String period;
    private LocalDate generatedDate;
    private ReportStatus status;
}
