package com.insurance.manage.business;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class PremiumCalculationService {

    public Double calculatePremium(String policyType, LocalDate startDate, LocalDate endDate) {
        int policyDurationMonths = Period.between(startDate, endDate).getMonths();
        double basePremium = getBasePremium(policyType);
        double durationMultiplier = policyDurationMonths / 12.0;
        
        return basePremium * durationMultiplier;
    }
    
    private double getBasePremium(String policyType) {
        return switch (policyType.toUpperCase()) {
            case "HEALTH" -> 1200.0;
            case "AUTO" -> 800.0;
            case "LIFE" -> 2000.0;
            default -> 1000.0;
        };
    }
    
    public boolean isPremiumValid(String policyType, Double providedPremium, LocalDate startDate, LocalDate endDate) {
        Double calculatedPremium = calculatePremium(policyType, startDate, endDate);
        return Math.abs(providedPremium - calculatedPremium) <= calculatedPremium * 0.1;
    }
}