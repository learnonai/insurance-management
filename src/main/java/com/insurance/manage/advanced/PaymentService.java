package com.insurance.manage.advanced;

import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class PaymentService {

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private NotificationService notificationService;

    public String processBillPayment(Long billId, Double paymentAmount, String paymentMethod) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        
        if (!canPayBill(bill)) {
            throw new RuntimeException("Bill cannot be paid. Status: " + bill.getStatus());
        }
        
        if (!paymentAmount.equals(bill.getAmount())) {
            throw new RuntimeException("Payment amount does not match bill amount");
        }
        
        // Simulate payment processing
        boolean paymentSuccess = processPayment(paymentAmount, paymentMethod);
        
        if (paymentSuccess) {
            bill.setStatus("PAID");
            billRepository.save(bill);
            
            return notificationService.generatePaymentConfirmation(bill.getBillNumber(), paymentAmount);
        } else {
            throw new RuntimeException("Payment processing failed");
        }
    }
    
    public Double calculateLateFee(Bill bill) {
        if (bill.getStatus().equals("OVERDUE")) {
            long daysOverdue = bill.getDueDate().until(LocalDate.now()).getDays();
            return bill.getAmount() * 0.05 * (daysOverdue / 30.0); // 5% per month
        }
        return 0.0;
    }
    
    public String getPaymentStatus(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        
        return switch (bill.getStatus()) {
            case "PAID" -> "Payment completed";
            case "PENDING" -> "Payment pending";
            case "OVERDUE" -> "Payment overdue - late fees may apply";
            default -> "Unknown status";
        };
    }
    
    private boolean canPayBill(Bill bill) {
        return bill.getStatus().equals("PENDING") || bill.getStatus().equals("OVERDUE");
    }
    
    private boolean processPayment(Double amount, String paymentMethod) {
        // Simulate payment gateway integration
        // In real implementation, integrate with Stripe, PayPal, etc.
        
        if (amount <= 0) return false;
        if (paymentMethod == null || paymentMethod.isEmpty()) return false;
        
        // Simulate 95% success rate
        return Math.random() > 0.05;
    }
}