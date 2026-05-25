package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.SalesGoalRepository;

@Service
public class SalesGoalService {
   private final SalesGoalRepository attSalesGoalRepository;

   public SalesGoalService(SalesGoalRepository prmSalesGoalRepository){
      this.attSalesGoalRepository = prmSalesGoalRepository;
   }
}
