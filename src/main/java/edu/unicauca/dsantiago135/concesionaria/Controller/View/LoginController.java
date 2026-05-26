package edu.unicauca.dsantiago135.concesionaria.Controller.View;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class LoginController {

	@FXML
	private TextField txtUsuario;
	
	@FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    public void iniciarSesion() {

        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if(usuario.equals("admin")
                && password.equals("1234")) {

            lblMensaje.setText("Bienvenido");

        } else {

            lblMensaje.setText("Credenciales incorrectas");
        }
    }
}
