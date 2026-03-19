package com.insuretrack.quote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRequestDTO {
    private Long customerId;
    private Long productId;
    private Long insuredObjectId;
    private String coveragesJSON;
}
