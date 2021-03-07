package controller.accountController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.FramesController;
import database.AccountDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.accountModel.Encryption;
import model.accountModel.User;

public class SignupController extends FramesController{

	@FXML
	private JFXTextField tfUserEmail;

	@FXML
	private JFXPasswordField pfPassword;

	@FXML
	private Label lblStatus;

	@FXML
	private JFXTextField tfUserName;

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXPasswordField pfRePassword;

	@FXML // User Registration
	void processSignup(ActionEvent event) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
		lblStatus.setVisible(true);

		try {
			String userName = tfUserName.getText();
			String email = tfUserEmail.getText();
			String password = pfPassword.getText();
			String rePassword = pfRePassword.getText();

			if (password.equals("") || rePassword.equals("") || userName.equals("") || email.equals("")) {
				lblStatus.setTextFill(Color.RED);
				lblStatus.setText("All the required fields must be filled! Try Again !!");
			} else {
				
				if (password.equals(rePassword)) {
					Encryption newEncryption=new Encryption();
					 byte[] salt = newEncryption.getSalt();
					 String securePassword = newEncryption.getSecurePassword(password, salt);
					AccountDBModel accountDb = new AccountDBModel();
					User user = new User(userName, email, securePassword);
					
					int status = accountDb.signUp(user);
					
					User.userId = accountDb.getLatestUserId();
					
					if (status > 0) {
						lblStatus.setTextFill(Color.GREEN);
						lblStatus.setText("Congratulations! SignUp SuccessFul.! ");
						openFrame("subuView","TargetGoalUI");

					}
				} else {
					lblStatus.setTextFill(Color.RED);
					lblStatus.setText("Password does't match. Try Again !!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML //// Scenes Changes to Login Scene
	void processLogin(ActionEvent event) throws IOException {
		openFrame("accountView","LoginUI");
	}

	@FXML // Close the scenes
	void processClose(ActionEvent event) {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}
}
