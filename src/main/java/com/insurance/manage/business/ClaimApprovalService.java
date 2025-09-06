package com.insurance.manage.business;

import com.insurance.manage.claim.dto.ClaimRequest;
import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.policy.model.Policy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ClaimApprovalService {

    public String processClaimApproval(Policy policy, ClaimRequest request) {
        if (!isPolicyActive(policy)) {
            return "REJECTED";
        }
        
        if (!isClaimAmountValid(policy, request.getClaimAmount())) {
            return "REJECTED";
        }
        
        if (!isClaimTimely(policy, request.getDateFiled())) {
            return "REJECTED";
        }
        
        // Auto-approve small claims, manual review for large ones
        return request.getClaimAmount() <= 10000.0 ? "APPROVED" : "PENDING";
    }
    
    private boolean isPolicyActive(Policy policy) {
        LocalDate now = LocalDate.now();
        return !now.isBefore(policy.getStartDate()) && !now.isAfter(policy.getEndDate());
    }
    
    private boolean isClaimAmountValid(Policy policy, Double claimAmount) {
        // Claim cannot exceed 10x the annual premium
        return claimAmount <= (policy.getPremium() * 10);
    }
    
    private boolean isClaimTimely(Policy policy, LocalDate dateFiled) {
        // Claim must be filed within policy period
        return !dateFiled.isBefore(policy.getStartDate()) && !dateFiled.isAfter(policy.getEndDate());
    }
    
    public boolean canUpdateClaimStatus(Claim claim, String newStatus) {
        String currentStatus = claim.getStatus();
        
        // Business rules for status transitions
        return switch (currentStatus) {
            case "PENDING" -> newStatus.equals("APPROVED") || newStatus.equals("REJECTED");
            case "APPROVED", "REJECTED" -> false; // Final states
            default -> false;
        };
    }
}