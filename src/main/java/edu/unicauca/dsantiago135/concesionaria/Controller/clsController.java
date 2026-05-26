package edu.unicauca.dsantiago135.concesionaria.Controller;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.Model.*;
import edu.unicauca.dsantiago135.concesionaria.Service.*;

import java.sql.Date;
import java.util.List;

/**
 * Fachada principal del sistema de concesionaria.
 * <p>
 * Centraliza el acceso a todos los servicios y expone una interfaz
 * unificada para las operaciones del negocio, ocultando la complejidad
 * interna de cada servicio (patrón Facade).
 * </p>
 */
@Component
public class clsController {

    // region ATTRIBUTES
    private final CustomerService attCustomerService;
    private final EmployeeService attEmployeeService;
    private final DealershipService attDealershipService;
    private final VehicleService attVehicleService;
    private final UnitService attUnitService;
    private final SaleService attSaleService;
    private final SalesGoalService attSalesGoalService;
    // endregion

    // region CONSTRUCTOR
    public clsController(
            CustomerService prmCustomerService,
            EmployeeService prmEmployeeService,
            DealershipService prmDealershipService,
            VehicleService prmVehicleService,
            UnitService prmUnitService,
            SaleService prmSaleService,
            SalesGoalService prmSalesGoalService) {

        this.attCustomerService    = prmCustomerService;
        this.attEmployeeService    = prmEmployeeService;
        this.attDealershipService  = prmDealershipService;
        this.attVehicleService     = prmVehicleService;
        this.attUnitService        = prmUnitService;
        this.attSaleService        = prmSaleService;
        this.attSalesGoalService   = prmSalesGoalService;
    }
    // endregion

    // region DEALERSHIP

    /**
     * Registra una nueva concesionaria en el sistema.
     *
     * @param prmId      identificador único de la concesionaria
     * @param prmName    nombre de la concesionaria
     * @param prmState   estado inicial (active/inactive)
     * @param prmAddress dirección física
     * @param prmPhone   teléfono de contacto
     */
    public void opRegisterDealership(int prmId, String prmName, String prmState,
            String prmAddress, String prmPhone) {
        attDealershipService.opRegisterDealership(prmId, prmName, prmState, prmAddress, prmPhone);
    }

    /**
     * Actualiza los datos editables de una concesionaria.
     *
     * @param prmId      identificador de la concesionaria
     * @param prmName    nuevo nombre (null para no modificar)
     * @param prmAddress nueva dirección (null para no modificar)
     * @param prmPhone   nuevo teléfono (null para no modificar)
     */
    public void opUpdateDealership(int prmId, String prmName, String prmAddress, String prmPhone) {
        attDealershipService.opUpdateDealership(prmId, prmName, prmAddress, prmPhone);
    }

    /**
     * Inactiva una concesionaria existente.
     *
     * @param prmId identificador de la concesionaria a inactivar
     */
    public void opDisableDealership(int prmId) {
        attDealershipService.opDisableDealership(prmId);
    }

    /**
     * Obtiene una concesionaria por su identificador.
     *
     * @param prmId identificador de la concesionaria
     * @return objeto {@link clsDealership} con los datos de la concesionaria
     */
    public clsDealership opGetDealershipById(int prmId) {
        return attDealershipService.opGetDealershipById(prmId);
    }

    /**
     * Obtiene la lista completa de concesionarias registradas.
     *
     * @return lista de {@link clsDealership}
     */
    public List<clsDealership> opGetAllDealerships() {
        return attDealershipService.opGetAllDealership();
    }

    // endregion

    // region EMPLOYEE

    /**
     * Registra un nuevo empleado asociado a una concesionaria.
     *
     * @param prmIdEmployee    identificador del empleado
     * @param prmIdDealership  identificador de la concesionaria
     * @param prmName          nombre completo
     * @param prmPhone         teléfono de contacto
     * @param prmSalary        salario
     * @param prmHireDate      fecha de contratación
     * @param prmRole          rol (seller/manager)
     * @param prmState         estado inicial (active/inactive)
     */
    public void opRegisterEmployee(int prmIdEmployee, int prmIdDealership, String prmName,
            String prmPhone, double prmSalary, Date prmHireDate, String prmRole, String prmState) {
        attEmployeeService.opRegisterEmployee(
                prmIdEmployee, prmIdDealership, prmName,
                prmPhone, prmSalary, prmHireDate, prmRole, prmState);
    }

