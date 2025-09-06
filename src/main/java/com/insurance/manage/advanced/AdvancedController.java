package com.insurance.manage.advanced;

import com.insurance.manage.bill.repository.BillRepository;
import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.claim.repository.ClaimRepository;
import com.insurance.manage.policy.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/advanced")
public class AdvancedController {

    @Autowired
    private PolicyRenewalService renewalService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private BillRepository billRepository;

    @PostMapping("/policies/{id}/renew")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Policy renewPolicy(@PathVariable Long id, @RequestParam int years) {
        return renewalService.renewPolicy(id, years);
    }
    
    @GetMapping("/policies/expiring")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public List<Policy> getPoliciesExpiringSoon() {
        return renewalService.getPoliciesExpiringIn30Days();
    }
    
    @GetMapping("/policies/expired")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Policy> getExpiredPolicies() {
        return renewalService.getExpiredPolicies();
    }
    
    @PostMapping("/bills/{id}/pay")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public String payBill(@PathVariable Long id, 
                         @RequestParam Double amount,
                         @RequestParam String paymentMethod) {
        return paymentService.processBillPayment(id, amount, paymentMethod);
    }
    
    @GetMapping("/bills/{id}/late-fee")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public Double getLateFee(@PathVariable Long id) {
        return paymentService.calculateLateFee(
            billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found")));
    }
    
    @GetMapping("/notifications/policy-alerts")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public List<String> getPolicyAlerts() {
        List<Policy> expiring = renewalService.getPoliciesExpiringIn30Days();
        return notificationService.generatePolicyExpirationAlerts(expiring);
    }
    
    @GetMapping("/notifications/claim-status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<String> getClaimNotifications() {
        List<Claim> claims = claimRepository.findAll();
        return notificationService.generateClaimStatusNotifications(claims);
    }
    
    @PostMapping("/generate-bills")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public String generateBills() {
        return "Bills generated successfully";
    }
    
    @GetMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<String> getAllNotifications() {
        List<Policy> expiring = renewalService.getPoliciesExpiringIn30Days();
        List<Claim> claims = claimRepository.findAll();
        List<String> notifications = notificationService.generatePolicyExpirationAlerts(expiring);
        notifications.addAll(notificationService.generateClaimStatusNotifications(claims));
        return notifications;
    }
}