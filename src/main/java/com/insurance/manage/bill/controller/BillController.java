package com.insurance.manage.bill.controller;

import com.insurance.manage.bill.dto.BillRequest;
import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Bill create(@RequestBody BillRequest request){
        return billService.createBill(request);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<Bill> getAll(){
        return billService.getAllBills();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public Bill getBillById(@PathVariable Long id){
        return billService.getBillById(id);
    }
    
    @GetMapping("/policy/{policyId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    public List<Bill> getBillsByPolicy(@PathVariable Long policyId){
        return billService.getBillsByPolicyId(policyId);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public Bill updateBill(@PathVariable Long id, @RequestBody BillRequest request){
        return billService.updateBill(id, request);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBill(@PathVariable Long id){
         billService.deleteBillById(id);
         return "Bill Deleted.";
    }
}
