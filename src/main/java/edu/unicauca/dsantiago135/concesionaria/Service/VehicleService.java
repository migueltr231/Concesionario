package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;
import edu.unicauca.dsantiago135.concesionaria.Repository.VehicleRepository;

@Service
public class VehicleService {

   // region ATTRIBUTES
   private final VehicleRepository attVehicleRepository;
   private HashMap<Integer, clsVehicle> attVehicles = new HashMap<>();
   // endregion

   // region CONSTRUCTOR
   public VehicleService(VehicleRepository prmVehicleRepository) {
      this.attVehicleRepository = prmVehicleRepository;
   }
   // endregion

   // region PRIVATE HELPERS
   private clsVehicle opFetchFromDB(int prmId) {
      clsVehicle varVehicle;
      try {
         varVehicle = attVehicleRepository.opGetVehicleById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener el vehículo desde BD: ", e);
      }
      if (varVehicle == null)
         throw new excNotFoundException("Vehículo no encontrado");
      return varVehicle;
   }

   private void opRefreshCache(int prmId) {
      attVehicles.remove(prmId);
      clsVehicle varFresh = opFetchFromDB(prmId);
      attVehicles.put(prmId, varFresh);
   }
   // endregion

   // region PROCEDURES
   public void opRegisterVehicle(int prmId, String prmState, String prmBrand, String prmModel, int prmYear,
         String prmBodyType, String prmFuelType, String prmCategory) {

      clsValidations.opValidateId(prmId);

      if (attVehicleRepository.opVehicleExist(prmId))
         throw new excDuplicateDataException("Vehiculo ya registrado");

      clsValidations.opValidateState(prmState);
      clsValidations.opValidateAlphanumericField(prmBrand, "la marca", 2, 40);
      clsValidations.opValidateAlphanumericField(prmModel, "el modelo", 1, 40);
      clsValidations.opValidateYear(prmYear);
      clsValidations.opValidateAlphanumericField(prmBodyType, "la carrocería", 2, 20);
      clsValidations.opValidateVehicleFuelType(prmFuelType);
      clsValidations.opValidateVehicleCategory(prmCategory);

      clsVehicle varTemp = new clsVehicle();
      varTemp.setAttVehicleId(prmId);
      varTemp.setAttState(prmState);
      varTemp.setAttBrand(prmBrand);
      varTemp.setAttModel(prmModel);
      varTemp.setAttYear(prmYear);
      varTemp.setAttBodyType(prmBodyType);
      varTemp.setAttFuelType(prmFuelType);
      varTemp.setAttCategory(prmCategory);

      try {
         attVehicleRepository.opRegisterVehicle(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar vehiculo: ", e);
      }

      opRefreshCache(prmId);
   }

   public void opUpdateVehicle(int prmId, String prmBrand, String prmModel, Integer prmYear,
         String prmBodyType, String prmFuelType, String prmCategory) {

      clsValidations.opValidateId(prmId);

      if (!attVehicleRepository.opVehicleExist(prmId))
         throw new excNotFoundException("El vehículo no fue encontrado.");

      if (prmBrand != null)
         clsValidations.opValidateAlphanumericField(prmBrand, "la marca", 2, 40);
      if (prmModel != null)
         clsValidations.opValidateAlphanumericField(prmModel, "el modelo", 1, 40);
      if (prmYear != null)
         clsValidations.opValidateYear(prmYear);
      if (prmBodyType != null && prmBodyType.isBlank())
         throw new excValidationException("Error: Tipo de carrocería vacío");
      if (prmFuelType != null)
         clsValidations.opValidateVehicleFuelType(prmFuelType);
      if (prmCategory != null)
         clsValidations.opValidateVehicleCategory(prmCategory);


      clsVehicle varVehicle = opFetchFromDB(prmId);

      if (prmBrand != null)
         varVehicle.setAttBrand(prmBrand);
      if (prmModel != null)
         varVehicle.setAttModel(prmModel);
      if (prmYear != null)
         varVehicle.setAttYear(prmYear);
      if (prmBodyType != null)
         varVehicle.setAttBodyType(prmBodyType);
      if (prmFuelType != null)
         varVehicle.setAttFuelType(prmFuelType);
      if (prmCategory != null)
         varVehicle.setAttCategory(prmCategory);

      try {
         attVehicleRepository.opUpdateVehicle(varVehicle);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar vehiculo: ", e);
      }

      opRefreshCache(prmId);
   }

   public void opDisableVehicle(int prmId) {
      clsValidations.opValidateId(prmId);

      if (!attVehicleRepository.opVehicleExist(prmId))throw new excNotFoundException("El vehículo no fue encontrado.");

      try {
         attVehicleRepository.opDisableVehicle(prmId);
      } catch (excNotFoundException e) {
         throw e;
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al inactivar vehiculo: ", e);
      }

      opRefreshCache(prmId);
   }
   // endregion

   // region FUNCTIONS
   public clsVehicle opGetVehicleById(int prmId) {
      clsValidations.opValidateId(prmId);

      clsVehicle varVehicle = attVehicles.get(prmId);
      if (varVehicle != null)
         return varVehicle;

      varVehicle = opFetchFromDB(prmId);
      attVehicles.put(prmId, varVehicle);
      return varVehicle;
   }

   public List<clsVehicle> opGetAllVehicles() {
      List<clsVehicle> varVehicles;
      try {
         varVehicles = attVehicleRepository.opGetAllVehicles();
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener vehiculos: ", e);
      }
      return varVehicles;
   }
   // endregion
}