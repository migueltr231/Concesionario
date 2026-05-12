package edu.unicauca.dsantiago135.concesionaria.Controller;

import edu.unicauca.dsantiago135.concesionaria.Model.*;
import java.util.Date;
import java.util.List;

public class clsController {

    private static clsController attInstance;
    public static clsController opGetInstance() {
        if (attInstance != null) return attInstance ;
        return attInstance = new clsController();
    }

    
    public boolean opRegisterDealership(String prmOUID, String prmName, String prmState, String prmAddress, String prmPhone) {
        
        return false;
    }

    public boolean opRegisterEmployee(String prmOUID, String prmName, String prmState, String prmPhone, double prmSalary, Date prmHireDate, String prmRole) {
    
        return false;
    }

    public boolean opRegisterCustomer(String prmOUID, String prmName, String prmState, String prmPhone, String prmEmail) {

    return false;
    }

    public boolean opRegisterUnit(String prmOUIDVehicle, String prmOUIDUnit, String prmState, String prmLicensePlate, String prmColor, int prmMileage, Date prmDateEntry, String prmCondition) {

    return false;
    }

    public boolean opRegisterVehicle(String prmOUID, String prmState, String prmBrand, String prmModel, int prmYear, String prmBodyType, String prmFuelType, String prmCategory) {

    return false;
    }

    public boolean opRegisterReservation(String prmOUIDSale, String prmOUIDCustomer, String prmOUIDEmployee, String prmOUIDUnit, Date prmDateStart, double prmPrice, String prmStatus, Date prmDateEnd) {

    return false;
    }

    public boolean opRegisterSale(String prmOUIDSale, String prmOUIDCustomer, String prmOUIDEmployee, String prmOUIDUnit, Date prmDateStart, double prmPrice, String prmStatus) {

    return false;
    }

    public boolean opRegisterSalesGoal(String prmOUIDSaleGoal, String prmOUIDDealership, String prmOUIDEmployee, String prmGoalType, double prmTargetValue, Date prmStartDate, Date prmEndDate, String prmState) {
    
    return false;
    }

    public boolean opUpdateDealership(String prmOUID, String prmName, String prmAddress, String prmPhone) {
    
    return false;
    }

    public boolean opUpdateEmployee(String prmOUID, String prmName, String prmPhone, double prmSalary) {
    
    return false;
    }

    public boolean opUpdateCustomer(String prmOUID, String prmName, String prmPhone, String prmEmail) {
    
    return false;
    }

    public boolean opUpdateUnitState(String prmOUID, String prmState) {
    
    return false;
    }

    public boolean opUpdateSaleState(String prmOUID, String prmState) {
    
    return false;
    }

    public boolean opCancelReservation(String prmOUID) {
    
    return false;
    }

    public clsEmployee opGetEmployeeBy(String prmOUID){
    
    return null;
    }
    
    public clsDealership opGetDealershipBy(String prmOUID) {
    
    return null;
    }
    
    public clsCustomer opGetCustomerBy(String prmOUID) {
    
    return null;
    }
    
    public clsUnit opGetUnitBy(String prmOUID) {
    
    return null;
    }
    
    public clsSale opGetSaleBy(String prmOUID) {
    
    return null;
    }
    
    public clsSalesGoal opGetSalesGoalBy(String prmOUID) {
    
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

    public boolean opDisableDealership(String prmOUID) {
    
    return false;
    }

    public boolean opDisableEmployee(String prmOUID) {
    
    return false;
    }

    public boolean opDisableCustomer(String prmOUID) {
    
    return false;
    }

    public boolean opDisableVehicle(String prmOUID) {
    
    return false;
    }

    public boolean opDisableSalesGoal(String prmOUID) {
    
    return false;
    }

    public boolean opCompleteSale(String prmOUID) {
    
    return false;
    }

    public boolean opCompleteSalesGoal(String prmOUID) {
    
    return false;
    }
    
}