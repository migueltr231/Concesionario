package edu.unicauca.dsantiago135.concesionaria.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;
import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;
import edu.unicauca.dsantiago135.concesionaria.Repository.UnitRepository;

@Service
public class UnitService {

   // region ATTRIBUTES
   private final UnitRepository attUnitRepository;
   private final VehicleService attVehicleService;
   private final DealershipService attDealershipService;
   private HashMap<Integer, clsUnit> attUnits = new HashMap<>();
   // endregion

   // region CONSTRUCTOR
   public UnitService(UnitRepository prmUnitRepository, VehicleService prmVehicleService,
         DealershipService prmDealershipService) {
      this.attUnitRepository = prmUnitRepository;
      this.attVehicleService = prmVehicleService;
      this.attDealershipService = prmDealershipService;
   }
   // endregion

   // region PRIVATE HELPERS
   private clsUnit opFetchFromDB(int prmId) {
      clsUnit varUnit;
      try {
         varUnit = attUnitRepository.opGetUnitById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener la unidad desde BD: ", e);
      }
      if (varUnit == null)
         throw new excNotFoundException("Unidad no encontrada");

      clsVehicle varVehicle = attVehicleService
            .opGetVehicleById(varUnit.getAttVehicle().getAttVehicleId());
      clsDealership varDealership = attDealershipService
            .opGetDealershipById(varUnit.getAttDealership().getAttDealershipId());
      varUnit.setAttVehicle(varVehicle);
      varUnit.setAttDealership(varDealership);
      return varUnit;
   }

   private void opRefreshCache(int prmId) {
      attUnits.remove(prmId);
      clsUnit varFresh = opFetchFromDB(prmId);
      attUnits.put(prmId, varFresh);
   }
   // endregion

   // region PROCEDURES
   public void opRegisterUnit(int prmIdVehicle, int prmIdUnit, int prmIdDealership, String prmStatus,
         String prmLicensePlate, String prmColor, int prmMileage, Date prmDateEntry, String prmCondition) {

      clsValidations.opValidateId(prmIdUnit);
      
      if (attUnitRepository.opUnitExist(prmIdUnit))
         throw new excDuplicateDataException("La unidad ya fue registrada. ");

      clsValidations.opValidateId(prmIdDealership);
      clsValidations.opValidateId(prmIdVehicle);
      clsValidations.opValidateLicensePlate(prmLicensePlate);
      clsValidations.opValidateUnitStatus(prmStatus);
      clsValidations.opValidateUnitCondition(prmCondition);
      clsValidations.opValidateDate(prmDateEntry);
      clsValidations.opValidateName(prmColor, 0, 15);
      clsValidations.opValidatePositiveNumber(prmMileage, "el kilometraje");

      clsUnit varUnitTemp = new clsUnit();
      clsVehicle varVehicle = attVehicleService.opGetVehicleById(prmIdVehicle);
      clsDealership varDealership = attDealershipService.opGetDealershipById(prmIdDealership);
      varUnitTemp.setAttUnitId(prmIdUnit);
      varUnitTemp.setAttVehicle(varVehicle);
      varUnitTemp.setAttDealership(varDealership);
      varUnitTemp.setAttColor(prmColor);
      varUnitTemp.setAttCondition(prmCondition);
      varUnitTemp.setAttDateEntry(prmDateEntry);
      varUnitTemp.setAttLicensePlate(prmLicensePlate);
      varUnitTemp.setAttMileage(prmMileage);
      varUnitTemp.setAttStatus(prmStatus);

      try {
         attUnitRepository.opRegisterUnit(varUnitTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar unidad: ", e);
      }

      opRefreshCache(prmIdUnit);
   }

   public void opUpdateUnit(int prmIdUnit, String prmLicensePlate, String prmColor, Integer prmMileage,
         String prmCondition) {

      clsValidations.opValidateId(prmIdUnit);
      if (!attUnitRepository.opUnitExist(prmIdUnit))
         throw new excNotFoundException("La unidad no encontrada. ");

      if (prmLicensePlate != null)
         clsValidations.opValidateLicensePlate(prmLicensePlate);
      if (prmCondition != null)
         clsValidations.opValidateUnitCondition(prmCondition);
      if (prmColor != null)
         clsValidations.opValidateName(prmColor, 0, 15);
      if (prmMileage != null)
         clsValidations.opValidatePositiveNumber(prmMileage, "el kilometraje");

      clsUnit varUnit = opFetchFromDB(prmIdUnit);

      if (prmLicensePlate != null)
         varUnit.setAttLicensePlate(prmLicensePlate);
      if (prmCondition != null)
         varUnit.setAttCondition(prmCondition);
      if (prmColor != null)
         varUnit.setAttColor(prmColor);
      if (prmMileage != null)
         varUnit.setAttMileage(prmMileage);

      try {
         attUnitRepository.opUpdateUnit(varUnit);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar unidad", e);
      }

      opRefreshCache(prmIdUnit);
   }

   public void opUpdateUnitStatus(int prmId, String prmStatus) {
      clsValidations.opValidateId(prmId);
      if (!attUnitRepository.opUnitExist(prmId))
         throw new excNotFoundException("Unidad no encontrada");

      clsValidations.opValidateUnitStatus(prmStatus);

      try {
         attUnitRepository.opUpdateUnitStatus(prmId, prmStatus);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al modificar el estado de la unidad: ", e);
      }

      opRefreshCache(prmId);
   }
   // endregion

   // region FUNCTIONS
   public clsUnit opGetUnitById(int prmId) {

      clsValidations.opValidateId(prmId);

      clsUnit varUnit = attUnits.get(prmId);
      if (varUnit != null)
         return varUnit;

      varUnit = opFetchFromDB(prmId);
      attUnits.put(prmId, varUnit);
      return varUnit;
   }

   public List<clsUnit> opGetAllUnits() {
      List<clsUnit> varUnits;
      try {
         varUnits = attUnitRepository.opGetAllUnits();
         for (clsUnit varUnit : varUnits) {
            clsVehicle varVehicle = attVehicleService
                  .opGetVehicleById(varUnit.getAttVehicle().getAttVehicleId());
            clsDealership varDealership = attDealershipService
                  .opGetDealershipById(varUnit.getAttDealership().getAttDealershipId());
            varUnit.setAttVehicle(varVehicle);
            varUnit.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener unidades: ", e);
      }
      return varUnits;
   }

   public List<clsUnit> opGetUnitsByStatus(String prmStatus) {
      clsValidations.opValidateUnitStatus(prmStatus);
      List<clsUnit> varUnits;
      try {
         varUnits = attUnitRepository.opGetUnitsByStatus(prmStatus);
         for (clsUnit varUnit : varUnits) {
            clsVehicle varVehicle = attVehicleService
                  .opGetVehicleById(varUnit.getAttVehicle().getAttVehicleId());
            clsDealership varDealership = attDealershipService
                  .opGetDealershipById(varUnit.getAttDealership().getAttDealershipId());
            varUnit.setAttVehicle(varVehicle);
            varUnit.setAttDealership(varDealership);
         }
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener unidades: ", e);
      }
      return varUnits;
   }
   // endregion
}