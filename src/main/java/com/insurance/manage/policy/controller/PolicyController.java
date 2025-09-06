package com.insurance.manage.policy.controller;

import com.insurance.manage.policy.dto.PolicyRequest;
import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Policy createPolicy(@Valid @RequestBody PolicyRequest request){
        return policyService.savePolicy(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<Policy> getAllPolicies(){
        return policyService.getAllPolicies();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public Policy getPolicyById(@PathVariable Long id){
        return policyService.getPolicyById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Policy updatePolicy(@PathVariable Long id, @Valid @RequestBody PolicyRequest request){
        return policyService.updatePolicy(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePolicy(@PathVariable Long id){
        policyService.deletePolicy(id);
        return "Policy Deleted.";
    }
}
