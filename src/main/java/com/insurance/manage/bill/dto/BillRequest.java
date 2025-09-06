package com.insurance.manage.bill.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BillRequest {
    @NotBlank(message = "Bill number is required")
    private String billNumber;
    
    @Positive(message = "Amount must be positive")
    private Double amount;
    
    @Pattern(regexp = "PENDING|PAID|OVERDUE", message = "Status must be PENDING, PAID, or OVERDUE")
    private String status = "PENDING";
    
    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in future")
    private LocalDate dueDate;
    
    @NotNull(message = "Policy ID is required")
    private Long policyId;
}