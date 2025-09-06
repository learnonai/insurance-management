package com.insurance.manage.claim.controller;

import com.insurance.manage.claim.dto.ClaimRequest;
import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.claim.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Claim createClaim(@Valid @RequestBody ClaimRequest request){
        return claimService.fileClaim(request);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<Claim> getAllClaims(){
        return claimService.getAllClaims();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public Claim getClaimById(@PathVariable Long id){
        return claimService.getClaimById(id);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Claim updateClaim(@PathVariable Long id, @Valid @RequestBody ClaimRequest request){
        return claimService.updateClaim(id, request);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteById(@PathVariable Long id){
        claimService.deleteClaimById(id);
        return "Claim Deleted";
    }
}
