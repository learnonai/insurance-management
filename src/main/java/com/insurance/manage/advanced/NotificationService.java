package com.insurance.manage.advanced;

import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.policy.model.Policy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    public List<String> generatePolicyExpirationAlerts(List<Policy> policies) {
        List<String> alerts = new ArrayList<>();
        
        for (Policy policy : policies) {
            long daysUntilExpiry = LocalDate.now().until(policy.getEndDate()).getDays();
            
            if (daysUntilExpiry <= 30 && daysUntilExpiry > 0) {
                alerts.add(String.format("Policy %s expires in %d days. Renewal required.", 
                    policy.getPolicyNumber(), daysUntilExpiry));
            } else if (daysUntilExpiry <= 0) {
                alerts.add(String.format("Policy %s has expired. Immediate renewal required.", 
                    policy.getPolicyNumber()));
            }
        }
        
        return alerts;
    }
    
    public List<String> generateClaimStatusNotifications(List<Claim> claims) {
        List<String> notifications = new ArrayList<>();
        
        for (Claim claim : claims) {
            switch (claim.getStatus()) {
                case "APPROVED":
                    notifications.add(String.format("Claim %s has been approved. Amount: $%.2f", 
                        claim.getClaimNumber(), claim.getClaimAmount()));
                    break;
                case "REJECTED":
                    notifications.add(String.format("Claim %s has been rejected. Please contact support.", 
                        claim.getClaimNumber()));
                    break;
                case "PENDING":
                    long daysPending = claim.getDateFiled().until(LocalDate.now()).getDays();
                    if (daysPending > 7) {
                        notifications.add(String.format("Claim %s is pending for %d days. Under review.", 
                            claim.getClaimNumber(), daysPending));
                    }
                    break;
            }
        }
        
        return notifications;
    }
    
    public String generatePaymentConfirmation(String billNumber, Double amount) {
        return String.format("Payment of $%.2f for bill %s has been processed successfully.", 
            amount, billNumber);
    }
    
    public String generatePolicyRenewalConfirmation(String policyNumber, LocalDate newEndDate) {
        return String.format("Policy %s has been renewed until %s.", 
            policyNumber, newEndDate.toString());
    }
}