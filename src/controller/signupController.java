package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
	void processSignup(ActionEvent event) throws IOException {
		lblStatus.setVisible(true);
		Connection connection = DBConnection.getConnection();
		try {
			String userName = tfUserName.getText();
			String email = tfUserEmail.getText();
			String password = pfPassword.getText();
			String rePassword = pfRePassword.getText();
			Statement statement = connection.createStatement();
			if (password.equals("") || rePassword.equals("") || userName.equals("") || email.equals("")) {
				lblStatus.setTextFill(Color.RED);
				lblStatus.setText("All the required fields must be filled! Try Again !!");
			} else {
				if (password.equals(rePassword)) {
					int status = statement.executeUpdate("insert into user (userName,email,password) values ('"
							+ userName + "','" + email + "','" + password + "')");
					if (status > 0) {
						lblStatus.setTextFill(Color.GREEN);
						lblStatus.setText("Congratulations! SignUp SuccessFul.! ");
						mainFrame();

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
		loginFrame();
	}

	@FXML // Close the scenes
	void processClose(ActionEvent event) {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}
}
