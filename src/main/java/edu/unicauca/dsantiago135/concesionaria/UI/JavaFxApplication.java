package edu.unicauca.dsantiago135.concesionaria.UI;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxApplication extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {
		
		SceneManager.setStage(stage);
		SceneManager.switchScene("login.fxml");
		stage.setTitle("Concesionaria");
				
		stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
	}

}
