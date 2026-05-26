package edu.unicauca.dsantiago135.concesionaria.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

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
   public void opRegisterSalesGoal(int prmIdDealership, Integer prmIdEmployee, String prmGoalType, Integer prmTargetValue, Date prmStartDate ){
      clsValidations.opValidateId(prmIdEmployee);
      clsValidations.opValidateId(prmIdDealership);
      clsValidations.opValidateGoalType(prmGoalType);
      clsValidations.opValidatePositiveNumber(prmTargetValue, "el valor de la meta");
      clsValidations.opValidateDate(prmStartDate);

      clsSalesGoal varTemp = new clsSalesGoal();
      varTemp.setAttDealership(attDealershipService.opGetDealershipById(prmIdDealership));
      if(prmIdEmployee != null)
         varTemp.setAttEmployee(attEmployeeService.opGetEmployeeById(prmIdEmployee));
      varTemp.setAttGoalType(prmGoalType);
      varTemp.setAttTargetValue(prmTargetValue);
      varTemp.setAttStartDate(prmStartDate);

      int varId;
      try {
         varId = attSalesGoalRepository.opRegisterSalesGoal(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar la meta",e);
      }

      opRefreshCache(varId);
   }
   
   public void opUpdateSalesGoal(int prmId,Integer prmTargetValue, String prmGoalType){
      clsValidations.opValidateId(prmId);

      if(!attSalesGoalRepository.opSalesGoalExist(prmId)) throw new excNotFoundException("Meta no encontrada");

      clsValidations.opValidatePositiveNumber(prmTargetValue, "el valor de la meta");
      clsValidations.opValidateGoalType(prmGoalType);

      clsSalesGoal varSalesGoal = opFetchFromDB(prmId);
      if(prmTargetValue != null)
         varSalesGoal.setAttTargetValue(prmTargetValue);
      if(prmGoalType != null)
         varSalesGoal.setAttGoalType(prmGoalType);

      try {
         attSalesGoalRepository.opUpdateSalesGoal(varSalesGoal);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar la meta",e);
      }

      opRefreshCache(prmId);
   }
   
   public void opDisableSalesGoal(int prmId){
      clsValidations.opValidateId(prmId);
      try {
         attSalesGoalRepository.opDisableSalesGoal(prmId);
      } catch (Exception e) {
         throw new excDatabaseException("Error al deshabilitar la meta",e);
      }
      opRefreshCache(prmId);
   }
   
   public void opCompleteSalesGoal(int prmId){
      clsValidations.opValidateId(prmId);
      try {
         attSalesGoalRepository.opCompleteSalesGoal(prmId);
      } catch (Exception e) {
         throw new excDatabaseException("Error al completar la meta",e);
      }
      opRefreshCache(prmId);
   }
   //endregion

   //region FUNCTIONS
   public List<clsSalesGoal> opGetAllSalesGoals(){
      List<clsSalesGoal> varSalesGoals = null;
      try {
         varSalesGoals=attSalesGoalRepository.opGetAllSalesGoals();
         for(clsSalesGoal varTemp : varSalesGoals){
            varTemp.setAttDealership(attDealershipService
               .opGetDealershipById(varTemp.getAttDealership().getAttDealershipId()));
            varTemp.setAttEmployee(attEmployeeService
               .opGetEmployeeById(varTemp.getAttEmployee().getAttEmployeeId()));
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener las metas",e);
      }
      return varSalesGoals;
   }
   
   public clsSalesGoal opGetSalesGoalById(int prmId){
      clsValidations.opValidateId(prmId);
      
      clsSalesGoal varSalesGoal = attSalesGoals.get(prmId);
      if(varSalesGoal != null)
         return varSalesGoal;
      
      varSalesGoal = opFetchFromDB(prmId);
      attSalesGoals.put(prmId, varSalesGoal);
      return varSalesGoal;
   }
   
   public List<clsSalesGoal> opGetSalesGoalsByState(String prmState){
      clsValidations.opValidateSalesGoalState(prmState);
      List<clsSalesGoal> varSalesGoals = null;
      try {
         varSalesGoals=attSalesGoalRepository.opGetSalesGoalsByState(prmState);
         for(clsSalesGoal varTemp : varSalesGoals){
            varTemp.setAttDealership(attDealershipService
               .opGetDealershipById(varTemp.getAttDealership().getAttDealershipId()));
            varTemp.setAttEmployee(attEmployeeService
               .opGetEmployeeById(varTemp.getAttEmployee().getAttEmployeeId()));
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener las metas",e);
      }
      return varSalesGoals;
   }
   //endregion
}