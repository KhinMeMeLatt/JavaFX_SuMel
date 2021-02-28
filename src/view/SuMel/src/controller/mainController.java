package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainController implements Initializable {

	@FXML // Scenes Changes to Login Scene
	void processLogin(ActionEvent event) throws IOException {
		Parent mainParent = FXMLLoader.load(getClass().getResource("../view/loginUI.fxml"));
		Scene scene = new Scene(mainParent);
		Stage mainStage = new Stage();
		mainStage.hide();
		mainStage.setScene(scene);
		mainStage.close();
		mainStage.show();
		mainStage.setTitle("LoginUI");

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
