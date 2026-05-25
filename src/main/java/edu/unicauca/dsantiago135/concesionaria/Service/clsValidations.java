package edu.unicauca.dsantiago135.concesionaria.Service;

import java.sql.Date;
import java.time.Year;

import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;

public class clsValidations {

   private clsValidations() {
   }

   public static void opValidatePhone(String prmPhone) throws excValidationException {
      if (prmPhone == null || !prmPhone.matches("^\\d{10}$"))
         throw new excValidationException("Error: Teléfono con caracteres no numéricos o nulo");
   }

   public static void opValidateEmail(String prmEmail) throws excValidationException {
      if (prmEmail != null)
         if (!prmEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new excValidationException("Error: Email inválido");
   }

   public static void opValidateState(String prmState) throws excValidationException {
      if (prmState == null || prmState.isBlank())
         throw new excValidationException("Error: Estado vacío o nulo");
      if (!prmState.equals("active") && !prmState.equals("inactive"))
         throw new excValidationException("Error: Estado no permitido");
   }

   public static void opValidateDate(Date prmDate) throws excValidationException {
      if (prmDate == null || prmDate.after(new Date(System.currentTimeMillis())))
         throw new excValidationException("Error: Fecha nula o futura");
   }

   public static void opValidateName(String prmName, int prmMin, int prmMax) throws excValidationException {
      if (prmName == null || prmName.isBlank())
         throw new excValidationException("Error: Nombre vacío o nulo");
      if (!prmName.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))
         throw new excValidationException("Error: caracteres no permitidos en el nombre");
      if (prmName.length() < prmMin || prmName.length() > prmMax)
         throw new excValidationException("Error: Nombre fuera del rango");
   }

   public static void opValidatePositiveNumber(double prmValue, String prmField) throws excValidationException {
      if (prmValue < 0)
         throw new excValidationException("Error: " + prmField + " no puede ser negativo");
   }

   public static void opValidateLicensePlate(String prmPlate) throws excValidationException {
      if (prmPlate == null || !prmPlate.toUpperCase().trim().matches("^[A-Z]{3}[0-9]{3}$"))
         throw new excValidationException("Error: Placa inválida");
   }

   public static void opValidateUnitCondition(String prmCondition) throws excValidationException {
      if (prmCondition == null || (!prmCondition.equals("new") && !prmCondition.equals("used")))
         throw new excValidationException("Error: Condición inválida");
   }

   public static void opValidateUnitStatus(String prmUnitStatus) throws excValidationException {
      if (prmUnitStatus == null || prmUnitStatus.isBlank())
         throw new excValidationException("Error: Estado de unidad vacío o nulo");
      if (!prmUnitStatus.equals("available") && !prmUnitStatus.equals("reserved") && !prmUnitStatus.equals("sold"))
         throw new excValidationException("Error: Estado de unidad inválido");
   }

   public static void opValidateSaleStatus(String prmStatus) throws excValidationException {
      if (prmStatus == null || prmStatus.isBlank())
         throw new excValidationException("Error: Estado de venta vacío o nulo");
      if (!prmStatus.equals("confirmed") && !prmStatus.equals("cancelled") && !prmStatus.equals("inprogress"))
         throw new excValidationException("Error: Estado de venta inválido");
   }

   public static void opValidateSalesGoalState(String prmState) throws excValidationException {
      if (prmState == null || prmState.isBlank())
         throw new excValidationException("Error: Estado de meta vacío o nulo");
      if (!prmState.equals("inactive") && !prmState.equals("active") && !prmState.equals("complete"))
         throw new excValidationException("Error: Estado de meta inválido");
   }

   public static void opValidateGoalType(String prmGoalType) throws excValidationException {
      if (prmGoalType == null || prmGoalType.isBlank())
         throw new excValidationException("Error: Tipo de meta vacío o nulo");
      if (!prmGoalType.equals("monthly") && !prmGoalType.equals("quarterly") && !prmGoalType.equals("yearly"))
         throw new excValidationException("Error: Tipo de meta inválido");
   }

   public static void opValidateDateRange(Date prmStartDate, Date prmEndDate) throws excValidationException {
      if (prmEndDate != null && prmEndDate.before(prmStartDate))
         throw new excValidationException("Error: La fecha final no puede ser menor a la inicial");
   }

   public static void opValidateVehicleFuelType(String prmFuelType) throws excValidationException {
      if (prmFuelType == null || prmFuelType.isBlank())
         throw new excValidationException("Error: Tipo de combustible vacío o nulo");
      if (!prmFuelType.equals("electric") && !prmFuelType.equals("gasoline") && !prmFuelType.equals("hybrid"))
         throw new excValidationException("Error: Tipo de combustible no permitido");
   }

   public static void opValidateVehicleCategory(String prmCategory) throws excValidationException {
      if (prmCategory == null || prmCategory.isBlank())
         throw new excValidationException("Error: Categoría vacía o nula");
      if (!prmCategory.equals("standard") && !prmCategory.equals("luxury"))
         throw new excValidationException("Error: Categoría no permitida");
   }

   public static void opValidateYear(int prmYear) throws excValidationException {
      int varCurrentYear = Year.now().getValue();
      if (prmYear < 1900 || prmYear > varCurrentYear)
         throw new excValidationException("Error: Año fuera del rango permitido");
   }

   public static void opValidateId(int prmId) throws excValidationException {
      if (prmId <= 0)
         throw new excValidationException("Error: Id negativo");
      if (String.valueOf(prmId).length() != 10)
         throw new excValidationException("Error: Id vacío o incompleto");
   }

   public static void opValidateAlphanumericField(String prmValue, String prmField, int prmMin, int prmMax)
         throws excValidationException {
      if (prmValue == null || prmValue.isBlank())
         throw new excValidationException("Error: " + prmField + " vacío o nulo");
      if (!prmValue.matches("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ \\-]+$"))
         throw new excValidationException("Error: caracteres no permitidos en " + prmField);
      if (prmValue.length() < prmMin || prmValue.length() > prmMax)
         throw new excValidationException("Error: " + prmField + " fuera del rango");
   }
}
