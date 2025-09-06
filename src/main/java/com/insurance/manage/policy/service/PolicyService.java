package com.insurance.manage.policy.service;

import com.insurance.manage.auth.model.User;
import com.insurance.manage.auth.repository.UserRepository;
import com.insurance.manage.business.PolicyEligibilityService;
import com.insurance.manage.business.PremiumCalculationService;
import com.insurance.manage.policy.dto.PolicyRequest;
import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PolicyEligibilityService eligibilityService;
    
    @Autowired
    private PremiumCalculationService premiumService;

    public Policy savePolicy(PolicyRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check eligibility
        if (!eligibilityService.isEligibleForPolicy(user, request)) {
            throw new RuntimeException(eligibilityService.getEligibilityMessage(user, request));
        }
        
        // Validate premium
        if (!premiumService.isPremiumValid(request.getType(), request.getPremium(), 
                request.getStartDate(), request.getEndDate())) {
            Double calculatedPremium = premiumService.calculatePremium(request.getType(), 
                    request.getStartDate(), request.getEndDate());
            throw new RuntimeException("Invalid premium. Expected: " + calculatedPremium);
        }
        
        // Validate end date is after start date
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }
                
        Policy policy = Policy.builder()
                .policyNumber(request.getPolicyNumber())
                .policyHolderName(request.getPolicyHolderName())
                .type(request.getType())
                .premium(request.getPremium())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .user(user)
                .build();
                
        return policyRepository.save(policy);
    }

    public List<Policy> getAllPolicies(){
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id){
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }

    public Policy updatePolicy(Long id, PolicyRequest request){
        Policy existing = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        
        // Validate end date is after start date
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }
                
        existing.setPolicyNumber(request.getPolicyNumber());
        existing.setPolicyHolderName(request.getPolicyHolderName());
        existing.setType(request.getType());
        existing.setPremium(request.getPremium());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        
        return policyRepository.save(existing);
    }
    
    public void deletePolicy(Long id){
         policyRepository.deleteById(id);
    }
}
