package com.insurance.manage.claim.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimRequest {
    @NotBlank(message = "Claim number is required")
    private String claimNumber;
    
    @NotBlank(message = "Claimant name is required")
    private String claimantName;
    
    @Positive(message = "Claim amount must be positive")
    private Double claimAmount;
    
    @Pattern(regexp = "PENDING|APPROVED|REJECTED", message = "Status must be PENDING, APPROVED, or REJECTED")
    private String status = "PENDING";
    
    @NotNull(message = "Date filed is required")
    @PastOrPresent(message = "Date filed cannot be in future")
    private LocalDate dateFiled;
    
    @NotNull(message = "Policy ID is required")
    private Long policyId;
}