package edu.unicauca.dsantiago135.concesionaria.Controller;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.Model.*;
import edu.unicauca.dsantiago135.concesionaria.Service.*;

import java.util.Date;
import java.util.List;

@Component
public class clsController {

//region Attributes
    private final CustomerService attCustomerService;
    private final EmployeeService attEmployeeService;
    private final DealershipService attDealershipService;
    private final VehicleService attVehicleService;
    private final UnitService attUnitService;
    private final SaleService attSaleService;
    private final SalesGoalService attSalesGoalService;
//endregion

    public clsController(CustomerService prmCustomerService, EmployeeService prmEmployeeService,DealershipService prmDealershipService,
                        VehicleService prmVehicleService, UnitService prmUnitService, SaleService prmSaleService, SalesGoalService prmSalesGoalService) {
        this.attCustomerService = prmCustomerService;
        this.attEmployeeService = prmEmployeeService;
        this.attDealershipService = prmDealershipService;
        this.attVehicleService = prmVehicleService;
        this.attUnitService = prmUnitService;
        this.attSaleService = prmSaleService;
        this.attSalesGoalService = prmSalesGoalService;
    }


    public boolean opRegisterDealership(int prmOUID, String prmName, String prmState, String prmAddress, String prmPhone) {
        
        return false;
    }

    public boolean opRegisterEmployee(int prmOUID, String prmName, String prmState, String prmPhone, double prmSalary, Date prmHireDate, String prmRole) {
    
        return false;
    }

    public boolean opRegisterCustomer(int prmOUID, String prmName, String prmState, String prmPhone, String prmEmail) {
    return false;
    }

    public boolean opRegisterUnit(int prmOUIDVehicle, int prmOUIDUnit, String prmState, String prmLicensePlate, String prmColor, int prmMileage, Date prmDateEntry, String prmCondition) {

    return false;
    }

    public boolean opRegisterVehicle(int prmOUID, String prmState, String prmBrand, String prmModel, int prmYear, String prmBodyType, String prmFuelType, String prmCategory) {

    return false;
    }

    public boolean opRegisterReservation(int prmOUIDSale, int prmOUIDCustomer, int prmOUIDEmployee, int prmOUIDUnit, Date prmDateStart, double prmPrice, String prmStatus, Date prmDateEnd) {

    return false;
    }

    public boolean opRegisterSale(int prmOUIDSale, int prmOUIDCustomer, int prmOUIDEmployee, int prmOUIDUnit, Date prmDateStart, double prmPrice, String prmStatus) {

    return false;
    }

    public boolean opRegisterSalesGoal(int prmOUIDSaleGoal, int prmOUIDDealership, int prmOUIDEmployee, String prmGoalType, double prmTargetValue, Date prmStartDate, Date prmEndDate, String prmState) {
    
    return false;
    }

    public boolean opUpdateDealership(int prmOUID, String prmName, String prmAddress, String prmPhone) {
    
    return false;
    }

    public boolean opUpdateEmployee(int prmOUID, String prmName, String prmPhone, double prmSalary) {
    
    return false;
    }

    public boolean opUpdateCustomer(int prmOUID, String prmName, String prmPhone, String prmEmail) {
    
    return false;
    }

    public boolean opUpdateUnitState(int prmOUID, String prmState) {
    
    return false;
    }

    public boolean opUpdateSaleState(int prmOUID, String prmState) {
    
    return false;
    }

    public boolean opCancelReservation(int prmOUID) {
    
    return false;
    }

    public clsEmployee opGetEmployeeBy(int prmOUID){
    
    return null;
    }

    public clsDealership opGetDealershipBy(int prmOUID) {
    
    return null;
    }

    public clsCustomer opGetCustomerBy(int prmOUID) {
        return null;
    }

    public clsUnit opGetUnitBy(int prmOUID) {
    
    return null;
    }

    public clsSale opGetSaleBy(int prmOUID) {
    
    return null;
    }

    public clsSalesGoal opGetSalesGoalBy(int prmOUID) {
    
    return null;
    }

    public List<clsDealership> opGetDealerships() {
    
    return null;
    }

    public List<clsEmployee> opGetEmployees() {
    
    return null;
    }

    public List<clsCustomer> opGetCustomers() {
    
    return null;
    }

    public List<clsUnit> opGetInventory() {
    
    return null;
    }

    public List<clsUnit> opGetAvailableUnits() {
    
    return null;
    }

    public List<clsSale> opGetSales() {
    
    return null;
    }

    public List<clsUnit> opGetReservedUnits() {
    
    return null;
    }

    public List<clsSalesGoal> opGetSalesGoal() {
    
    return null;
    }

    public boolean opAssociateSalesGoal() {
    
    return false;
    }

    public boolean opDisableDealership(int prmOUID) {
    
    return false;
    }

    public boolean opDisableEmployee(int prmOUID) {
    
    return false;
    }

    public boolean opDisableCustomer(int prmOUID) {
    
    return false;
    }

    public boolean opDisableVehicle(int prmOUID) {
    
    return false;
    }

    public boolean opDisableSalesGoal(int prmOUID) {
    
    return false;
    }

    public boolean opCompleteSale(int prmOUID) {
    
    return false;
    }

    public boolean opCompleteSalesGoal(int prmOUID) {
    
    return false;
    }

    public boolean opModifyEmployeeRole(){

        return false;
    }

    public List<clsUnit> opGetInnventory(int prmOUID){
        return null;
    }
}