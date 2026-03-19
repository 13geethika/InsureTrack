package com.insuretrack.claims.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDTO {
    private long totalClaims;
    private long openClaims;
    private long investigatingClaims;
    private long settledClaims;
    private long deniedClaims;
    private long closedClaims;
    private double totalReserveAmount;
    private double totalSettlementAmount;
    private List<StatusCount> claimsByStatus;
    private List<RecentClaim> recentClaims;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatusCount {
        private String status;
        private long count;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecentClaim {
        private Long claimId;
        private String claimType;
        private String status;
        private String reportedDate;
    }
}