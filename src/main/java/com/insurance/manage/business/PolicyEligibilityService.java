package com.insurance.manage.business;

import com.insurance.manage.auth.model.User;
import com.insurance.manage.policy.dto.PolicyRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class PolicyEligibilityService {

    public boolean isEligibleForPolicy(User user, PolicyRequest request) {
        return isValidPolicyDuration(request.getStartDate(), request.getEndDate()) &&
               isValidPolicyType(request.getType()) &&
               isUserEligible(user, request.getType());
    }
    
    private boolean isValidPolicyDuration(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        int months = period.getMonths() + (period.getYears() * 12);
        return months >= 6 && months <= 60; // 6 months to 5 years
    }
    
    private boolean isValidPolicyType(String type) {
        return type.matches("HEALTH|AUTO|LIFE");
    }
    
    private boolean isUserEligible(User user, String policyType) {
        // Business rule: Users can have max 3 policies of same type
        long existingPoliciesCount = user.getPolicies() != null ? 
            user.getPolicies().stream().filter(p -> p.getType().equals(policyType)).count() : 0;
        return existingPoliciesCount < 3;
    }
    
    public String getEligibilityMessage(User user, PolicyRequest request) {
        if (!isValidPolicyDuration(request.getStartDate(), request.getEndDate())) {
            return "Policy duration must be between 6 months and 5 years";
        }
        if (!isValidPolicyType(request.getType())) {
            return "Invalid policy type. Must be HEALTH, AUTO, or LIFE";
        }
        if (!isUserEligible(user, request.getType())) {
            return "User already has maximum policies of this type";
        }
        return "Eligible";
    }
}