package com.insurance.manage.policy.controller;

import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy){
      return    policyService.savePolicy(policy);
    }

    @GetMapping
    public List<Policy> geAlltPolicies(){
        return policyService.getAllPolicies();
    }

    @GetMapping("/{id}")
    public Policy getPolicyById(@PathVariable Long id){
        return policyService.getPolicyById(id);
    }

    @PutMapping("/{id}")
    public Policy updatePolicy(@PathVariable Long id, @RequestBody Policy policy){
        return policyService.updatePolicy(id, policy);
    }

    @DeleteMapping("/{id}")
    public String  deletePolicy(@PathVariable Long id){
         policyService.deletePolicy(id);
         return "Policy Deleted.";
    }
}
