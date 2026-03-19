package com.insuretrack.claims.controller;

import com.insuretrack.claims.dto.ReportSummaryDTO;
import com.insuretrack.claims.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adjuster/reports")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ReportSummaryDTO getSummary() {
        return reportService.getSummary();
    }
}