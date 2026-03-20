package com.insuretrack.underwriting.repository;

import com.insuretrack.underwriting.entity.UnderwritingCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCase,Long> {
    Optional<UnderwritingCase> findByQuoteQuoteId(Long quoteId);
}
