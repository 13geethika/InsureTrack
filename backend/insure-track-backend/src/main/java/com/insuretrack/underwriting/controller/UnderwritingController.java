package com.insuretrack.underwriting.controller;

import com.insuretrack.common.enums.UnderwritingDecision;
import com.insuretrack.underwriting.dto.UnderwritingDecisionDTO;
import com.insuretrack.underwriting.dto.UnderwritingRequestDTO;
import com.insuretrack.underwriting.dto.UnderwritingResponseDTO;
import com.insuretrack.underwriting.entity.UnderwritingCase;
import com.insuretrack.underwriting.repository.UnderwritingCaseRepository;
import com.insuretrack.underwriting.service.UnderwritingService;
import com.insuretrack.user.entity.AuditLog;
import com.insuretrack.user.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/underwriter")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UnderwritingController {

    private final UnderwritingService underwritingService;
    private final UnderwritingCaseRepository underwritingRepository;
    private final AuditLogRepository auditLogRepository;

    // Create case
    @PostMapping("/{quoteId}")
    public UnderwritingResponseDTO createCase(
            @PathVariable Long quoteId) {

        return underwritingService.createCase(quoteId);
    }

    // Get ALL cases (Pending, Approved, Rejected)
    @GetMapping("/all")
    public List<UnderwritingResponseDTO> getAll() {
        return underwritingService.getAllCases();
    }
    // Get pending cases
    @GetMapping("/pending")
    public
    List<UnderwritingResponseDTO> getPending() {
        return underwritingService.getPendingCases();
    }
    
    @PutMapping("/decide/{caseId}")
    public ResponseEntity<UnderwritingResponseDTO> decide(@PathVariable Long caseId,@RequestBody UnderwritingDecisionDTO request){
        return ResponseEntity.ok(underwritingService.makeDecision(caseId,request));
    }
    // Get statistics for the dashboard cards
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        // Fetch DTOs directly from service so we don't have to map manually here
        List<UnderwritingResponseDTO> allCases = underwritingService.getAllCases();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProposals", allCases.size());

        // Using DTO properties directly
        stats.put("pendingReview", allCases.stream().filter(c -> "PENDING".equals(c.getDecision())).count());
        stats.put("approved", allCases.stream().filter(c -> "APPROVE".equals(c.getDecision())).count());
        stats.put("declined", allCases.stream().filter(c -> "DECLINE".equals(c.getDecision())).count());

        // Product Distribution (Using PolicyType or ProductName from the DTO)
        Map<String, Long> productTypeData = allCases.stream()
                .collect(Collectors.groupingBy(UnderwritingResponseDTO::getPolicyType, Collectors.counting()));
        stats.put("productDistribution", productTypeData);

        // Recent Activity (Already DTOs, just need to sort and limit)
        List<UnderwritingResponseDTO> recentActivity = allCases.stream()
                .sorted(Comparator.comparing(UnderwritingResponseDTO::getDecisionDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .toList();
        stats.put("recentActivity", recentActivity);

        return ResponseEntity.ok(stats);
    }

    // Get cases by ID for the Risk Assessment page
    @GetMapping("/case/{caseId}")
    public ResponseEntity<UnderwritingResponseDTO> getCaseById(@PathVariable Long caseId) {
        return ResponseEntity.ok(underwritingService.getCase(caseId));
    }
    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> getProfile() {
        return ResponseEntity.ok(underwritingService.getUnderwriterProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody Map<String, String> updates) {
        // Call the service to save the new name/details to the DB
        underwritingService.updateProfile(updates);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile updated successfully");
        return ResponseEntity.ok(response);

    }
//    @GetMapping("/audit-logs")
//    public List<Map<String, Object>> getAuditLogs() {
//        String currentUserEmail = org.springframework.security.core.context.SecurityContextHolder
//                .getContext().getAuthentication().getName();
//
//        // This now returns the list of 75 (or more) logs safely
//        List<AuditLog> logs = auditLogRepository.findByUserEmailOrderByTimestampDesc(currentUserEmail);
//
//        return logs.stream()
//                .limit(10) // Only show the last 10 logs to the user for better performance
//                .map(log -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("action", log.getAction());
//                    map.put("timestamp", log.getTimestamp());
//                    return map;
//                })
//                .toList();
//    }
    @GetMapping("/audit-logs")
    public ResponseEntity<Page<Map<String, Object>>> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    String currentUserEmail = org.springframework.security.core.context.SecurityContextHolder
            .getContext().getAuthentication().getName();

    Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
    Page<AuditLog> auditPage = auditLogRepository.findByUserEmail(currentUserEmail, pageable);

    return ResponseEntity.ok(auditPage.map(log -> {
        Map<String, Object> map = new HashMap<>();
        map.put("action", log.getAction());
        map.put("timestamp", log.getTimestamp());
        return map;
    }));
}
}