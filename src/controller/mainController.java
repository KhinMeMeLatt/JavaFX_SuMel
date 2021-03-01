package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainController extends FramesController implements Initializable {

	@FXML // Scenes Changes to Login Scene
	void processLogin(ActionEvent event) throws IOException {
		openFrame("LoginUI");
	}

	@FXML // Scenes Changes to SignUp Scene
	void processSignUp(ActionEvent event) throws IOException {
		openFrame("SignupUI");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
