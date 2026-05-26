package edu.unicauca.dsantiago135.concesionaria;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import edu.unicauca.dsantiago135.concesionaria.Controller.clsController;
import edu.unicauca.dsantiago135.concesionaria.Model.*;

@SpringBootApplication
public class ConcesionariaApplication {

	// region HELPERS
	private static void opPrint(String prmMsg) {
		System.out.println(prmMsg);
	}

	private static void opSection(String prmTitle) {
		System.out.println("\n========== " + prmTitle + " ==========");
	}

	private static void opBlock(String prmTitle) {
		System.out.println("\n--- " + prmTitle + " ---");
	}

	private static void opError(String prmContext, Exception prmEx) {
		System.out.println("  [ERROR] " + prmContext + ": " + prmEx.getMessage());
	}
	// endregion

	public static void main(String[] args) {
		ApplicationContext varContext = SpringApplication.run(ConcesionariaApplication.class, args);
		clsController varController = varContext.getBean(clsController.class);

		// =====================================================================
		// DEALERSHIP
		// =====================================================================
		opSection("DEALERSHIP");

		opBlock("Registrar");
		try {
			varController.opRegisterDealership(101, "Centro Motors", "active", "Calle 10 #5-20", "3001234567");
			opPrint("  Concesionaria 101 registrada.");
		} catch (Exception e) { opError("Register Dealership 101", e); }
		try {
			varController.opRegisterDealership(102, "Norte Autos", "active", "Av 30 #45-10", "3019876543");
			opPrint("  Concesionaria 102 registrada.");
		} catch (Exception e) { opError("Register Dealership 102", e); }

		opBlock("GetAll");
		try {
			List<clsDealership> varList = varController.opGetAllDealerships();
			for (clsDealership d : varList)
				opPrint("  [" + d.getAttDealershipId() + "] " + d.getAttName() + " | " + d.getAttAddress() + " | " + d.getAttPhone() + " | " + d.getAttState());
		} catch (Exception e) { opError("GetAll Dealerships", e); }

		opBlock("Update");
		try {
			varController.opUpdateDealership(101, "Centro Motors Plus", null, "3007654321");
			clsDealership varD = varController.opGetDealershipById(101);
			opPrint("  -> " + varD.getAttName() + " | " + varD.getAttPhone());
		} catch (Exception e) { opError("Update Dealership 101", e); }

		opBlock("Disable");
		try {
			varController.opDisableDealership(102);
			clsDealership varD2 = varController.opGetDealershipById(102);
			opPrint("  -> Estado: " + varD2.getAttState());
		} catch (Exception e) { opError("Disable Dealership 102", e); }

		// =====================================================================
		// VEHICLE
		// =====================================================================
		opSection("VEHICLE");

		opBlock("Registrar");
		try {
			varController.opRegisterVehicle(101, "active", "Toyota", "Corolla", 2022, "Sedan", "gasoline", "standard");
			opPrint("  Vehículo 101 registrado.");
		} catch (Exception e) { opError("Register Vehicle 101", e); }
		try {
			varController.opRegisterVehicle(102, "active", "BMW", "X5", 2023, "SUV", "hybrid", "luxury");
			opPrint("  Vehículo 102 registrado.");
		} catch (Exception e) { opError("Register Vehicle 102", e); }

		opBlock("GetAll");
		try {
			List<clsVehicle> varList = varController.opGetAllVehicles();
			for (clsVehicle v : varList)
				opPrint("  [" + v.getAttVehicleId() + "] " + v.getAttBrand() + " " + v.getAttModel()
						+ " " + v.getAttYear() + " | " + v.getAttFuelType() + " | " + v.getAttCategory() + " | " + v.getAttState());
		} catch (Exception e) { opError("GetAll Vehicles", e); }

		opBlock("Update");
		try {
			varController.opUpdateVehicle(101, null, "Corolla Cross", 2023, null, null, null);
			opPrint("  Vehículo 101 actualizado.");
		} catch (Exception e) { opError("Update Vehicle 101", e); }

		opBlock("Disable");
		try {
			varController.opDisableVehicle(102);
			opPrint("  Vehículo 102 desactivado.");
		} catch (Exception e) { opError("Disable Vehicle 102", e); }

		// =====================================================================
		// EMPLOYEE
		// =====================================================================
		opSection("EMPLOYEE");

		opBlock("Registrar");
		try {
			varController.opRegisterEmployee(101, 101, "Carlos Gomez", "3101234567", 3500000, Date.valueOf("2022-01-15"), "seller", "active");
			opPrint("  Empleado 101 registrado.");
		} catch (Exception e) { opError("Register Employee 101", e); }
		try {
			varController.opRegisterEmployee(102, 101, "Laura Martinez", "3209876543", 5000000, Date.valueOf("2021-06-01"), "manager", "active");
			opPrint("  Empleado 102 registrado.");
		} catch (Exception e) { opError("Register Employee 102", e); }

		opBlock("GetById");
		try {
			clsEmployee varEmp = varController.opGetEmployeeById(101);
			opPrint("  [" + varEmp.getAttEmployeeId() + "] " + varEmp.getAttName()
					+ " | " + varEmp.getAttRole() + " | $" + varEmp.getAttSalary()
					+ " | " + varEmp.getAttDealership().getAttName());
		} catch (Exception e) { opError("GetById Employee 101", e); }

		opBlock("GetAll");
		try {
			List<clsEmployee> varList = varController.opGetAllEmployees();
			for (clsEmployee emp : varList)
				opPrint("  [" + emp.getAttEmployeeId() + "] " + emp.getAttName()
						+ " | " + emp.getAttRole() + " | $" + emp.getAttSalary() + " | " + emp.getAttState());
		} catch (Exception e) { opError("GetAll Employees", e); }

		opBlock("GetByDealership");
		try {
			List<clsEmployee> varList = varController.opGetEmployeesByDealership(101);
			for (clsEmployee emp : varList)
				opPrint("  [" + emp.getAttEmployeeId() + "] " + emp.getAttName());
		} catch (Exception e) { opError("GetByDealership 101", e); }

		opBlock("AboveAvg");
		try {
			List<clsEmployee> varList = varController.opGetEmployeesAboveAvgSalary();
			for (clsEmployee emp : varList)
				opPrint("  [" + emp.getAttEmployeeId() + "] " + emp.getAttName() + " | $" + emp.getAttSalary());
		} catch (Exception e) { opError("GetEmployeesAboveAvg", e); }

		opBlock("Update");
		try {
			varController.opUpdateEmployee(101, null, null, 4000000.0);
			clsEmployee varEmp = varController.opGetEmployeeById(101);
			opPrint("  -> Salario: $" + varEmp.getAttSalary());
		} catch (Exception e) { opError("Update Employee 101", e); }

		opBlock("Disable");
		try {
			varController.opDisableEmployee(102);
			clsEmployee varEmp = varController.opGetEmployeeById(102);
			opPrint("  -> Estado: " + varEmp.getAttState());
		} catch (Exception e) { opError("Disable Employee 102", e); }

		// =====================================================================
		// CUSTOMER
		// =====================================================================
		opSection("CUSTOMER");

		opBlock("Registrar");
		try {
			varController.opRegisterCustomer(101, "Juan Perez", "3151234567", "juan@email.com", "active");
			opPrint("  Cliente 101 registrado.");
		} catch (Exception e) { opError("Register Customer 101", e); }
		try {
			varController.opRegisterCustomer(102, "Maria Lopez", "3169876543", "maria@email.com", "active");
			opPrint("  Cliente 102 registrado.");
		} catch (Exception e) { opError("Register Customer 102", e); }

		opBlock("GetById");
		try {
			clsCustomer varCus = varController.opGetCustomerById(101);
			opPrint("  [" + varCus.getAttCustomerId() + "] " + varCus.getAttName()
					+ " | " + varCus.getAttEmail() + " | " + varCus.getAttPhone());
		} catch (Exception e) { opError("GetById Customer 101", e); }

		opBlock("GetAll");
		try {
			List<clsCustomer> varList = varController.opGetAllCustomers();
			for (clsCustomer c : varList)
				opPrint("  [" + c.getAttCustomerId() + "] " + c.getAttName()
						+ " | " + c.getAttEmail() + " | " + c.getAttState());
		} catch (Exception e) { opError("GetAll Customers", e); }

		opBlock("Update");
		try {
			varController.opUpdateCustomer(101, "Juan Perez Actualizado", null, "juan.nuevo@email.com");
			clsCustomer varCus = varController.opGetCustomerById(101);
			opPrint("  -> " + varCus.getAttName() + " | " + varCus.getAttEmail());
		} catch (Exception e) { opError("Update Customer 101", e); }

		opBlock("Disable");
		try {
			varController.opDisableCustomer(102);
			clsCustomer varCus = varController.opGetCustomerById(102);
			opPrint("  -> Estado: " + varCus.getAttState());
		} catch (Exception e) { opError("Disable Customer 102", e); }

		// =====================================================================
		// UNIT
		// =====================================================================
		opSection("UNIT");

		opBlock("Registrar");
		try {
			varController.opRegisterUnit(101, 101, 101, "ABC123", "Blanco", 0, Date.valueOf("2023-03-01"), "new");
			opPrint("  Unidad 101 registrada.");
		} catch (Exception e) { opError("Register Unit 101", e); }
		try {
			varController.opRegisterUnit(102, 101, 101, "XYZ789", "Negro", 15000, Date.valueOf("2022-08-10"), "used");
			opPrint("  Unidad 102 registrada.");
		} catch (Exception e) { opError("Register Unit 102", e); }

		opBlock("GetById");
		try {
			clsUnit varUnit = varController.opGetUnitById(101);
			opPrint("  [" + varUnit.getAttUnitId() + "] " + varUnit.getAttLicensePlate()
					+ " | " + varUnit.getAttColor() + " | " + varUnit.getAttCondition()
					+ " | " + varUnit.getAttStatus()
					+ " | " + varUnit.getAttVehicle().getAttBrand()
					+ " | " + varUnit.getAttDealership().getAttName());
		} catch (Exception e) { opError("GetById Unit 101", e); }

		opBlock("GetAll");
		try {
			List<clsUnit> varList = varController.opGetAllUnits();
			for (clsUnit u : varList)
				opPrint("  [" + u.getAttUnitId() + "] " + u.getAttLicensePlate()
						+ " | " + u.getAttStatus() + " | km:" + u.getAttMileage());
		} catch (Exception e) { opError("GetAll Units", e); }

		opBlock("GetAvailable");
		try {
			List<clsUnit> varList = varController.opGetAvailableUnits();
			for (clsUnit u : varList)
				opPrint("  [" + u.getAttUnitId() + "] " + u.getAttLicensePlate());
		} catch (Exception e) { opError("GetAvailableUnits", e); }

		opBlock("Update");
		try {
			varController.opUpdateUnit(102, null, "Gris Oscuro", 16000, null);
			clsUnit varUnit = varController.opGetUnitById(102);
			opPrint("  -> Color: " + varUnit.getAttColor() + " | km: " + varUnit.getAttMileage());
		} catch (Exception e) { opError("Update Unit 102", e); }

		opBlock("UpdateStatus");
		try {
			varController.opUpdateUnitStatus(102, "reserved");
			clsUnit varUnit = varController.opGetUnitById(102);
			opPrint("  -> Estado: " + varUnit.getAttStatus());
		} catch (Exception e) { opError("UpdateStatus Unit 102", e); }

		// =====================================================================
		// SALE
		// =====================================================================
		opSection("SALE");

		opBlock("Registrar venta directa");
		try {
			varController.opRegisterSale(101, 101, 101, 85000000);
			opPrint("  Venta directa registrada.");
		} catch (Exception e) { opError("RegisterSale", e); }

		opBlock("Registrar reserva");
		try {
			varController.opRegisterReservation(101, 101, 102, 120000000, Date.valueOf("2024-12-31"));
			opPrint("  Reserva registrada.");
		} catch (Exception e) { opError("RegisterReservation", e); }

		opBlock("GetAll");
		List<clsSale> varSales = List.of();
		try {
			varSales = varController.opGetAllSales();
			for (clsSale s : varSales)
				opPrint("  [" + s.getAttSaleId() + "] " + s.getAttCustomer().getAttName()
						+ " | " + s.getAttEmployee().getAttName()
						+ " | $" + s.getAttPrice() + " | " + s.getAttStatus());
		} catch (Exception e) { opError("GetAll Sales", e); }

		opBlock("GetByStatus inprogress");
		List<clsSale> varInProgress = List.of();
		try {
			varInProgress = varController.opGetSalesByStatus("inprogress");
			for (clsSale s : varInProgress)
				opPrint("  [" + s.getAttSaleId() + "] " + s.getAttStatus());
		} catch (Exception e) { opError("GetSalesByStatus inprogress", e); }

		opBlock("GetById");
		try {
			if (!varSales.isEmpty()) {
				clsSale varSale = varController.opGetSaleById(varSales.get(0).getAttSaleId());
				opPrint("  [" + varSale.getAttSaleId() + "] $" + varSale.getAttPrice()
						+ " | " + varSale.getAttStatus() + " | " + varSale.getAttDateStart());
			}
		} catch (Exception e) { opError("GetSaleById", e); }

		opBlock("CompleteReservation");
		try {
			if (!varInProgress.isEmpty()) {
				int varResId = varInProgress.get(0).getAttSaleId();
				varController.opCompleteReservation(varResId);
				clsSale varCompleted = varController.opGetSaleById(varResId);
				opPrint("  Reserva " + varResId + " -> Estado: " + varCompleted.getAttStatus());
			} else {
				opPrint("  Sin reservas inprogress para completar.");
			}
		} catch (Exception e) { opError("CompleteReservation", e); }

		opBlock("CancelReservation");
		try {
			varController.opRegisterReservation(101, 101, 102, 95000000, Date.valueOf("2024-11-30"));
			List<clsSale> varInProgress2 = varController.opGetSalesByStatus("inprogress");
			if (!varInProgress2.isEmpty()) {
				int varResId2 = varInProgress2.get(0).getAttSaleId();
				varController.opCancelReservation(varResId2);
				clsSale varCancelled = varController.opGetSaleById(varResId2);
				opPrint("  Reserva " + varResId2 + " -> Estado: " + varCancelled.getAttStatus());
			} else {
				opPrint("  Sin reservas inprogress para cancelar.");
			}
		} catch (Exception e) { opError("CancelReservation", e); }

		opBlock("GetByStatus confirmed");
		try {
			List<clsSale> varConfirmed = varController.opGetSalesByStatus("confirmed");
			for (clsSale s : varConfirmed)
				opPrint("  [" + s.getAttSaleId() + "] " + s.getAttStatus());
		} catch (Exception e) { opError("GetSalesByStatus confirmed", e); }

		opPrint("\n========== FIN DE PRUEBAS ==========\n");
	}
}