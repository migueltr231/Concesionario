package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.EmployeeRepository;

@Service
public class EmployeeService {
   private final EmployeeRepository attEmployeeRepository;

   public EmployeeService(EmployeeRepository prmEmployeeRepository){
      this.attEmployeeRepository = prmEmployeeRepository;
   }
}
