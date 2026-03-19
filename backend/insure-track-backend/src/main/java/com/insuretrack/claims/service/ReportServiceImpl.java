package com.insuretrack.claims.service;

import com.insuretrack.claims.dto.ReportSummaryDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.ReserveRepository;
import com.insuretrack.claims.repository.SettlementRepository;
import com.insuretrack.common.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ClaimRepository     claimRepository;
    private final ReserveRepository   reserveRepository;
    private final SettlementRepository settlementRepository;

    @Override
    public ReportSummaryDTO getSummary() {
        List<Claim> allClaims = claimRepository.findAll();

        // Count by status
        Map<ClaimStatus, Long> counts = allClaims.stream()
                .collect(Collectors.groupingBy(Claim::getStatus, Collectors.counting()));

        // Claims by status list for chart
        List<ReportSummaryDTO.StatusCount> byStatus = counts.entrySet().stream()
                .map(e -> new ReportSummaryDTO.StatusCount(e.getKey().name(), e.getValue()))
                .collect(Collectors.toList());

        // Total reserve amount
        double totalReserve = reserveRepository.findAll().stream()
                .mapToDouble(r -> r.getAmount() != null ? r.getAmount() : 0)
                .sum();

        // Total settlement amount
        double totalSettlement = settlementRepository.findAll().stream()
                .mapToDouble(s -> s.getSettlementAmount() != null ? s.getSettlementAmount() : 0)
                .sum();

        // Recent claims — last 10, most recent first
        List<ReportSummaryDTO.RecentClaim> recent = allClaims.stream()
                .sorted((a, b) -> {
                    if (a.getReportedDate() == null) return 1;
                    if (b.getReportedDate() == null) return -1;
                    return b.getReportedDate().compareTo(a.getReportedDate());
                })
                .limit(10)
                .map(c -> new ReportSummaryDTO.RecentClaim(
                        c.getClaimId(),
                        c.getClaimType(),
                        c.getStatus().name(),
                        c.getReportedDate() != null ? c.getReportedDate().toString() : ""
                ))
                .collect(Collectors.toList());

        return ReportSummaryDTO.builder()
                .totalClaims(allClaims.size())
                .openClaims(counts.getOrDefault(ClaimStatus.OPEN, 0L))
                .investigatingClaims(counts.getOrDefault(ClaimStatus.INVESTIGATING, 0L))
                .settledClaims(counts.getOrDefault(ClaimStatus.SETTLED, 0L))
                .deniedClaims(counts.getOrDefault(ClaimStatus.DENIED, 0L))
                .closedClaims(counts.getOrDefault(ClaimStatus.CLOSED, 0L))
                .totalReserveAmount(totalReserve)
                .totalSettlementAmount(totalSettlement)
                .claimsByStatus(byStatus)
                .recentClaims(recent)
                .build();
    }
}