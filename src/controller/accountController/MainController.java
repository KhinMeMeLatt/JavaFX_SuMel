package controller.accountController;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import controller.FramesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController extends FramesController {
	
	@FXML
	private JFXButton loginBtn;

	@FXML
	private JFXButton signUpBtn;
	
	@FXML
    void aboutFrame(ActionEvent event) throws IOException {
       openFrame("accountView", "AboutUI", "About");
    }

    @FXML
    void currencyConverterFrame(ActionEvent event) throws IOException {
       openCurrencyFrame("CurrencyConverterUI", "Currency Converter");
    }

	@FXML // Scenes Changes to Login Scene
	void processLogin(ActionEvent event) throws IOException {
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		stage.close();
		openFrame("accountView","LoginUI","Login");
	}

	@FXML // Scenes Changes to SignUp Scene
	void processSignUp(ActionEvent event) throws IOException {
		Stage stage = (Stage) signUpBtn.getScene().getWindow();
		stage.close();
		openFrame("accountView","SignupUI","Sign Up");
	}

}
