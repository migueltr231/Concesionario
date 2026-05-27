package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;

@Service
public class AuthService {

	private final EmployeeService employeeService;
	
	// id(tipo int) como user
	private Map<Integer, String> passwords = new HashMap<>();
	
	public AuthService(EmployeeService employeeService) {
		this.employeeService = employeeService;
		
		// test users
		passwords.put(1, "123456");
		passwords.put(2, "oracle");
		passwords.put(3, "admin");
	}
	
	public clsEmployee login(int employeeId, String password) {
		
		clsValidations.opValidateId(employeeId);
		clsValidations.opValidateAlphanumericField(password, "Contraseña", 4, 20);
		
		clsEmployee emp = employeeService.opGetEmployeeById(employeeId);
		
		if (emp == null)
			throw new RuntimeException("Usuario no existe");
		
		String savedPassword = passwords.get(employeeId);

        if (savedPassword == null)
            throw new RuntimeException("Usuario sin contraseña asignada");

        if (!savedPassword.equals(password))
            throw new RuntimeException("Contraseña incorrecta");

        return emp;
	}
}
