package com.insurance.manage.claim.controller;


import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.claim.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public Claim createClaim(@RequestBody Claim claim){
        return  claimService.fileClaim(claim);
    }
    @GetMapping
    public List<Claim> getAllClaims(){
        return claimService.getAllClaims();
    }
    @GetMapping("{id}")
    public Claim getClaimById(@PathVariable Long id){
        return claimService.getClaimById(id);
    }
    @PutMapping("{id}")
    public Claim updateClaim(@PathVariable Long id, @RequestBody  Claim claim){
        return claimService.updateClaim(id,claim);
    }
    @DeleteMapping("{id}")
    public String deleteById(@PathVariable Long id){
        claimService.deleteClaimById(id);
        return "Claim Deleted";
    }
}
