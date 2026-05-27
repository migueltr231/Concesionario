package edu.unicauca.dsantiago135.concesionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import edu.unicauca.dsantiago135.concesionaria.UI.JavaFxApplication;
import javafx.application.Application;

@SpringBootApplication
public class ConcesionariaApplication {
	
	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
				context = SpringApplication.run(
				ConcesionariaApplication.class,
				args
		);
		
		Application.launch(JavaFxApplication.class,  args);
	}
}