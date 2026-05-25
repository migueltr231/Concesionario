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
   private HashMap<Integer, clsDealership> attDealerships;

   public DealershipService(DealershipRepository prmDealershipRepository){
      this.attDealershipRepository = prmDealershipRepository;
   }
   
   public void opRegisterDealership (int prmId, String prmName, String prmState, String prmAddress, String prmPhone)
   throws excDatabaseException, excDuplicateDataException, excValidationException{
      if(prmId <= 0) throw new excValidationException("Error: Id negativo");
      if(String.valueOf(prmId).length() != 10) throw new excValidationException("Error: Id vacío o incompleto");

      if(attDealerships.containsKey(prmId)) throw new excDuplicateDataException("Error Concesionaria ya registrada.");

      if(prmName == null || prmName.isBlank()) throw new excValidationException("Error: Nombre vacío o nulo");
      if(!prmName.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) throw new excValidationException("Error: caracteres no permitidos en el nombre");
      if(prmName.length() < 3 || prmName.length() > 40) throw new excValidationException("Error: Nombre fuera del rango");

      if(prmAddress == null || prmAddress.isBlank()) throw new excValidationException("Error: Email vacío o nulo");

      if(prmPhone == null || !prmPhone.matches("^\\d{10}$")) throw new excValidationException("Error: Teléfono con caracteres no numéricos o nulo");

      if(!prmState.equals("active") && !prmState.equals("inactive")) throw new excValidationException("Error: Estado no permitido");
      
      clsDealership varDealership = new clsDealership();
         varDealership.setAttDealershipId(prmId);
         varDealership.setAttName(prmName);
         varDealership.setAttAddress(prmAddress);
         varDealership.setAttPhone(prmPhone);
         varDealership.setAttState(prmState);

      try {
         attDealershipRepository.opRegisterDealership(varDealership);
         attDealerships.put(prmId, varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error al registrar Concesionaria: " + e.getMessage());
      }
   }
   
   public void opUpdateDealership (int prmId, String prmName, String prmAddress, String prmPhone)
   throws excDatabaseException, excNotFoundException, excValidationException{
      if(prmId <= 0) throw new excValidationException("Error: Id negativo");
      if(String.valueOf(prmId).length() != 10) throw new excValidationException("Error: Id vacío o incompleto");

      if(!attDealershipRepository.opDealershipExist(prmId)) throw new excNotFoundException("Concesionaria no encontrada");

      if (prmName != null){
         if(prmName.isBlank()) throw new excValidationException("Error: Nombre vacío");
         if(!prmName.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) throw new excValidationException("Error: caracteres no permitidos en el nombre");
         if(prmName.length() < 3 || prmName.length() > 40) throw new excValidationException("Error: Nombre fuera del rango");
      }
      if(prmAddress != null && prmAddress.isBlank()) throw new excValidationException("Error: Email vacío");

      if(prmPhone != null && !prmPhone.matches("^\\d{10}$")) throw new excValidationException("Error: Teléfono con caracteres no numéricos");

      clsDealership varDealership = new clsDealership();
      try {
         varDealership = attDealerships.get(prmId);
         if(varDealership == null ) varDealership = attDealershipRepository.opGetDealershipById(prmId);
         if(prmName != null) varDealership.setAttName(prmName);
         if(prmAddress !=null) varDealership.setAttAddress(prmAddress);
         if(prmPhone!= null) varDealership.setAttPhone(prmPhone);
         attDealershipRepository.opUpdateDealership(varDealership);
         attDealerships.put(prmId, varDealership);
      } catch (excDatabaseException e) {
         throw new excDatabaseException("Error en la base de datos: "+ e.getMessage());
      }
   }
   
   public void opDisableDealership (int prmId)
   throws excDatabaseException, excNotFoundException, excValidationException{
      if(prmId <= 0) throw new excValidationException("Error: Id negativo");
      if(String.valueOf(prmId).length() != 10) throw new excValidationException("Error: Id vacío o incompleto");
      try {
         clsDealership varDealership = attDealerships.get(prmId);
         if(varDealership == null) varDealership = attDealershipRepository.opGetDealershipById(prmId);
         if(varDealership == null) throw new excNotFoundException("Concesionaria no encontrada");
         attDealershipRepository.opDisableDealership(prmId);
         varDealership.setAttState("inactive");
         attDealerships.put(prmId, varDealership);
      } catch (Exception e) {
         throw new excDatabaseException("Error en la base de datos: "+ e.getMessage());
      }
   }
   
   public clsDealership opGetDealershipById(int prmId)
   throws excDatabaseException, excNotFoundException, excValidationException{
      if(prmId <= 0) throw new excValidationException("Error: Id negativo");
      if(String.valueOf(prmId).length() != 10) throw new excValidationException("Error: Id vacío o incompleto");
      clsDealership varDealership = attDealerships.get(prmId);
      try{
         if(varDealership == null) varDealership = attDealershipRepository.opGetDealershipById(prmId);
      }catch (excDatabaseException e ){
         throw new excDatabaseException("Error en la base de datos: "+ e.getMessage());
      }
      
      if(varDealership == null) throw new excNotFoundException("Concesionaria no encontrada");
      return varDealership;
   }
   
   public List<clsDealership> opGetAllDealership(){
      List<clsDealership> varDealerships = null;
      try {
         varDealerships = attDealershipRepository.opGetAllDealership();
     } catch (excDatabaseException e) {
         throw new excDatabaseException("Error en la base de datos: "+e.getMessage());
     }
      return varDealerships;
   }
}
