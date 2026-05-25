package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Repository.CustomerRepository;

@Service
public class CustomerService {

    // region ATTRIBUTES
    private final CustomerRepository attCustomerRepository;
    private HashMap<Integer, clsCustomer> attCustomers = new HashMap<>();
    // endregion

    // region CONSTRUCTOR
    public CustomerService(CustomerRepository prmCustomerRepository) {
        this.attCustomerRepository = prmCustomerRepository;
    }
    // endregion

    // region PRIVATE HELPERS
    private clsCustomer opFetchFromDB(int prmId) {
        clsCustomer varCustomer;
        try {
            varCustomer = attCustomerRepository.opGetCustomerById(prmId);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al obtener el cliente desde BD: ", e);
        }
        if (varCustomer == null)
            throw new excNotFoundException("Cliente no encontrado");
        return varCustomer;
    }

    private void opRefreshCache(int prmId) {
        attCustomers.remove(prmId);
        clsCustomer varFresh = opFetchFromDB(prmId);
        attCustomers.put(prmId, varFresh);
    }
    // endregion

    // region PROCEDURES
    public void opRegisterCustomer(int prmId, String prmName, String prmEmail, String prmPhone, String prmState) {
        clsValidations.opValidateId(prmId);

        if (attCustomerRepository.opCustomerExist(prmId))
            throw new excDuplicateDataException("Cliente ya registrado");

        clsValidations.opValidateName(prmName, 3, 60);
        clsValidations.opValidateEmail(prmEmail);
        clsValidations.opValidatePhone(prmPhone);
        clsValidations.opValidateState(prmState);

        clsCustomer varTemp = new clsCustomer();
        varTemp.setAttCustomerId(prmId);
        varTemp.setAttName(prmName);
        varTemp.setAttEmail(prmEmail);
        varTemp.setAttPhone(prmPhone);
        varTemp.setAttState(prmState);

        try {
            attCustomerRepository.opRegisterCustomer(varTemp);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al registrar cliente: ", e);
        }

        opRefreshCache(prmId);
    }

    public void opUpdateCustomer(int prmId, String prmName, String prmPhone, String prmEmail) {
        clsValidations.opValidateId(prmId);

        if (!attCustomerRepository.opCustomerExist(prmId))
            throw new excNotFoundException("Cliente no encontrado");

        if (prmName != null)
            clsValidations.opValidateName(prmName, 3, 60);
        if (prmEmail != null)
            clsValidations.opValidateEmail(prmEmail);
        if (prmPhone != null)
            clsValidations.opValidatePhone(prmPhone);

        clsCustomer varCustomer = opFetchFromDB(prmId);

        if (prmName != null)
            varCustomer.setAttName(prmName);
        if (prmEmail != null)
            varCustomer.setAttEmail(prmEmail);
        if (prmPhone != null)
            varCustomer.setAttPhone(prmPhone);

        try {
            attCustomerRepository.opUpdateCustomer(varCustomer);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al actualizar cliente: ", e);
        }

        opRefreshCache(prmId);
    }

    public void opDisableCustomer(int prmId) {
        clsValidations.opValidateId(prmId);

        if(!attCustomerRepository.opCustomerExist(prmId)) throw new excNotFoundException("Cliente no encontrado");

        try {
            attCustomerRepository.opDisableCustomer(prmId);
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al inactivar al cliente: ", e);
        }
        opRefreshCache(prmId);
    }
    // endregion

    // region FUNCTIONS
    public clsCustomer opGetCustomerById(int prmId) {
        clsValidations.opValidateId(prmId);

        clsCustomer varCustomer = attCustomers.get(prmId);
        if (varCustomer != null)
            return varCustomer;

        varCustomer = opFetchFromDB(prmId);
        attCustomers.put(prmId, varCustomer);
        return varCustomer;
    }

    public List<clsCustomer> opGetAllCustomers() {
        List<clsCustomer> varCustomers;
        try {
            varCustomers = attCustomerRepository.opGetAllCustomers();
        } catch (excDatabaseException e) {
            throw new excDatabaseException("Error al obtener clientes: ", e);
        }
        return varCustomers;
    }
    // endregion
}