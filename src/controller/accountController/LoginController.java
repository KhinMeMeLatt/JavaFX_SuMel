package controller.accountController;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.FramesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.accountModel.AccountDBModel;
import model.accountModel.User;

public class LoginController extends FramesController{

	@FXML
	private JFXTextField tfUserEmail;

	@FXML
	private JFXPasswordField pfPassword;

	@FXML
	private Label lblStatus;
	@FXML
	private JFXButton btnClose;

	@FXML
	void processLogin(ActionEvent event) throws IOException, SQLException {
		lblStatus.setVisible(true);
		{
			String email = tfUserEmail.getText();
			String password = pfPassword.getText();
			if (password.equals("") || email.equals("")) {
				lblStatus.setTextFill(Color.RED);
				lblStatus.setText("All the required fields must be filled! Try Again !!");
			} else {
				AccountDBModel accountDb = new AccountDBModel();
				
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				
				if (accountDb.isValidated(user)) {
					lblStatus.setTextFill(Color.GREEN);
					lblStatus.setText("Congratulations! Login Success! ");
					openFrame("accountView","MainUI");
				} else {
					lblStatus.setTextFill(Color.RED);
					lblStatus.setText("Incorrect email or password! Try Again !!");
				}

			}
		}
	}

	@FXML // Scenes Changes to SignUp Scene
	void processSignUp(ActionEvent event) throws IOException {
		openFrame("accountView","Signup");
	}

	@FXML // Close the scene
	void processClose(ActionEvent event) {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}

}
