package edu.unicauca.dsantiago135.concesionaria.Controller.View;

import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.UI.SceneManager;
import edu.unicauca.dsantiago135.concesionaria.UI.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController {
	@FXML
	private Label lblUser;
	@FXML 
	private VBox menuBox;
    @FXML 
    private StackPane contentArea;

    @FXML 
    private Button btnVentas;
    @FXML 
    private Button btnInventario;
    @FXML 
    private Button btnReportes;
    
    @FXML
    public void initialize() {

        clsEmployee user = SessionManager.getUser();

        lblUser.setText("Bienvenido: " + user.getAttName());

        applyPermissions(user);
    }
    
    private void applyPermissions(clsEmployee user) {

        if(user.getAttRole().equals("seller")) {

            btnReportes.setVisible(false);
            btnReportes.setManaged(false);
        }

        if(user.getAttRole().equals("manager")) {

            btnReportes.setVisible(true);
            btnInventario.setVisible(true);
        }
    }
    
    // Botones del menu vertical
    @FXML
    public void showVentas() {
        SceneManager.loadView(contentArea,"ventas.fxml");
    }
    
    @FXML
    public void showInventario() {
    	SceneManager.loadView(contentArea,"inventario.fxml");
    }
    
    @FXML
    public void showReportes() {
    	SceneManager.loadView(contentArea,"reportes.fxml");
    }
    
    @FXML
    public void logout() {

        SessionManager.clear();

        SceneManager.switchScene("login.fxml");
    }
}
