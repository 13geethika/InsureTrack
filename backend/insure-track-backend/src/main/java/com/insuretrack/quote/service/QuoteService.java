package com.insuretrack.quote.service;


import com.insuretrack.quote.dto.QuoteRequestDTO;
import com.insuretrack.quote.dto.QuoteResponseDTO;

import java.util.List;


public interface QuoteService {
    QuoteResponseDTO createQuote(QuoteRequestDTO request);

    QuoteResponseDTO submitQuote(Long quoteId);

    QuoteResponseDTO rateQuote(Long quoteId);
    List<QuoteResponseDTO> findAllQuotes();

}
