package com.insurance.manage.advanced;

import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.repository.BillRepository;
import com.insurance.manage.business.BillGenerationService;
import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AutoBillGenerationService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private BillGenerationService billGenerationService;

    @Scheduled(cron = "0 0 9 1 * ?") // 9 AM on 1st of every month
    public void generateMonthlyBills() {
        List<Policy> activePolicies = getActivePolicies();
        
        for (Policy policy : activePolicies) {
            if (!hasMonthlyBillForCurrentMonth(policy)) {
                Bill monthlyBill = billGenerationService.generateMonthlyBill(policy);
                billRepository.save(monthlyBill);
            }
        }
    }
    
    @Scheduled(cron = "0 0 9 * * ?") // 9 AM daily
    public void generateRenewalBills() {
        List<Policy> policiesExpiringIn30Days = policyRepository.findByEndDateBetween(
            LocalDate.now().plusDays(25), LocalDate.now().plusDays(35));
        
        for (Policy policy : policiesExpiringIn30Days) {
            if (!hasRenewalBill(policy)) {
                Bill renewalBill = billGenerationService.generateRenewalBill(policy);
                billRepository.save(renewalBill);
            }
        }
    }
    
    @Scheduled(cron = "0 0 10 * * ?") // 10 AM daily
    public void updateOverdueBills() {
        List<Bill> pendingBills = billRepository.findByStatus("PENDING");
        
        for (Bill bill : pendingBills) {
            String updatedStatus = billGenerationService.updateBillStatus(bill);
            if (!updatedStatus.equals(bill.getStatus())) {
                bill.setStatus(updatedStatus);
                billRepository.save(bill);
            }
        }
    }
    
    private List<Policy> getActivePolicies() {
        LocalDate now = LocalDate.now();
        return policyRepository.findByStartDateBeforeAndEndDateAfter(now, now);
    }
    
    private boolean hasMonthlyBillForCurrentMonth(Policy policy) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        
        return billRepository.existsByPolicyAndDueDateBetween(policy, startOfMonth, endOfMonth);
    }
    
    private boolean hasRenewalBill(Policy policy) {
        return billRepository.existsByPolicyAndBillNumberContaining(policy, "RENEWAL");
    }
}