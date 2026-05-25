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
    private HashMap<Integer, clsCustomer> attCustomers = new HashMap<>();

    public CustomerService(CustomerRepository prmCustomerRepository){
        this.attCustomerRepository = prmCustomerRepository;
    }

    public void opRegisterCustomer(int prmId, String prmName, String prmEmail, String prmPhone, String prmState){
        clsValidations.opValidateId(prmId);

        if (attCustomerRepository.opCustomerExist(prmId))
            throw new excDuplicateDataException("Cliente ya registrado");

        clsValidations.opValidateName(prmName, 3, 60);
        clsValidations.opValidateEmail(prmEmail);
        clsValidations.opValidatePhone(prmPhone);
        clsValidations.opValidateState(prmState);

        clsCustomer varCustomer = new clsCustomer();
        varCustomer.setAttCustomerId(prmId);
        varCustomer.setAttName(prmName);
        varCustomer.setAttEmail(prmEmail);
        varCustomer.setAttPhone(prmPhone);
        varCustomer.setAttState(prmState);
        try {
            attCustomerRepository.opRegisterCustomer(varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al registrar cliente: ", e);
        }
        attCustomers.put(prmId, varCustomer);
    }

    public void opUpdateCustomer(int prmId, String prmName, String prmPhone, String prmEmail){
        clsValidations.opValidateId(prmId);

        if (!attCustomerRepository.opCustomerExist(prmId))
            throw new excNotFoundException("Cliente no encontrado");

        if (prmName != null)
            clsValidations.opValidateName(prmName, 3, 60);
        if (prmEmail != null)
            clsValidations.opValidateEmail(prmEmail);
        if (prmPhone != null)
            clsValidations.opValidatePhone(prmPhone);

        clsCustomer varCustomer = attCustomers.get(prmId);
        if (varCustomer == null)
            varCustomer = attCustomerRepository.opGetCustomerById(prmId);
        if (prmName != null)
            varCustomer.setAttName(prmName);
        if (prmEmail != null)
            varCustomer.setAttEmail(prmEmail);
        if (prmPhone != null)
            varCustomer.setAttPhone(prmPhone);
        try {
            attCustomerRepository.opUpdateCustomer(varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al actualizar cliente: " , e);
        }
        attCustomers.put(prmId, varCustomer);
    }

    public void opDisableCustomer(int prmId){
        clsValidations.opValidateId(prmId);
        clsCustomer varCustomer = attCustomers.get(prmId);
        try {
            if (varCustomer == null)
                varCustomer = attCustomerRepository.opGetCustomerById(prmId);
            if (varCustomer == null)
                throw new excNotFoundException("Cliente no encontrado");
            attCustomerRepository.opDisableCustomer(prmId);
            varCustomer.setAttState("inactive");
        } catch (excNotFoundException e) {
            throw e;
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al inactivar al cliente: " , e);
        }
        attCustomers.put(prmId, varCustomer);
    }

    public clsCustomer opGetCustomerById(int prmId){

        clsValidations.opValidateId(prmId);
        clsCustomer varCustomer = attCustomers.get(prmId);
        if (varCustomer == null) {
            try {
                varCustomer = attCustomerRepository.opGetCustomerById(prmId);
            } catch (excDatabaseException e) {
                throw new excDatabaseException("Error al obtener cliente: " , e);
            }
        }

        if (varCustomer == null)
            throw new excNotFoundException("Cliente no encontrado");
        return varCustomer;
    }

    public List<clsCustomer> opGetAllCustomers() {
        List<clsCustomer> varCustomers = null;
        try {
            varCustomers = attCustomerRepository.opGetAllCustomers();
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al obtener clientes: " , e);
        }
        return varCustomers;
    }
}