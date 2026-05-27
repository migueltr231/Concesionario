package edu.unicauca.dsantiago135.concesionaria.Controller.View;

import org.springframework.stereotype.Component;

import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Service.AuthService;
import edu.unicauca.dsantiago135.concesionaria.UI.SceneManager;
import edu.unicauca.dsantiago135.concesionaria.UI.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class LoginController {

	private final AuthService authService;

	public LoginController(AuthService authService) {
		this.authService = authService;
	}

	@FXML
	private TextField txtUsuario;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblMensaje;

	@FXML
	public void iniciarSesion() {

		int id = Integer.parseInt(txtUsuario.getText());
		String pass = txtPassword.getText();

		clsEmployee user = authService.login(id, pass);

		SessionManager.setUser(user);
		SceneManager.switchScene("dashboard.fxml");
	}
}
