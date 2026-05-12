package edu.unicauca.dsantiago135.concesionaria;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.unicauca.dsantiago135.concesionaria.Controller.clsController;
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;

@SpringBootApplication
public class ConcesionariaApplication {

	public static void main(String[] args) {

		SpringApplication.run(ConcesionariaApplication.class,args);
	}

	@Bean
	CommandLineRunner opMostrarCliente(clsController prmController) {

		return args -> {

			clsCustomer varCustomer =prmController.opGetCustomerBy(6);

			if(varCustomer == null) {

				System.out.println(
						"Cliente no encontrado.");

				return;
			}

			System.out.println(
					"========== CLIENTE ==========");

			System.out.println(
					"ID: "
					+ varCustomer.getAttCustomerId());

			System.out.println(
					"Nombre: "
					+ varCustomer.getAttName());

			System.out.println(
					"Telefono: "
					+ varCustomer.getAttPhone());

			System.out.println(
					"Email: "
					+ varCustomer.getAttEmail());

			System.out.println(
					"Estado: "
					+ varCustomer.getAttState());
		};
	}
}