package com.insurance.manage.bill.repository;

import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    List<Bill> findByPolicyId(Long policyId);
    List<Bill> findByPolicyPolicyNumber(String policyNumber);
    List<Bill> findByStatus(String status);
    boolean existsByPolicyAndDueDateBetween(Policy policy, LocalDate startDate, LocalDate endDate);
    boolean existsByPolicyAndBillNumberContaining(Policy policy, String keyword);
}
