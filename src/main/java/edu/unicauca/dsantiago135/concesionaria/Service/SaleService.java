package edu.unicauca.dsantiago135.concesionaria.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Model.clsSale;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;
import edu.unicauca.dsantiago135.concesionaria.Repository.SaleRepository;

@Service
public class SaleService {

   //region ATTRIBUTES
   private final SaleRepository attSaleRepository;
   private final UnitService attUnitService;
   private final CustomerService attCustomerService;
   private final EmployeeService attEmployeeService;
   
   private HashMap<Integer, clsSale> attSales = new HashMap<>();
   //endregion

   //region CONSTRUCTOR
   public SaleService (SaleRepository prmSaleRepository, UnitService prmUnitService, CustomerService prmCustomerService, EmployeeService prmEmployeeService){
      this.attSaleRepository = prmSaleRepository;
      this.attUnitService = prmUnitService;
      this.attCustomerService = prmCustomerService;
      this.attEmployeeService = prmEmployeeService;
   }
   //endregion

   //region PRIVATE HELPERS
   private clsSale opFetchFromDB(int prmId) {
      clsSale varSale;
      try {
         varSale = attSaleRepository.opGetSaleById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener la venta desde BD: ", e);
      }
      if (varSale == null)
         throw new excNotFoundException("Venta no encontrada");

      clsCustomer varCustomer = attCustomerService
         .opGetCustomerById(varSale.getAttCustomer().getAttCustomerId());
      clsEmployee varEmployee = attEmployeeService
         .opGetEmployeeById(varSale.getAttEmployee().getAttEmployeeId());
      clsUnit varUnit = attUnitService
         .opGetUnitById(varSale.getAttUnit().getAttUnitId());

      varSale.setAttCustomer(varCustomer);
      varSale.setAttEmployee(varEmployee);
      varSale.setAttUnit(varUnit);
      return varSale;
   }

   private void opRefreshCache(int prmId) {
      attSales.remove(prmId);
      clsSale varFresh = opFetchFromDB(prmId);
      attSales.put(prmId, varFresh);
   }
   //endregion

   //region PROCEDURES
   public void opRegisterSale(int prmIdCustomer, int prmIdEmployee, int prmIdUnit,double prmPrice){
      clsValidations.opValidateId(prmIdUnit);
      clsValidations.opValidateId(prmIdEmployee);
      clsValidations.opValidateId(prmIdCustomer);

      clsValidations.opValidatePositiveNumber(prmPrice, "el precio de la venta");

      clsSale varTemp = new clsSale();
      varTemp.setAttSaleId(-1);
      varTemp.setAttPrice(prmPrice);
      varTemp.setAttDateStart(null);
      varTemp.setAttDateEnd(null);
      varTemp.setAttStatus(null);

      clsEmployee varEmployee = attEmployeeService.opGetEmployeeById(prmIdEmployee);
      clsCustomer varCustomer = attCustomerService.opGetCustomerById(prmIdCustomer);
      clsUnit varUnit = attUnitService.opGetUnitById(prmIdUnit);
      varTemp.setAttEmployee(varEmployee);
      varTemp.setAttCustomer(varCustomer);
      varTemp.setAttUnit(varUnit);

      int varId; 
      try {
         varId = attSaleRepository.opRegisterSale(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar venta: ",e);
      }

      opRefreshCache(varId);
   }
   
   public void opRegisterReservation(int prmIdCustomer, int prmIdEmployee, int prmIdUnit, double prmPrice, Date prmDateEnd){
      clsValidations.opValidateId(prmIdUnit);
      clsValidations.opValidateId(prmIdEmployee);
      clsValidations.opValidateId(prmIdCustomer);

      clsValidations.opValidatePositiveNumber(prmPrice, "el precio de la venta");
      clsValidations.opValidateDate(prmDateEnd);

      clsSale varTemp = new clsSale();
      varTemp.setAttPrice(prmPrice);
      varTemp.setAttDateStart(null);
      varTemp.setAttDateEnd(prmDateEnd);
      varTemp.setAttStatus(null);

      clsEmployee varEmployee = attEmployeeService.opGetEmployeeById(prmIdEmployee);
      clsCustomer varCustomer = attCustomerService.opGetCustomerById(prmIdCustomer);
      clsUnit varUnit = attUnitService.opGetUnitById(prmIdUnit);
      varTemp.setAttEmployee(varEmployee);
      varTemp.setAttCustomer(varCustomer);
      varTemp.setAttUnit(varUnit);

      int varId;
      try {
         varId = attSaleRepository.opRegisterReservation(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar la reserva: ",e);
      }

      opRefreshCache(varId);
   }
   
   public void opCompleteReservation(int prmId){
      clsValidations.opValidateId(prmId);
      if(!attSaleRepository.opSaleExist(prmId)) throw new excNotFoundException("Reservación no encontrada");
      try {
         attSaleRepository.opCompleteReservation(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al completar venta",e);
      }
      opRefreshCache(prmId);
   }
   
   public void opCancelReservation(int prmId){
      clsValidations.opValidateId(prmId);
      if(!attSaleRepository.opSaleExist(prmId)) throw new excNotFoundException("Reserva no encontrada");
      try {
         attSaleRepository.opCancelReservation(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al cancelar venta",e);
      }
      opRefreshCache(prmId);
   }
   //endregion

   //region FUNCTIONS
   public clsSale opGetSaleById(int prmId){
      clsValidations.opValidateId(prmId);
      clsSale varSale = attSales.get(prmId);
      if(varSale != null)
         return varSale;
      varSale = opFetchFromDB(prmId);
      attSales.put(prmId, varSale);
      return varSale;
   }
   
   public List<clsSale> opGetAllSales(){
      List<clsSale> varSales = null;
      try {
         varSales = attSaleRepository.opGetAllSales();
         for(clsSale varSale : varSales){
            clsEmployee varEmployee =  attEmployeeService
                  .opGetEmployeeById(varSale.getAttEmployee().getAttEmployeeId());
            clsCustomer varCustomer = attCustomerService
                  .opGetCustomerById(varSale.getAttCustomer().getAttCustomerId());
            clsUnit varUnit = attUnitService
                  .opGetUnitById(varSale.getAttUnit().getAttUnitId());
            varSale.setAttEmployee(varEmployee);
            varSale.setAttCustomer(varCustomer);
            varSale.setAttUnit(varUnit);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener ventas",e);
      }
      return varSales;
   }
   
   public List<clsSale> opGetSalesByStatus(String prmStatus){
      clsValidations.opValidateSaleStatus(prmStatus);
      List<clsSale> varSales = null;
      try {
         varSales = attSaleRepository.opGetSalesByStatus(prmStatus);
         for(clsSale varSale : varSales){
            clsEmployee varEmployee =  attEmployeeService
                  .opGetEmployeeById(varSale.getAttEmployee().getAttEmployeeId());
            clsCustomer varCustomer = attCustomerService
                  .opGetCustomerById(varSale.getAttCustomer().getAttCustomerId());
            clsUnit varUnit = attUnitService
                  .opGetUnitById(varSale.getAttUnit().getAttUnitId());
            varSale.setAttEmployee(varEmployee);
            varSale.setAttCustomer(varCustomer);
            varSale.setAttUnit(varUnit);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener ventas",e);
      }
      return varSales;
   }
   //endregion
}
