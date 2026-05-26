package edu.unicauca.dsantiago135.concesionaria.UI;

import java.io.IOException;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.ConcesionariaApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class SceneManager {
	
	private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

	public static void switchScene(String fxml) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(
			SceneManager.class.getResource(
				"/concesionaria/fxml/" + fxml
			)
		);
		
		loader.setControllerFactory(
                ConcesionariaApplication.context::getBean
        );
		
		Scene scene = new Scene(loader.load());
		
		mainStage.setScene(scene);
		mainStage.show();
	}
}
