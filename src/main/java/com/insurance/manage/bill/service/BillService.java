package com.insurance.manage.bill.service;


import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

   @Autowired
    private BillRepository billRepository;

   public Bill createBill(Bill bill){
       return billRepository.save(bill);
   }
   public List<Bill> getAllBills(){
       return billRepository.findAll();
   }
   public Bill getBillById(Long id){
       return billRepository.findById(id).orElse(null);
   }
   public List<Bill> getBillsByPolicy(String policyNumber){
       return billRepository.findByPolicyNumber(policyNumber);
   }
   public Bill updateBill(Long id, Bill updateBill){
       Bill existing = billRepository.findById(id).orElse(null);
       if(existing == null ) return null;

    existing.setBillNumber(updateBill.getBillNumber());
    existing.setPolicyNumber(updateBill.getPolicyNumber());
    existing.setAmount(updateBill.getAmount());
    existing.setStatus(updateBill.getStatus());
    existing.setDueDate(updateBill.getDueDate());

    return billRepository.save(existing);
   }
   public void  deleteBillById(Long id){
       billRepository.deleteById(id);
   }

}
