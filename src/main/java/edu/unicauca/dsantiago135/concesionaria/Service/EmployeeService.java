package edu.unicauca.dsantiago135.concesionaria.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Repository.EmployeeRepository;

@Service
public class EmployeeService {

   // region ATTRIBUTES
   private final EmployeeRepository attEmployeeRepository;
   private final DealershipService attDealershipService;
   private HashMap<Integer, clsEmployee> attEmployees = new HashMap<>();
   // endregion

   // region CONSTRUCTOR
   public EmployeeService(EmployeeRepository prmEmployeeRepository, DealershipService prmDealershipService) {
      this.attEmployeeRepository = prmEmployeeRepository;
      this.attDealershipService = prmDealershipService;
   }
   // endregion

   // region PRIVATE HELPERS
   private clsEmployee opFetchFromDB(int prmId) {
      clsEmployee varEmployee;
      try {
         varEmployee = attEmployeeRepository.opGetEmployeeById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener el empleado desde BD: ", e);
      }
      if (varEmployee == null)
         throw new excNotFoundException("Empleado no encontrado");

      clsDealership varDealership = attDealershipService
            .opGetDealershipById(varEmployee.getAttDealership().getAttDealershipId());
      varEmployee.setAttDealership(varDealership);
      return varEmployee;
   }

   private void opRefreshCache(int prmId) {
      attEmployees.remove(prmId);
      clsEmployee varFresh = opFetchFromDB(prmId);
      attEmployees.put(prmId, varFresh);
   }
   // endregion

   // region PROCEDURES
   public void opRegisterEmployee(int prmId, int prmDealershipId, String prmName, String prmPhone,
         double prmSalary, Date prmHireDate, String prmRole, String prmState) {

      clsValidations.opValidateId(prmId);
      clsValidations.opValidateId(prmDealershipId);

      if (attEmployeeRepository.opEmployeeExist(prmId))
         throw new excDuplicateDataException("Empleado ya registrado");

      clsValidations.opValidateName(prmName, 3, 60);
      clsValidations.opValidatePhone(prmPhone);
      clsValidations.opValidatePositiveNumber(prmSalary, "Salario");
      clsValidations.opValidateDate(prmHireDate);

      if (!prmRole.equals("seller") && !prmRole.equals("manager"))
         throw new excValidationException("Error: Rol no permitido");

      clsValidations.opValidateState(prmState);

      clsDealership varDealership = attDealershipService.opGetDealershipById(prmDealershipId);

      clsEmployee varTemp = new clsEmployee();
      varTemp.setAttEmployeeId(prmId);
      varTemp.setAttDealership(varDealership);
      varTemp.setAttName(prmName);
      varTemp.setAttPhone(prmPhone);
      varTemp.setAttSalary(prmSalary);
      varTemp.setAttHireDate(prmHireDate);
      varTemp.setAttRole(prmRole);
      varTemp.setAttState(prmState);

      try {
         attEmployeeRepository.opRegisterEmployee(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar empleado: ", e);
      }
      opRefreshCache(prmId);
   }

   public void opUpdateEmployee(int prmId, String prmName, String prmPhone, Double prmSalary) {
      clsValidations.opValidateId(prmId);

      if (!attEmployeeRepository.opEmployeeExist(prmId))
         throw new excNotFoundException("Empleado no encontrado");

      if (prmName != null)
         clsValidations.opValidateName(prmName, 3, 60);
      if (prmPhone != null)
         clsValidations.opValidatePhone(prmPhone);
      if (prmSalary != null)
         clsValidations.opValidatePositiveNumber(prmSalary, "Salario");

      clsEmployee varEmployee = opFetchFromDB(prmId);

      if (prmName != null)
         varEmployee.setAttName(prmName);
      if (prmPhone != null)
         varEmployee.setAttPhone(prmPhone);
      if (prmSalary != null)
         varEmployee.setAttSalary(prmSalary);

      try {
         attEmployeeRepository.opUpdateEmployee(varEmployee);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar empleado: ", e);
      }

      opRefreshCache(prmId);
   }

   public void opDisableEmployee(int prmId) {
      clsValidations.opValidateId(prmId);

      if(!attEmployeeRepository.opEmployeeExist(prmId)) throw new excNotFoundException("Empleado no encontrado");

      try {
         attEmployeeRepository.opDisableEmployee(prmId);
      } catch (excNotFoundException e) {
         throw e;
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al inactivar al empleado: ", e);
      }

      opRefreshCache(prmId);
   }
   // endregion

   // region FUNCTIONS
   public clsEmployee opGetEmployeeById(int prmId) {
      if(!attEmployeeRepository.opEmployeeExist(prmId)) throw new excNotFoundException("Empleado no encontrado");

      clsEmployee varEmployee = attEmployees.get(prmId);
      if (varEmployee != null)
         return varEmployee;

      varEmployee = opFetchFromDB(prmId);
      attEmployees.put(prmId, varEmployee);
      return varEmployee;
   }

   public List<clsEmployee> opGetEmployeesByDealership(int prmDealershipId) {
      clsValidations.opValidateId(prmDealershipId);
      clsDealership varDealership = attDealershipService.opGetDealershipById(prmDealershipId);

      List<clsEmployee> varEmployees;
      try {
         varEmployees = attEmployeeRepository.opGetEmployeesByDealership(prmDealershipId);
         for (clsEmployee varEmployee : varEmployees)
            varEmployee.setAttDealership(varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: ", e);
      }
      return varEmployees;
   }

   public List<clsEmployee> opGetAllEmployees() {
      List<clsEmployee> varEmployees;
      try {
         varEmployees = attEmployeeRepository.opGetAllEmployees();
         for (clsEmployee varEmployee : varEmployees) {
            clsDealership varDealership = attDealershipService
                  .opGetDealershipById(varEmployee.getAttDealership().getAttDealershipId());
            varEmployee.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: ", e);
      }
      return varEmployees;
   }

   public List<clsEmployee> opGetEmployeesAboveAvg() {
      List<clsEmployee> varEmployees;
      try {
         varEmployees = attEmployeeRepository.opGetEmployeesAboveAvg();
         for (clsEmployee varEmployee : varEmployees) {
            clsDealership varDealership = attDealershipService
                  .opGetDealershipById(varEmployee.getAttDealership().getAttDealershipId());
            varEmployee.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: ", e);
      }
      return varEmployees;
   }
   // endregion
}