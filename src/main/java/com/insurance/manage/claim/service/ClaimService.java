package com.insurance.manage.claim.service;

import com.insurance.manage.claim.model.Claim;
import com.insurance.manage.claim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    public Claim fileClaim(Claim claim){
        return claimRepository.save(claim);
    }
    public List<Claim> getAllClaims(){
        return claimRepository.findAll();
    }
    public Claim getClaimById(Long id){
        return claimRepository.findById(id).orElse(null);
    }

    public Claim updateClaim(Long id, @RequestBody  Claim updateClaim){
        Claim existing = claimRepository.findById(id).orElse(null);
        if(existing == null) return  null;
    existing.setClaimNumber(updateClaim.getClaimNumber());
    existing.setPolicyNumber(updateClaim.getPolicyNumber());
    existing.setClaimantNmae(updateClaim.getClaimantNmae());
    existing.setStatus(updateClaim.getStatus());
    existing.setDateFiled(updateClaim.getDateFiled());
    return  claimRepository.save(existing);
    }
    public void  deleteClaimById(Long id){
        claimRepository.deleteById(id);
    }
}
