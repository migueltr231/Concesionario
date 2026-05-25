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

   private final DealershipRepository attDealershipRepository;
   private HashMap<Integer, clsDealership> attDealerships = new HashMap<>();

   public DealershipService(DealershipRepository prmDealershipRepository) {
      this.attDealershipRepository = prmDealershipRepository;
   }

   public void opRegisterDealership(int prmId, String prmName, String prmState, String prmAddress, String prmPhone){
      clsValidations.opValidateId(prmId);

      if (attDealershipRepository.opDealershipExist(prmId))
         throw new excDuplicateDataException("Error Concesionaria ya registrada.");

      clsValidations.opValidateName(prmName, 3, 40);

      if (prmAddress == null || prmAddress.isBlank())
         throw new excValidationException("Error: Dirección vacía o nula");

      clsValidations.opValidatePhone(prmPhone);
      clsValidations.opValidateState(prmState);

      clsDealership varDealership = new clsDealership();
      varDealership.setAttDealershipId(prmId);
      varDealership.setAttName(prmName);
      varDealership.setAttAddress(prmAddress);
      varDealership.setAttPhone(prmPhone);
      varDealership.setAttState(prmState);
      try {
         attDealershipRepository.opRegisterDealership(varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar Concesionaria: " , e);
      }
      attDealerships.put(prmId, varDealership);
   }

   public void opUpdateDealership(int prmId, String prmName, String prmAddress, String prmPhone){
      clsValidations.opValidateId(prmId);

      if (!attDealershipRepository.opDealershipExist(prmId))
         throw new excNotFoundException("Concesionaria no encontrada");

      if (prmName != null)
         clsValidations.opValidateName(prmName, 3, 40);
      if (prmAddress != null && prmAddress.isBlank())
         throw new excValidationException("Error: Dirección vacía");
      if (prmPhone != null)
         clsValidations.opValidatePhone(prmPhone);

      clsDealership varDealership = attDealerships.get(prmId);
      if (varDealership == null)
         varDealership = attDealershipRepository.opGetDealershipById(prmId);
      if (prmName != null)
         varDealership.setAttName(prmName);
      if (prmAddress != null)
         varDealership.setAttAddress(prmAddress);
      if (prmPhone != null)
         varDealership.setAttPhone(prmPhone);
      try {
         attDealershipRepository.opUpdateDealership(varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al actualizar concesionaria: " , e);
      }
      attDealerships.put(prmId, varDealership);
   }

   public void opDisableDealership(int prmId){
      clsValidations.opValidateId(prmId);
      clsDealership varDealership = attDealerships.get(prmId);
      try {
         if (varDealership == null)
            varDealership = attDealershipRepository.opGetDealershipById(prmId);
         if (varDealership == null)
            throw new excNotFoundException("Concesionaria no encontrada");
         attDealershipRepository.opDisableDealership(prmId);
         varDealership.setAttState("inactive");
      } catch (excNotFoundException e) {
         throw e;
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al inactivar concesionaria: " , e);
      }
      attDealerships.put(prmId, varDealership);
   }

   public clsDealership opGetDealershipById(int prmId){
      clsValidations.opValidateId(prmId);
      clsDealership varDealership = attDealerships.get(prmId);
      try {
         if (varDealership == null)
            varDealership = attDealershipRepository.opGetDealershipById(prmId);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener concesionaria: " , e);
      }
      if (varDealership == null)
         throw new excNotFoundException("Concesionaria no encontrada");
      return varDealership;
   }

   public List<clsDealership> opGetAllDealership() {
      List<clsDealership> varDealerships = null;
      try {
         varDealerships = attDealershipRepository.opGetAllDealership();
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al obtener concesionarias: " , e);
      }
      return varDealerships;
   }
}