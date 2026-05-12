package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository attCustomerRepository;

    public CustomerService(CustomerRepository prmCustomerRepository) {
        this.attCustomerRepository = prmCustomerRepository;
    }

    public clsCustomer opGetCustomerBy(int prmId) {
        return attCustomerRepository.opGetCustomerBy(prmId);
    }
}
