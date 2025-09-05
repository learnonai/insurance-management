package com.insurance.manage.bill.controller;


import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public Bill create(@RequestBody Bill bill){
        return billService.createBill(bill);
    }
    @GetMapping
    public List<Bill> getAll(){
        return billService.getAllBills();
    }
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id){
        return billService.getBillById(id);
    }
    @GetMapping("/policy/{policyNumber}")
    public List<Bill> getBillByPolicy(@PathVariable String policyNumber){
        return billService.getBillsByPolicy(policyNumber);
    }
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill){
        return billService.updateBill(id,bill);
    }
    @DeleteMapping("/{id}")
    public String deleteBill(@PathVariable Long id){
         billService.deleteBillById(id);
         return "Bill Deleted.";
    }
}
