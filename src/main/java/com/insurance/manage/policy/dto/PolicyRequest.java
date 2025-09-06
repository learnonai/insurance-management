package com.insurance.manage.policy.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PolicyRequest {
    @NotBlank(message = "Policy number is required")
    private String policyNumber;
    
    @NotBlank(message = "Policy holder name is required")
    private String policyHolderName;
    
    @NotBlank(message = "Policy type is required")
    @Pattern(regexp = "HEALTH|AUTO|LIFE", message = "Policy type must be HEALTH, AUTO, or LIFE")
    private String type;
    
    @Positive(message = "Premium must be positive")
    private Double premium;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "User ID is required")
    private Long userId;
}