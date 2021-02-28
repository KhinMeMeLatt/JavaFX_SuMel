package controller;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class loginController {

	@FXML
	private JFXTextField tfUserEmail;

	@FXML
	private JFXPasswordField pfPassword;

	@FXML
	private Label lblStatus;
	@FXML
	private JFXButton btnClose;

	private final UserLogin userLogin = new UserLogin();

	@FXML // Login Into SUMEL App
	void processLogin(ActionEvent event) throws IOException, SQLException {
		Stage mainStage = new Stage();
		lblStatus.setVisible(true);
		{
			String email = tfUserEmail.getText();
			String password = pfPassword.getText();
			if (password.equals("") || email.equals("")) {
				lblStatus.setTextFill(Color.RED);
				lblStatus.setText("All the required fields must be filled! Try Again !!");
			} else {
				if (userLogin.isValidated(tfUserEmail.getText().trim(), pfPassword.getText())) {
					lblStatus.setTextFill(Color.GREEN);
					lblStatus.setText("Congratulations! Login Success! ");
					Parent mainParent = FXMLLoader.load(getClass().getResource("../view/mainUI.fxml"));
					Scene scene = new Scene(mainParent);
					mainStage.hide();
					mainStage.setScene(scene);
					mainStage.close();
					mainStage.show();
					mainStage.setTitle("mainUI");
				} else {
					lblStatus.setTextFill(Color.RED);
					lblStatus.setText("Incorrect email or password! Try Again !!");
				}

			}
		}
	}

	@FXML // Scenes Changes to SignUp Scene
	void processSignUp(ActionEvent event) throws IOException {
		Parent mainParent = FXMLLoader.load(getClass().getResource("../view/signupUI.fxml"));
		Scene scene = new Scene(mainParent);
		Stage mainStage = new Stage();
		mainStage.hide();
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.setTitle("SignUpUI");
	}

	@FXML // Close the scene
	void processClose(ActionEvent event) {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}

}