    /**
     * Actualiza los datos editables de un empleado.
     *
     * @param prmId     identificador del empleado
     * @param prmName   nuevo nombre (null para no modificar)
     * @param prmPhone  nuevo teléfono (null para no modificar)
     * @param prmSalary nuevo salario (null para no modificar)
     */
    public void opUpdateEmployee(int prmId, String prmName, String prmPhone, Double prmSalary) {
        attEmployeeService.opUpdateEmployee(prmId, prmName, prmPhone, prmSalary);
    }

    /**
     * Inactiva un empleado existente.
     *
     * @param prmId identificador del empleado
     */
    public void opDisableEmployee(int prmId) {
        attEmployeeService.opDisableEmployee(prmId);
    }

    /**
     * Obtiene un empleado por su identificador.
     *
     * @param prmId identificador del empleado
     * @return objeto {@link clsEmployee}
     */
    public clsEmployee opGetEmployeeById(int prmId) {
        return attEmployeeService.opGetEmployeeById(prmId);
    }

    /**
     * Obtiene la lista completa de empleados.
     *
     * @return lista de {@link clsEmployee}
     */
    public List<clsEmployee> opGetAllEmployees() {
        return attEmployeeService.opGetAllEmployees();
    }

    /**
     * Obtiene los empleados de una concesionaria específica.
     *
     * @param prmIdDealership identificador de la concesionaria
     * @return lista de {@link clsEmployee} de la concesionaria
     */
    public List<clsEmployee> opGetEmployeesByDealership(int prmIdDealership) {
        return attEmployeeService.opGetEmployeesByDealership(prmIdDealership);
    }

    /**
     * Obtiene los empleados con salario superior al promedio.
     *
     * @return lista de {@link clsEmployee}
     */
    public List<clsEmployee> opGetEmployeesAboveAvgSalary() {
        return attEmployeeService.opGetEmployeesAboveAvg();
    }

    // endregion

    // region CUSTOMER

    /**
     * Registra un nuevo cliente.
     *
     * @param prmId    identificador del cliente
     * @param prmName  nombre completo
     * @param prmPhone teléfono de contacto
     * @param prmEmail correo electrónico (puede ser null)
     * @param prmState estado inicial (active/inactive)
     */
    public void opRegisterCustomer(int prmId, String prmName, String prmPhone,
            String prmEmail, String prmState) {
        attCustomerService.opRegisterCustomer(prmId, prmName, prmEmail, prmPhone, prmState);
    }

    /**
     * Actualiza los datos editables de un cliente.
     *
     * @param prmId    identificador del cliente
     * @param prmName  nuevo nombre (null para no modificar)
     * @param prmPhone nuevo teléfono (null para no modificar)
     * @param prmEmail nuevo email (null para no modificar)
     */
    public void opUpdateCustomer(int prmId, String prmName, String prmPhone, String prmEmail) {
        attCustomerService.opUpdateCustomer(prmId, prmName, prmPhone, prmEmail);
    }

    /**
     * Inactiva un cliente existente.
     *
     * @param prmId identificador del cliente
     */
    public void opDisableCustomer(int prmId) {
        attCustomerService.opDisableCustomer(prmId);
    }

    /**
     * Obtiene un cliente por su identificador.
     *
     * @param prmId identificador del cliente
     * @return objeto {@link clsCustomer}
     */
    public clsCustomer opGetCustomerById(int prmId) {
        return attCustomerService.opGetCustomerById(prmId);
    }

    /**
     * Obtiene la lista completa de clientes.
     *
     * @return lista de {@link clsCustomer}
     */
    public List<clsCustomer> opGetAllCustomers() {
        return attCustomerService.opGetAllCustomers();
    }

    // endregion

    // region VEHICLE

    /**
     * Registra una nueva línea de vehículos.
     *
     * @param prmId       identificador del vehículo
     * @param prmState    estado inicial (active/inactive)
     * @param prmBrand    marca
     * @param prmModel    modelo
     * @param prmYear     año de fabricación
     * @param prmBodyType tipo de carrocería
     * @param prmFuelType tipo de combustible (electric/gasoline/hybrid)
     * @param prmCategory categoría (standard/luxury)
     */
    public void opRegisterVehicle(int prmId, String prmState, String prmBrand, String prmModel,
            int prmYear, String prmBodyType, String prmFuelType, String prmCategory) {
        attVehicleService.opRegisterVehicle(
                prmId, prmState, prmBrand, prmModel,
                prmYear, prmBodyType, prmFuelType, prmCategory);
    }

