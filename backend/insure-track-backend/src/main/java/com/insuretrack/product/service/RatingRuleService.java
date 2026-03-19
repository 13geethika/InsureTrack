package com.insuretrack.product.service;

import com.insuretrack.common.enums.Status;
import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.product.entity.Product;
import com.insuretrack.product.entity.RatingRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingRuleService {

    private final ObjectMapper objectMapper;

    public Double calculatePremium(Product product,
                                   InsuredObject insuredObject,
                                   List<RatingRule> rules) {

        if (product == null) {
            throw new RuntimeException("Product cannot be null");
        }

        if (product.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Product is not active");
        }

        if (insuredObject == null) {
            throw new RuntimeException("Insured object cannot be null");
        }

        double basePremium = insuredObject.getValuation() != 0.0
                ? insuredObject.getValuation()
                : 0.0;

        if (rules == null || rules.isEmpty()) {
            return basePremium;
        }

        Map<String, Object> detailsMap;

        try {
            detailsMap = objectMapper.readValue(
                    insuredObject.getDetailsJson(),
                    new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON in insured object details", e);
        }

        double adjustment = 0.0;

        for (RatingRule rule : rules) {
            Object value = detailsMap.get(rule.getFactor().toLowerCase());

            try {
                double numericValue = Double.parseDouble(value.toString());
                adjustment += numericValue * rule.getWeight();
                System.out.println(adjustment);
            } catch (NumberFormatException ex) {
                adjustment += handleNonNumericFactor(rule.getFactor(), value.toString(), rule.getWeight());
            }
        }
        System.out.println(adjustment);
        return basePremium * adjustment;
    }

    private double handleNonNumericFactor(String factor,
                                          String value,
                                          Double weight) {

        switch (factor.toLowerCase()) {
            case "age":
                return 2.0*weight;

            case "vehicletype":
                if (value.equalsIgnoreCase("car")) return 2.0 * weight;
                if (value.equalsIgnoreCase("bike")) return 1.5 * weight;
                if (value.equalsIgnoreCase("bus")) return 3.0 * weight;
                return 1.0 * weight;

            case "location":
                if (value.equalsIgnoreCase("urban")) return 2.0 * weight;
                if (value.equalsIgnoreCase("rural")) return 1.0 * weight;
                return 1.5 * weight;

            default:
                return 1.1;
        }
    }
}
