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

   private final EmployeeRepository attEmployeeRepository;
   private final DealershipService attDealershipService;
   private HashMap<Integer, clsEmployee> attEmployees = new HashMap<>();

   public EmployeeService(EmployeeRepository prmEmployeeRepository, DealershipService prmDealershipService) {
      this.attEmployeeRepository = prmEmployeeRepository;
      this.attDealershipService = prmDealershipService;
   }

   public void opRegisterEmployee(int prmId, int prmDealershipId, String prmName, String prmPhone,double prmSalary, Date prmHireDate, String prmRole, String prmState){
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

      clsEmployee varEmployee = new clsEmployee();
      varEmployee.setAttEmployeeId(prmId);
      varEmployee.setAttDealership(varDealership);
      varEmployee.setAttName(prmName);
      varEmployee.setAttPhone(prmPhone);
      varEmployee.setAttSalary(prmSalary);
      varEmployee.setAttHireDate(prmHireDate);
      varEmployee.setAttRole(prmRole);
      varEmployee.setAttState(prmState);
      try {
         attEmployeeRepository.opRegisterEmployee(varEmployee);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar empleado: " , e);
      }
      attEmployees.put(prmId, varEmployee);
   }

   public void opUpdateEmployee(int prmId, String prmName, String prmPhone, Double prmSalary){
      clsValidations.opValidateId(prmId);

      if (!attEmployeeRepository.opEmployeeExist(prmId))
         throw new excNotFoundException("Empleado no encontrado");

      if (prmName != null)
         clsValidations.opValidateName(prmName, 3, 60);
      if (prmPhone != null)
         clsValidations.opValidatePhone(prmPhone);
      if (prmSalary != null)
         clsValidations.opValidatePositiveNumber(prmSalary, "Salario");

      clsEmployee varEmployee = attEmployees.get(prmId);
      if (varEmployee == null)
         varEmployee = attEmployeeRepository.opGetEmployeeById(prmId);
      if (prmName != null)
         varEmployee.setAttName(prmName);
      if (prmPhone != null)
         varEmployee.setAttPhone(prmPhone);
      if (prmSalary != null)
         varEmployee.setAttSalary(prmSalary);
      try {
         attEmployeeRepository.opUpdateEmployee(varEmployee);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar empleado: " , e);
      }
      attEmployees.put(prmId, varEmployee);
   }

   public void opDisableEmployee(int prmId){
      clsValidations.opValidateId(prmId);
      clsEmployee varEmployee = attEmployees.get(prmId);
      try {
         if (varEmployee == null)
            varEmployee = attEmployeeRepository.opGetEmployeeById(prmId);
         if (varEmployee == null)
            throw new excNotFoundException("Empleado no encontrado");
         attEmployeeRepository.opDisableEmployee(prmId);
         varEmployee.setAttState("inactive");
      } catch (excNotFoundException e) {
         throw e;
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al inactivar al empleado: " , e);
      }
      attEmployees.put(prmId, varEmployee);
   }

   public clsEmployee opGetEmployeeById(int prmId){
      clsValidations.opValidateId(prmId);
      clsEmployee varEmployee = attEmployees.get(prmId);
      try {
         if (varEmployee == null)
            varEmployee = attEmployeeRepository.opGetEmployeeById(prmId);
         if (varEmployee != null) {
            clsDealership varDealership = attDealershipService.opGetDealershipById(
                  varEmployee.getAttDealership().getAttDealershipId());
            varEmployee.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener el empleado: " , e);
      }
      if (varEmployee == null)
         throw new excNotFoundException("Empleado no encontrado");
      attEmployees.put(prmId, varEmployee);
      return varEmployee;
   }

   public List<clsEmployee> opGetEmployeesByDealership(int prmDealershipId){
      clsValidations.opValidateId(prmDealershipId);
      clsDealership varDealership = attDealershipService.opGetDealershipById(prmDealershipId);
      List<clsEmployee> varEmployees = null;
      try {
         varEmployees = attEmployeeRepository.opGetEmployeesByDealership(prmDealershipId);
         for (clsEmployee varEmployee : varEmployees)
            varEmployee.setAttDealership(varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: " , e);
      }
      return varEmployees;
   }

   public List<clsEmployee> opGetAllEmployees(){
      List<clsEmployee> varEmployees = null;
      try {
         varEmployees = attEmployeeRepository.opGetAllEmployees();
         for (clsEmployee varEmployee : varEmployees) {
            clsDealership varDealership = attDealershipService.opGetDealershipById(
                  varEmployee.getAttDealership().getAttDealershipId());
            varEmployee.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: " , e);
      }
      return varEmployees;
   }

   public List<clsEmployee> opGetEmployeesAboveAvg(){
      List<clsEmployee> varEmployees = null;
      try {
         varEmployees = attEmployeeRepository.opGetEmployeesAboveAvg();
         for (clsEmployee varEmployee : varEmployees) {
            clsDealership varDealership = attDealershipService.opGetDealershipById(
                  varEmployee.getAttDealership().getAttDealershipId());
            varEmployee.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener empleados: " , e);
      }
      return varEmployees;
   }
}