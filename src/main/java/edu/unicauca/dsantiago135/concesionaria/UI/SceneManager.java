package edu.unicauca.dsantiago135.concesionaria.UI;

import java.io.IOException;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.ConcesionariaApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
public class SceneManager {
	
	private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

	public static void switchScene(String fxml){
		
		try {
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
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Cargar vista central
	public static void loadView(
            StackPane contentArea,
            String fxml) {

        try {

            FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource(
                    "/concesionaria/fxml/" + fxml
                )
            );

            loader.setControllerFactory(
                ConcesionariaApplication.context::getBean
            );

            Parent view = loader.load();

            contentArea.getChildren().clear();

            contentArea.getChildren().add(view);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
