package com.insuretrack.quote.repository;

import com.insuretrack.quote.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote,Long> {
}
