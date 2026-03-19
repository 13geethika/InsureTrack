package com.insuretrack.user.repository;

import com.insuretrack.user.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
    @Override
    Optional<AuditLog> findById(Long aLong);
    List<AuditLog> findByUserEmailOrderByTimestampDesc(String email);
    // Spring will automatically handle the "75 results" by only fetching the requested "size"
    Page<AuditLog> findByUserEmail(String email, Pageable pageable);
}
