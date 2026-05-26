package edu.unicauca.dsantiago135.concesionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import edu.unicauca.dsantiago135.concesionaria.Controller.clsController;

@SpringBootApplication
public class ConcesionariaApplication {

	public static void main(String[] args) {
		ApplicationContext varContext = SpringApplication.run(ConcesionariaApplication.class, args);
		clsController varController = varContext.getBean(clsController.class);
	}
}