    /**
     * Actualiza los datos editables de una línea de vehículos.
     *
     * @param prmId       identificador del vehículo
     * @param prmBrand    nueva marca (null para no modificar)
     * @param prmModel    nuevo modelo (null para no modificar)
     * @param prmYear     nuevo año (null para no modificar)
     * @param prmBodyType nuevo tipo de carrocería (null para no modificar)
     * @param prmFuelType nuevo tipo de combustible (null para no modificar)
     * @param prmCategory nueva categoría (null para no modificar)
     */
    public void opUpdateVehicle(int prmId, String prmBrand, String prmModel, Integer prmYear,
            String prmBodyType, String prmFuelType, String prmCategory) {
        attVehicleService.opUpdateVehicle(
                prmId, prmBrand, prmModel, prmYear,
                prmBodyType, prmFuelType, prmCategory);
    }

    /**
     * Inactiva una línea de vehículos.
     *
     * @param prmId identificador del vehículo
     */
    public void opDisableVehicle(int prmId) {
        attVehicleService.opDisableVehicle(prmId);
    }

    /**
     * Obtiene todos los vehículos registrados.
     *
     * @return lista de {@link clsVehicle}
     */
    public List<clsVehicle> opGetAllVehicles() {
        return attVehicleService.opGetAllVehicles();
    }

    // endregion

    // region UNIT

    /**
     * Registra una nueva unidad física de vehículo en inventario.
     *
     * @param prmIdUnit       identificador de la unidad
     * @param prmIdVehicle    identificador de la línea de vehículo
     * @param prmIdDealership identificador de la concesionaria
     * @param prmLicensePlate placa (formato AAA000)
     * @param prmColor        color de la unidad
     * @param prmMileage      kilometraje
     * @param prmDateEntry    fecha de ingreso al inventario
     * @param prmCondition    condición (new/used)
     */
    public void opRegisterUnit(int prmIdUnit, int prmIdVehicle, int prmIdDealership,
            String prmLicensePlate, String prmColor, int prmMileage,
            Date prmDateEntry, String prmCondition) {
        attUnitService.opRegisterUnit(
                prmIdVehicle, prmIdUnit, prmIdDealership,
                "available", prmLicensePlate, prmColor,
                prmMileage, prmDateEntry, prmCondition);
    }

    /**
     * Actualiza los datos editables de una unidad.
     *
     * @param prmId           identificador de la unidad
     * @param prmLicensePlate nueva placa (null para no modificar)
     * @param prmColor        nuevo color (null para no modificar)
     * @param prmMileage      nuevo kilometraje (null para no modificar)
     * @param prmCondition    nueva condición (null para no modificar)
     */
    public void opUpdateUnit(int prmId, String prmLicensePlate, String prmColor,
            Integer prmMileage, String prmCondition) {
        attUnitService.opUpdateUnit(prmId, prmLicensePlate, prmColor, prmMileage, prmCondition);
    }

    /**
     * Cambia el estado operativo de una unidad.
     *
     * @param prmId     identificador de la unidad
     * @param prmStatus nuevo estado (available/reserved/sold)
     */
    public void opUpdateUnitStatus(int prmId, String prmStatus) {
        attUnitService.opUpdateUnitStatus(prmId, prmStatus);
    }

    /**
     * Obtiene una unidad por su identificador.
     *
     * @param prmId identificador de la unidad
     * @return objeto {@link clsUnit}
     */
    public clsUnit opGetUnitById(int prmId) {
        return attUnitService.opGetUnitById(prmId);
    }

    /**
     * Obtiene el inventario completo de unidades.
     *
     * @return lista de {@link clsUnit}
     */
    public List<clsUnit> opGetAllUnits() {
        return attUnitService.opGetAllUnits();
    }

    /**
     * Obtiene las unidades disponibles para venta o reserva.
     *
     * @return lista de {@link clsUnit} con estado available
     */
    public List<clsUnit> opGetAvailableUnits() {
        return attUnitService.opGetUnitsByStatus("available");
    }

    /**
     * Obtiene las unidades actualmente reservadas.
     *
     * @return lista de {@link clsUnit} con estado reserved
     */
    public List<clsUnit> opGetReservedUnits() {
        return attUnitService.opGetUnitsByStatus("reserved");
    }

    /**
     * Obtiene las unidades ya vendidas.
     *
     * @return lista de {@link clsUnit} con estado sold
     */
    public List<clsUnit> opGetSoldUnits() {
        return attUnitService.opGetUnitsByStatus("sold");
    }

    // endregion

    // region SALE

    /**
     * Registra una venta directa (sin reserva previa).
     *
     * @param prmIdCustomer identificador del cliente
     * @param prmIdEmployee identificador del empleado vendedor
     * @param prmIdUnit     identificador de la unidad vendida
     * @param prmPrice      precio de venta
     */
    public void opRegisterSale(int prmIdCustomer, int prmIdEmployee,
            int prmIdUnit, double prmPrice) {
        attSaleService.opRegisterSale(prmIdCustomer, prmIdEmployee, prmIdUnit, prmPrice);
    }

