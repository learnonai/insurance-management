package com.insurance.manage.policy.repository;

import com.insurance.manage.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {

    //Custom queries (if needed) can go here.
}
