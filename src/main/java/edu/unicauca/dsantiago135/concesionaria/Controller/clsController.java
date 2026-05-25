package edu.unicauca.dsantiago135.concesionaria.Controller;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Model.*;
import edu.unicauca.dsantiago135.concesionaria.Service.*;

import java.util.Date;
import java.util.List;

@Component
public class clsController {

    // region Attributes
    private final CustomerService attCustomerService;
    private final EmployeeService attEmployeeService;
    private final DealershipService attDealershipService;
    private final VehicleService attVehicleService;
    private final UnitService attUnitService;
    private final SaleService attSaleService;
    private final SalesGoalService attSalesGoalService;
    // endregion

    public clsController(CustomerService prmCustomerService, EmployeeService prmEmployeeService,
            DealershipService prmDealershipService,
            VehicleService prmVehicleService, UnitService prmUnitService, SaleService prmSaleService,
            SalesGoalService prmSalesGoalService) {
        this.attCustomerService = prmCustomerService;
        this.attEmployeeService = prmEmployeeService;
        this.attDealershipService = prmDealershipService;
        this.attVehicleService = prmVehicleService;
        this.attUnitService = prmUnitService;
        this.attSaleService = prmSaleService;
        this.attSalesGoalService = prmSalesGoalService;
    }

    public void opRegisterDealership(int prmId, String prmName, String prmState, String prmAddress, String prmPhone) {
        attDealershipService.opRegisterDealership(prmId, prmName, prmState, prmAddress, prmPhone);
    }

    public void opRegisterEmployee(int prmIdEmployee, int prmIdDealership, String prmName, String prmState,String prmPhone, double prmSalary, Date prmHireDate, String prmRole) {
        attEmployeeService.opRegisterEmployee(prmIdEmployee, prmIdDealership, prmName, prmPhone, prmSalary, null,prmRole, prmState);
    }

    public void opRegisterCustomer(int prmId, String prmName, String prmState, String prmPhone, String prmEmail){
        attCustomerService.opRegisterCustomer(prmId, prmName, prmEmail, prmPhone, prmState);
    }

    public boolean opRegisterUnit(int prmIdVehicle, int prmIdUnit, String prmState, String prmLicensePlate,
            String prmColor, int prmMileage, Date prmDateEntry, String prmCondition) {

        return false;
    }

    public void opRegisterVehicle(int prmId, String prmState, String prmBrand, String prmModel, int prmYear,String prmBodyType, String prmFuelType, String prmCategory){
        attVehicleService.opRegisterVehicle(prmId, prmState, prmBrand, prmModel, prmYear, prmBodyType, prmFuelType,prmCategory);
    }

    public boolean opRegisterReservation(int prmIdSale, int prmIdCustomer, int prmIdEmployee, int prmIdUnit,
            Date prmDateStart, double prmPrice, String prmStatus, Date prmDateEnd) {

        return false;
    }

    public boolean opRegisterSale(int prmIdSale, int prmIdCustomer, int prmIdEmployee, int prmIdUnit, Date prmDateStart,
            double prmPrice, String prmStatus) {

        return false;
    }

    public boolean opRegisterSalesGoal(int prmIdSaleGoal, int prmIdDealership, int prmIdEmployee, String prmGoalType,
            double prmTargetValue, Date prmStartDate, Date prmEndDate, String prmState) {
        return false;
    }

    public void opUpdateDealership(int prmId, String prmName, String prmAddress, String prmPhone) {
        attDealershipService.opUpdateDealership(prmId, prmName, prmAddress, prmPhone);
    }

    public void opUpdateEmployee(int prmId, String prmName, String prmPhone, double prmSalary) {
        attEmployeeService.opUpdateEmployee(prmId, prmName, prmPhone, prmSalary);
    }

    public void opUpdateCustomer(int prmId, String prmName, String prmPhone, String prmEmail) {
        attCustomerService.opUpdateCustomer(prmId, prmName, prmPhone, prmEmail);
    }

    public boolean opUpdateUnitState(int prmId, String prmState) {

        return false;
    }

    public boolean opUpdateSaleState(int prmId, String prmState) {

        return false;
    }

    public boolean opCancelReservation(int prmId) {

        return false;
    }

    public clsEmployee opGetEmployeeBy(int prmId) {
        return attEmployeeService.opGetEmployeeById(prmId);

    }

    public clsDealership opGetDealershipBy(int prmId) {
        return attDealershipService.opGetDealershipById(prmId);
    }

    public clsCustomer opGetCustomerBy(int prmId){
        return attCustomerService.opGetCustomerById(prmId);
    }

    public clsUnit opGetUnitBy(int prmId) {
    
    return null;
    }

    public clsSale opGetSaleBy(int prmId) {
    
    return null;
    }

    public clsSalesGoal opGetSalesGoalBy(int prmId) {
    
    return null;
    }

    public List<clsDealership> opGetDealerships() {
        return attDealershipService.opGetAllDealership();
    }

    public List<clsEmployee> opGetEmployees(){
        return attEmployeeService.opGetAllEmployees();
    }

    public List<clsCustomer> opGetCustomers() {
        return attCustomerService.opGetAllCustomers();
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

    public void opDisableDealership(int prmId) {
        attDealershipService.opDisableDealership(prmId);
    } 

    public void opDisableEmployee(int prmId) {
        attEmployeeService.opDisableEmployee(prmId);
    }

    public void opDisableCustomer(int prmId) {
        attCustomerService.opDisableCustomer(prmId);
    }

    public void opDisableVehicle(int prmId) {
        attVehicleService.opDisableVehicle(prmId);
    }

    public boolean opDisableSalesGoal(int prmId) {

        return false;
    }

    public boolean opCompleteSale(int prmId) {

        return false;
    }

    public boolean opCompleteSalesGoal(int prmId) {

        return false;
    }

    public boolean opModifyEmployeeRole() {

        return false;
    }

    public List<clsUnit> opGetInnventory(int prmId) {
        return null;
    }

    public List<clsEmployee> opGetEmployeeByDealership(int prmId) {
        return attEmployeeService.opGetEmployeesByDealership(prmId);
    }
}