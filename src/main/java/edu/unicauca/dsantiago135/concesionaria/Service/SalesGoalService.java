package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Model.clsSalesGoal;
import edu.unicauca.dsantiago135.concesionaria.Repository.SalesGoalRepository;

@Service
public class SalesGoalService {

   //region ATTRIBUTES
   private final SalesGoalRepository attSalesGoalRepository;
   private final DealershipService attDealershipService;
   private final EmployeeService attEmployeeService;

   private HashMap<Integer, clsSalesGoal>  attSalesGoals = new HashMap<>(); 
   //endregion

   //region CONSTRUCTOR
   public SalesGoalService(SalesGoalRepository prmSalesGoalRepository, DealershipService prmDealershipService, EmployeeService prmEmployeeService){
      this.attSalesGoalRepository = prmSalesGoalRepository;
      this.attDealershipService = prmDealershipService;
      this.attEmployeeService = prmEmployeeService;
   }
   //endregion

   //region PRIVATE HELPERS
   private clsSalesGoal opFetchFromDB(int prmId){
      clsSalesGoal varSalesGoal;
      try {
         varSalesGoal = attSalesGoalRepository.opGetSalesGoalById(prmId);

      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener la meta de ventas desde la BD: ",e);
      }
      if(varSalesGoal == null) throw new excNotFoundException("Meta de venta no encontrada");

      clsDealership varDealership = attDealershipService
         .opGetDealershipById(varSalesGoal.getAttDealership().getAttDealershipId());
      clsEmployee varEmployee = attEmployeeService
         .opGetEmployeeById(varSalesGoal.getAttEmployee().getAttEmployeeId());
      varSalesGoal.setAttDealership(varDealership);
      varSalesGoal.setAttEmployee(varEmployee);
      return varSalesGoal;
   }

   private void opRefreshCache(int prmId){
      attSalesGoals.remove(prmId);
      clsSalesGoal varFresh = opFetchFromDB(prmId);
      attSalesGoals.put(prmId, varFresh);
   }
   //endregion

   //region PROCEDURES
   //public void opRegisterSalesGoal(clsSalesGoal prmSalesGoal)
   //public void opUpdateSalesGoal(clsSalesGoal prmSalesGoal)
   //public void opDisableSalesGoal(int prmId)
   //public void opCompleteSalesGoal(int prmId)
   //endregion

   //region FUNCTIONS
   //public List<clsSalesGoal> opGetAllSalesGoals()
   //public clsSalesGoal opGetSalesGoalById(int prmId)
   //public List<clsSalesGoal> opGetSalesGoalsByState(String prmState)
   //endregion
}