    /**
     * Registra una reserva de vehículo.
     *
     * @param prmIdCustomer identificador del cliente
     * @param prmIdEmployee identificador del empleado
     * @param prmIdUnit     identificador de la unidad reservada
     * @param prmPrice      precio acordado
     * @param prmDateEnd    fecha límite de la reserva
     */
    public void opRegisterReservation(int prmIdCustomer, int prmIdEmployee,
            int prmIdUnit, double prmPrice, Date prmDateEnd) {
        attSaleService.opRegisterReservation(prmIdCustomer, prmIdEmployee, prmIdUnit, prmPrice, prmDateEnd);
    }

    /**
     * Completa una reserva existente convirtiéndola en venta confirmada.
     *
     * @param prmId identificador de la reserva
     */
    public void opCompleteReservation(int prmId) {
        attSaleService.opCompleteReservation(prmId);
    }

    /**
     * Cancela una reserva activa.
     *
     * @param prmId identificador de la reserva
     */
    public void opCancelReservation(int prmId) {
        attSaleService.opCancelReservation(prmId);
    }

    /**
     * Obtiene una venta por su identificador.
     *
     * @param prmId identificador de la venta
     * @return objeto {@link clsSale}
     */
    public clsSale opGetSaleById(int prmId) {
        return attSaleService.opGetSaleById(prmId);
    }

    /**
     * Obtiene el historial completo de ventas y reservas.
     *
     * @return lista de {@link clsSale}
     */
    public List<clsSale> opGetAllSales() {
        return attSaleService.opGetAllSales();
    }

    /**
     * Obtiene las ventas filtradas por estado.
     *
     * @param prmStatus estado a filtrar (confirmed/cancelled/inprogress)
     * @return lista de {@link clsSale}
     */
    public List<clsSale> opGetSalesByStatus(String prmStatus) {
        return attSaleService.opGetSalesByStatus(prmStatus);
    }

    // endregion

    // region SALESGOAL

    /**
     * Registra una meta de ventas para una concesionaria o empleado específico.
     *
     * @param prmIdDealership identificador de la concesionaria
     * @param prmIdEmployee   identificador del empleado (null si es meta de concesionaria)
     * @param prmGoalType     tipo de meta (monthly/quarterly/yearly)
     * @param prmTargetValue  valor objetivo
     * @param prmStartDate    fecha de inicio
     */
    public void opRegisterSalesGoal(int prmIdDealership, Integer prmIdEmployee,
            String prmGoalType, int prmTargetValue, Date prmStartDate) {
        attSalesGoalService.opRegisterSalesGoal(
                prmIdDealership, prmIdEmployee,
                prmGoalType, prmTargetValue, prmStartDate);
    }

    /**
     * Actualiza los parámetros de una meta de ventas activa.
     *
     * @param prmId          identificador de la meta
     * @param prmTargetValue nuevo valor objetivo (null para no modificar)
     * @param prmGoalType    nuevo tipo de meta (null para no modificar)
     */
    public void opUpdateSalesGoal(int prmId, Integer prmTargetValue, String prmGoalType) {
        attSalesGoalService.opUpdateSalesGoal(prmId, prmTargetValue, prmGoalType);
    }

    /**
     * Inactiva una meta de ventas que no haya sido completada.
     *
     * @param prmId identificador de la meta
     */
    public void opDisableSalesGoal(int prmId) {
        attSalesGoalService.opDisableSalesGoal(prmId);
    }

    /**
     * Marca una meta de ventas como completada.
     *
     * @param prmId identificador de la meta
     */
    public void opCompleteSalesGoal(int prmId) {
        attSalesGoalService.opCompleteSalesGoal(prmId);
    }

    /**
     * Obtiene una meta de ventas por su identificador.
     *
     * @param prmId identificador de la meta
     * @return objeto {@link clsSalesGoal}
     */
    public clsSalesGoal opGetSalesGoalById(int prmId) {
        return attSalesGoalService.opGetSalesGoalById(prmId);
    }

    /**
     * Obtiene la lista completa de metas de ventas.
     *
     * @return lista de {@link clsSalesGoal}
     */
    public List<clsSalesGoal> opGetAllSalesGoals() {
        return attSalesGoalService.opGetAllSalesGoals();
    }

    /**
     * Obtiene las metas filtradas por estado.
     *
     * @param prmState estado a filtrar (active/inactive/complete)
     * @return lista de {@link clsSalesGoal}
     */
    public List<clsSalesGoal> opGetSalesGoalsByState(String prmState) {
        return attSalesGoalService.opGetSalesGoalsByState(prmState);
    }

    // endregion
}