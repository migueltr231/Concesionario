package edu.unicauca.dsantiago135.concesionaria.Service;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository attCustomerRepository;
    private HashMap<Integer, clsCustomer> attCustomers;

    public CustomerService(CustomerRepository prmCustomerRepository){
        this.attCustomerRepository = prmCustomerRepository;
    }

    public void opRegisterCustomer(int prmId, String prmName, String prmEmail, String prmPhone, String prmState)
    throws excDatabaseException, excDuplicateDataException, excValidationException{
        
        if(prmId <= 0) throw new excValidationException("Error: Id negativo");
        if(String.valueOf(prmId).length() != 10) throw new excValidationException("Error: Id vacío o incompleto");

        if(prmName == null || prmName.isBlank()) throw new excValidationException("Error: Nombre vacío o nulo");
        if(!prmName.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) throw new excValidationException("Error: caracteres no permitidos en el nombre");
        if(prmName.length() < 3 || prmName.length() > 60) throw new excValidationException("Error: Nombre fuera del rango");

        if(prmEmail == null || prmEmail.isBlank()) throw new excValidationException("Error: Email vacío o nulo");
        if(!prmEmail.matches("^[A-Za-z0-9._%+-]+@(gmail|hotmail)\\.com$")) throw new excValidationException("Error: Email no cumple especificaiones de nombrado");

        if(prmPhone == null || !prmPhone.matches("^\\d{10}$")) throw new excValidationException("Error: Teléfono con caracteres no numéricos o nulo");

        if(!prmState.equals("active") && !prmState.equals("inactive")) throw new excValidationException("Error: Estado no permitido");

        clsCustomer varCustomer= new clsCustomer();
        varCustomer.setAttCustomerId(prmId);
        varCustomer.setAttName(prmName);
        varCustomer.setAttEmail(prmEmail);
        varCustomer.setAttPhone(prmPhone);
        varCustomer.setAttState(prmState);
        try {
            attCustomerRepository.opRegisterCustomer(varCustomer);
            attCustomers.put(prmId, varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error en la base de datos al registrar cliente: "+ e.getMessage());
        }
    }

    public void opUpdateCustomer(int prmId, String prmName, String prmPhone, String prmEmail)throws excDatabaseException, excNotFoundException, excValidationException{
        if(!attCustomerRepository.opCustomerExist(prmId)) throw new excNotFoundException("Cliente no encontrado");
        
        if(prmName == null || prmName.isBlank()) throw new excValidationException("Error: Nombre vacío o nulo");
        if(!prmName.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) throw new excValidationException("Error: caracteres no permitidos en el nombre");
        if(prmName.length() < 3 || prmName.length() > 60) throw new excValidationException("Error: Nombre fuera del rango");

        if(prmEmail == null || prmEmail.isBlank()) throw new excValidationException("Error: Email vacío o nulo");
        if(!prmEmail.matches("^[A-Za-z0-9._%+-]+@(gmail|hotmail)\\.com$")) throw new excValidationException("Error: Email no cumple especificaiones de nombrado");

        if(prmPhone == null || !prmPhone.matches("^\\d{10}$")) throw new excValidationException("Error: Teléfono con caracteres no numéricos o nulo");

        try {
            clsCustomer varCustomer = attCustomers.get(prmId);
            if(varCustomer == null) varCustomer = attCustomerRepository.opGetCustomerById(prmId);
            if(varCustomer == null) throw new excNotFoundException("Cliente no encontrado");
            varCustomer.setAttName(prmName);
            varCustomer.setAttEmail(prmEmail);
            varCustomer.setAttPhone(prmPhone);
            attCustomerRepository.opUpdateCustomer(varCustomer);
            attCustomers.put(prmId, varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error en la base de datos al actualizar cliente: "+ e.getMessage());
        }
    }
    
    public void opDisableCustomer(int prmId)throws excDatabaseException, excNotFoundException{
        try {
            clsCustomer varCustomer = attCustomers.get(prmId);
            if(varCustomer == null) varCustomer = attCustomerRepository.opGetCustomerById(prmId);
            if(varCustomer == null) throw new excNotFoundException("Cliente no encontrado");
            attCustomerRepository.opDisableCustomer(prmId);
            varCustomer.setAttState("inactive");
            attCustomers.put(prmId, varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al inactivar al cliente: "+ e.getMessage());
        }
    }
    
    public clsCustomer opGetCustomerById(int prmId)throws excDatabaseException, excNotFoundException{
        clsCustomer varCustomer = attCustomers.get(prmId);
        if (varCustomer == null){
            try {
                varCustomer = attCustomerRepository.opGetCustomerById(prmId);
            } catch (excDatabaseException e ) {
                throw new excDatabaseException("Error en la base de datos: "+e.getMessage());
            }
        }

        if (varCustomer == null) throw new excNotFoundException("Cliente no encontrado");
        return varCustomer;
    }
    
    public List<clsCustomer> opGetAllCustomers()throws excDatabaseException{
        List<clsCustomer> varCustomers = null; 
        try {
            varCustomers = attCustomerRepository.opGetAllCustomers();
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error en la base de datos: "+e.getMessage());
        }
        return varCustomers;
    }
}
