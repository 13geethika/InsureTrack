package com.insuretrack.reporting.service;

import com.insuretrack.billing.entity.Invoice;
import com.insuretrack.billing.repository.InvoiceRepository;
import com.insuretrack.claims.entity.Settlement;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.SettlementRepository;
import com.insuretrack.common.enums.InvoiceStatus;
import com.insuretrack.policy.repository.PolicyRepository;
import com.insuretrack.reporting.dto.RiskMetricResponseDTO;
import com.insuretrack.reporting.entity.RiskMetric;
import com.insuretrack.reporting.repository.RiskMetricRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.*;
@Service
@AllArgsConstructor
@Transactional
public class RiskMetricServiceImpl implements RiskMetricService {

    private final SettlementRepository settlementRepository;
    private final InvoiceRepository invoiceRepository;
    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final RiskMetricRepository riskMetricRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RiskMetricResponseDTO generatePortfolioMetrics() {

        Double totalClaimsPaid = settlementRepository.findAll()
                .stream()
                .mapToDouble(Settlement::getSettlementAmount)
                .sum();

        Double totalPremium = invoiceRepository.findAll()
                .stream()
                .filter(i -> i.getStatus() == InvoiceStatus.PAID)
                .mapToDouble(Invoice::getAmount)
                .sum();

        long totalClaims = claimRepository.count();
        long totalPolicies = policyRepository.count();

        Double lossRatio = totalPremium == 0 ? 0 :
                totalClaimsPaid / totalPremium;

        Double averageSeverity = totalClaims == 0 ? 0 :
                totalClaimsPaid / totalClaims;

        Double frequency = totalPolicies == 0 ? 0 :
                (double) totalClaims / totalPolicies;

        Double expenseRatio = 0.10; // assumed 10%
        Double combinedRatio = lossRatio + expenseRatio;

        try {

            Map<String, Object> metricsMap = new HashMap<>();
            metricsMap.put("lossRatio", lossRatio);
            metricsMap.put("combinedRatio", combinedRatio);
            metricsMap.put("averageSeverity", averageSeverity);
            metricsMap.put("frequency", frequency);

            String metricsJson =
                    objectMapper.writeValueAsString(metricsMap);

            RiskMetric metric = RiskMetric.builder()
                    .scope("PORTFOLIO")
                    .metrics(metricsJson)
                    .asOfDate(LocalDate.now())
                    .build();

            riskMetricRepository.save(metric);

            return RiskMetricResponseDTO.builder()
                    .metricId(metric.getMetricId())
                    .scope(metric.getScope())
                    .metrics(metric.getMetrics())
                    .asOfDate(metric.getAsOfDate())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error generating metrics");
        }
    }
}
