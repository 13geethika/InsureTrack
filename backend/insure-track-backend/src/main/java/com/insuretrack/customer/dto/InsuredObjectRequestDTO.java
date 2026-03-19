package com.insuretrack.customer.dto;

import com.insuretrack.common.enums.ObjectType;

import java.math.BigDecimal;
import java.util.Map;

public record InsuredObjectRequestDTO(ObjectType objectType, Map<String,Object> detailsJson) {
}
