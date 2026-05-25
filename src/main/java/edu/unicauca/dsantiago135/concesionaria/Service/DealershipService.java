package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Repository.DealershipRepository;

@Service
public class DealershipService {

   // region ATTRIBUTES
   private final DealershipRepository attDealershipRepository;
   private HashMap<Integer, clsDealership> attDealerships = new HashMap<>();
   // endregion

   // region CONSTRUCTOR
   public DealershipService(DealershipRepository prmDealershipRepository) {
      this.attDealershipRepository = prmDealershipRepository;
   }
   // endregion

   // region PRIVATE HELPERS
   private clsDealership opFetchFromDB(int prmId) {
      clsDealership varDealership;
      try {
         varDealership = attDealershipRepository.opGetDealershipById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener la concesionaria desde BD: ", e);
      }
      if (varDealership == null)
         throw new excNotFoundException("Concesionaria no encontrada");
      return varDealership;
   }

   private void opRefreshCache(int prmId) {
      attDealerships.remove(prmId);
      clsDealership varFresh = opFetchFromDB(prmId);
      attDealerships.put(prmId, varFresh);
   }
   // endregion

   // region PROCEDURES
   public void opRegisterDealership(int prmId, String prmName, String prmState, String prmAddress, String prmPhone) {
      clsValidations.opValidateId(prmId);

      if (attDealershipRepository.opDealershipExist(prmId))
         throw new excDuplicateDataException("Error Concesionaria ya registrada.");

      clsValidations.opValidateName(prmName, 3, 40);

      if (prmAddress == null || prmAddress.isBlank())
         throw new excValidationException("Error: Dirección vacía o nula");

      clsValidations.opValidatePhone(prmPhone);
      clsValidations.opValidateState(prmState);

      clsDealership varTemp = new clsDealership();
      varTemp.setAttDealershipId(prmId);
      varTemp.setAttName(prmName);
      varTemp.setAttAddress(prmAddress);
      varTemp.setAttPhone(prmPhone);
      varTemp.setAttState(prmState);

      try {
         attDealershipRepository.opRegisterDealership(varTemp);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar Concesionaria: ", e);
      }

      opRefreshCache(prmId);
   }

   public void opUpdateDealership(int prmId, String prmName, String prmAddress, String prmPhone) {
      clsValidations.opValidateId(prmId);

      if (!attDealershipRepository.opDealershipExist(prmId))
         throw new excNotFoundException("Concesionaria no encontrada");

      if (prmName != null)
         clsValidations.opValidateName(prmName, 3, 40);
      if (prmAddress != null && prmAddress.isBlank())
         throw new excValidationException("Error: Dirección vacía");
      if (prmPhone != null)
         clsValidations.opValidatePhone(prmPhone);

      clsDealership varDealership = opFetchFromDB(prmId);

      if (prmName != null)
         varDealership.setAttName(prmName);
      if (prmAddress != null)
         varDealership.setAttAddress(prmAddress);
      if (prmPhone != null)
         varDealership.setAttPhone(prmPhone);

      try {
         attDealershipRepository.opUpdateDealership(varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar concesionaria: ", e);
      }

      opRefreshCache(prmId);
   }

   public void opDisableDealership(int prmId) {
      clsValidations.opValidateId(prmId);

      if(!attDealershipRepository.opDealershipExist(prmId)) throw new excNotFoundException("Concesionaria no encontrada");

      try {
         attDealershipRepository.opDisableDealership(prmId);
      } catch (excNotFoundException e) {
         throw e;
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al inactivar concesionaria: ", e);
      }

      opRefreshCache(prmId);
   }
   // endregion

   // region FUNCTIONS
   public clsDealership opGetDealershipById(int prmId) {
      clsValidations.opValidateId(prmId);

      clsDealership varDealership = attDealerships.get(prmId);
      if (varDealership != null)
         return varDealership;

      varDealership = opFetchFromDB(prmId);
      attDealerships.put(prmId, varDealership);
      return varDealership;
   }

   public List<clsDealership> opGetAllDealership() {
      List<clsDealership> varDealerships;
      try {
         varDealerships = attDealershipRepository.opGetAllDealership();
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener concesionarias: ", e);
      }
      return varDealerships;
   }
   // endregion
}