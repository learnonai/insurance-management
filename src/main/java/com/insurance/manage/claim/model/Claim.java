package com.insurance.manage.claim.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insurance.manage.policy.model.Policy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String claimNumber;
    private String claimantName;
    private Double claimAmount;
    private String status;
    private LocalDate dateFiled;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonBackReference("policy-claims")
    private Policy policy;
}
