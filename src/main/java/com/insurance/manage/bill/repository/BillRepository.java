package com.insurance.manage.bill.repository;

import com.insurance.manage.bill.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    List<Bill> findByPolicyNumber(String policyNumber);
}
