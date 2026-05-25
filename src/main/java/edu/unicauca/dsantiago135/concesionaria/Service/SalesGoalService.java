package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Model.clsSalesGoal;
import edu.unicauca.dsantiago135.concesionaria.Repository.SalesGoalRepository;

@Service
public class SalesGoalService {
   private final SalesGoalRepository attSalesGoalRepository;
   private HashMap<Integer, clsSalesGoal>  varSalesGoals = new HashMap<>(); 

   public SalesGoalService(SalesGoalRepository prmSalesGoalRepository){
      this.attSalesGoalRepository = prmSalesGoalRepository;
   }
   //public void opRegisterSalesGoal(clsSalesGoal prmSalesGoal)
   //public void opUpdateSalesGoal(clsSalesGoal prmSalesGoal)
   //public void opDisableSalesGoal(int prmId)
   //public void opCompleteSalesGoal(int prmId)
   //public List<clsSalesGoal> opGetAllSalesGoals()
   //public clsSalesGoal opGetSalesGoalById(int prmId)
   //public List<clsSalesGoal> opGetSalesGoalsByState(String prmState)
}
