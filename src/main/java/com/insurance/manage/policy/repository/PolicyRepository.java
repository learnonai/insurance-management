package com.insurance.manage.policy.repository;

import com.insurance.manage.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {
    
    List<Policy> findByEndDateBefore(LocalDate date);
    List<Policy> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
    List<Policy> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
}
