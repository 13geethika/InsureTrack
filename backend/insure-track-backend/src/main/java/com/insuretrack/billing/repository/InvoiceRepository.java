//package com.insuretrack.billing.repository;
//
//import com.insuretrack.billing.entity.Invoice;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
//    List<Invoice> findByPolicyPolicyId(Long policyId);
//}


package com.insuretrack.billing.repository;

import com.insuretrack.billing.entity.Invoice;
import com.insuretrack.billing.dto.InvoiceResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Existing method if you still want raw Invoice entities
    List<Invoice> findByPolicyPolicyId(Long policyId);

    @Query("SELECT new com.insuretrack.billing.dto.InvoiceResponseDTO(" +
            "i.invoiceId, i.policy.policyId, i.amount, i.issueDate, i.dueDate, i.status, c.name) " +
            "FROM Invoice i " +
            "JOIN i.policy p " +
            "JOIN p.quote q " +
            "JOIN q.customer c "+
            "WHERE i.policy.policyId = :policyId")
    List<InvoiceResponseDTO> findInvoicesWithCustomer(@Param("policyId") Long policyId);


    @Query("SELECT new com.insuretrack.billing.dto.InvoiceResponseDTO(" +
            "i.invoiceId, i.policy.policyId, i.amount, i.issueDate, i.dueDate, i.status, c.name) " +
            "FROM Invoice i " +
            "JOIN i.policy p " +
            "JOIN p.quote q " +
            "JOIN q.customer c")
    List<InvoiceResponseDTO> findAllInvoicesWithCustomer();


}
