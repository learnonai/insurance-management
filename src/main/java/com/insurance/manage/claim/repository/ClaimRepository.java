package com.insurance.manage.claim.repository;

import com.insurance.manage.claim.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    //you can add custom method or queries for next level functionality.
}
