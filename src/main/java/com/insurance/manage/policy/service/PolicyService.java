package com.insurance.manage.policy.service;

import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy savePolicy(Policy policy){
        return policyRepository.save(policy);
    }

    public List<Policy> getAllPolicies(){
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id){
        return policyRepository.findById(id).orElse(null);
    }

    public  Policy updatePolicy(Long id, Policy updatedPolicy){
        Policy existing = policyRepository.findById(id).orElse(null);
        if(existing == null) return  null;
    existing.setPolicyNumber(updatedPolicy.getPolicyNumber());
    existing.setPolicyHolderName(updatedPolicy.getPolicyHolderName());
    existing.setType(updatedPolicy.getType());
    existing.setPremium(updatedPolicy.getPremium());
    existing.setStartDate(updatedPolicy.getStartDate());
    existing.setEndDate(updatedPolicy.getEndDate());
    return policyRepository.save(existing);
    }
    public void deletePolicy(Long id ){
         policyRepository.deleteById(id);
    }
}
