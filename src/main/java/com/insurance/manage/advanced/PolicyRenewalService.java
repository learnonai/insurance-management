package com.insurance.manage.advanced;

import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PolicyRenewalService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy renewPolicy(Long policyId, int renewalYears) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        
        if (!canRenewPolicy(policy)) {
            throw new RuntimeException("Policy cannot be renewed");
        }
        
        LocalDate newEndDate = policy.getEndDate().plusYears(renewalYears);
        policy.setEndDate(newEndDate);
        
        return policyRepository.save(policy);
    }
    
    public List<Policy> getPoliciesExpiringIn30Days() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return policyRepository.findByEndDateBefore(thirtyDaysFromNow);
    }
    
    public List<Policy> getExpiredPolicies() {
        return policyRepository.findByEndDateBefore(LocalDate.now());
    }
    
    private boolean canRenewPolicy(Policy policy) {
        return policy.getEndDate().isAfter(LocalDate.now().minusYears(1));
    }
    
    public boolean isPolicyExpiringSoon(Policy policy) {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return policy.getEndDate().isBefore(thirtyDaysFromNow);
    }
}