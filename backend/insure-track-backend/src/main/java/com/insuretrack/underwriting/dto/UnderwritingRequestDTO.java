package com.insuretrack.underwriting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnderwritingRequestDTO {
    private Long quoteId;
   // private Long customerId;
}
