package com.insurance.manage.business;

import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.policy.model.Policy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BillGenerationService {

    public Bill generateMonthlyBill(Policy policy) {
        String billNumber = generateBillNumber(policy);
        Double monthlyAmount = calculateMonthlyAmount(policy);
        LocalDate dueDate = LocalDate.now().plusDays(30);
        
        return Bill.builder()
                .billNumber(billNumber)
                .amount(monthlyAmount)
                .status("PENDING")
                .dueDate(dueDate)
                .policy(policy)
                .build();
    }
    
    public Bill generateRenewalBill(Policy policy) {
        String billNumber = generateBillNumber(policy);
        Double renewalAmount = policy.getPremium();
        LocalDate dueDate = policy.getEndDate().minusDays(30); // 30 days before expiry
        
        return Bill.builder()
                .billNumber(billNumber)
                .amount(renewalAmount)
                .status("PENDING")
                .dueDate(dueDate)
                .policy(policy)
                .build();
    }
    
    private String generateBillNumber(Policy policy) {
        return "BILL-" + policy.getPolicyNumber() + "-" + 
               LocalDate.now().getYear() + "-" + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private Double calculateMonthlyAmount(Policy policy) {
        // Divide annual premium by 12 for monthly billing
        return policy.getPremium() / 12.0;
    }
    
    public String updateBillStatus(Bill bill) {
        LocalDate now = LocalDate.now();
        
        if (bill.getStatus().equals("PENDING") && now.isAfter(bill.getDueDate())) {
            return "OVERDUE";
        }
        
        return bill.getStatus();
    }
    
    public boolean canPayBill(Bill bill) {
        return bill.getStatus().equals("PENDING") || bill.getStatus().equals("OVERDUE");
    }
}