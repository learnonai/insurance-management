package com.insurance.manage.claim.service;

import com.insurance.manage.business.ClaimApprovalService;
import com.insurance.manage.claim.dto.ClaimRequest;
import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.claim.repository.ClaimRepository;
import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private ClaimApprovalService approvalService;

    public Claim fileClaim(ClaimRequest request){
        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        
        // Process claim approval based on business rules
        String approvalStatus = approvalService.processClaimApproval(policy, request);
                
        Claim claim = Claim.builder()
                .claimNumber(request.getClaimNumber())
                .claimantName(request.getClaimantName())
                .claimAmount(request.getClaimAmount())
                .status(approvalStatus)
                .dateFiled(request.getDateFiled())
                .policy(policy)
                .build();
                
        return claimRepository.save(claim);
    }
    
    public List<Claim> getAllClaims(){
        return claimRepository.findAll();
    }
    
    public Claim getClaimById(Long id){
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    public Claim updateClaim(Long id, ClaimRequest request){
        Claim existing = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        
        // Validate status transition
        if (!approvalService.canUpdateClaimStatus(existing, request.getStatus())) {
            throw new RuntimeException("Invalid status transition from " + existing.getStatus() + " to " + request.getStatus());
        }
                
        existing.setClaimNumber(request.getClaimNumber());
        existing.setClaimantName(request.getClaimantName());
        existing.setClaimAmount(request.getClaimAmount());
        existing.setStatus(request.getStatus());
        existing.setDateFiled(request.getDateFiled());
        
        return claimRepository.save(existing);
    }
    
    public void deleteClaimById(Long id){
        claimRepository.deleteById(id);
    }
}
