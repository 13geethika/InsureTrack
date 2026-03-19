package com.insuretrack.quote.service;
import com.insuretrack.common.enums.QuoteStatus;
import com.insuretrack.common.enums.UnderwritingDecision;
import com.insuretrack.customer.dto.RiskAssessmentRequestDTO;
import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.customer.repository.CustomerRepository;
import com.insuretrack.customer.repository.InsuredObjectRepository;
import com.insuretrack.product.entity.Product;
import com.insuretrack.product.entity.RatingRule;
import com.insuretrack.product.repository.ProductRepository;
import com.insuretrack.product.repository.RatingRuleRepository;
import com.insuretrack.product.service.RatingRuleService;
import com.insuretrack.quote.dto.QuoteRequestDTO;
import com.insuretrack.quote.dto.QuoteResponseDTO;
import com.insuretrack.quote.entity.Quote;
import com.insuretrack.quote.repository.QuoteRepository;
import com.insuretrack.underwriting.entity.UnderwritingCase;
import com.insuretrack.underwriting.repository.UnderwritingCaseRepository;
import com.insuretrack.underwriting.service.UnderwritingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {


    private final QuoteRepository quoteRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InsuredObjectRepository insuredObjectRepository;
    private final RatingRuleService ratingEngineService;
    private final RatingRuleRepository ratingRuleRepository;
    private final UnderwritingService underwritingService;

    @Override
    public QuoteResponseDTO createQuote(QuoteRequestDTO request) {

        Quote quote = new Quote();
        quote.setCustomer(customerRepository.findById(request.getCustomerId()).orElseThrow());
        quote.setProduct(productRepository.findById(request.getProductId()).orElseThrow());
        quote.setInsuredObject(
                insuredObjectRepository.findById(request.getInsuredObjectId()).orElseThrow()
        );
        quote.setCoveragesJSON(request.getCoveragesJSON());
        quote.setCreatedDate(LocalDateTime.now());
        quote.setStatus(QuoteStatus.DRAFT);

        quoteRepository.save(quote);
        return mapToResponse(quote);
    }
    @Override
    public QuoteResponseDTO submitQuote(Long quoteId) {

        Quote quote = quoteRepository.findById(quoteId).orElseThrow();
        if(quote.getStatus().name()=="SUBMITTED"){
            throw new RuntimeException("Already in pending state");
        }
        quote.setStatus(QuoteStatus.SUBMITTED);
        underwritingService.createCase(quoteId);
        return mapToResponse(quote);
    }

    @Override
    public QuoteResponseDTO rateQuote(Long quoteId) {

        Quote quote = quoteRepository.findById(quoteId).orElseThrow();

        Double premium = ratingEngineService.calculatePremium(quote.getProduct(),quote.getInsuredObject(),quote.getProduct().getRatingRules());

        quote.setPremium(premium);
        quote.setStatus(QuoteStatus.RATED);

        return mapToResponse(quote);
    }

    @Override
    public List<QuoteResponseDTO> findAllQuotes() {
        return quoteRepository.findAll()
                .stream()
                .map(this::mapToResponse) // Uses your existing mapping method
                .toList();
    }



    private QuoteResponseDTO mapToResponse(Quote quote) {

        QuoteResponseDTO dto = new QuoteResponseDTO();
        dto.setQuoteId(quote.getQuoteId());
        dto.setPremium(quote.getPremium());
        dto.setStatus(quote.getStatus().name());
        dto.setCreatedDate(quote.getCreatedDate());

        return dto;
    }

}