package com.insurance.manage.bill.service;

import com.insurance.manage.bill.dto.BillRequest;
import com.insurance.manage.bill.model.Bill;
import com.insurance.manage.bill.repository.BillRepository;
import com.insurance.manage.policy.model.Policy;
import com.insurance.manage.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

   @Autowired
    private BillRepository billRepository;
    
   @Autowired
    private PolicyRepository policyRepository;

   public Bill createBill(BillRequest request){
       Policy policy = policyRepository.findById(request.getPolicyId())
               .orElseThrow(() -> new RuntimeException("Policy not found"));
               
       Bill bill = Bill.builder()
               .billNumber(request.getBillNumber())
               .amount(request.getAmount())
               .status(request.getStatus())
               .dueDate(request.getDueDate())
               .policy(policy)
               .build();
               
       return billRepository.save(bill);
   }
   
   public List<Bill> getAllBills(){
       return billRepository.findAll();
   }
   
   public Bill getBillById(Long id){
       return billRepository.findById(id).orElse(null);
   }
   
   public Bill updateBill(Long id, BillRequest request){
       Bill existing = billRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Bill not found"));

       existing.setBillNumber(request.getBillNumber());
       existing.setAmount(request.getAmount());
       existing.setStatus(request.getStatus());
       existing.setDueDate(request.getDueDate());

       return billRepository.save(existing);
   }
   
   public List<Bill> getBillsByPolicyId(Long policyId){
       return billRepository.findByPolicyId(policyId);
   }
   
   public void deleteBillById(Long id){
       billRepository.deleteById(id);
   }
}
