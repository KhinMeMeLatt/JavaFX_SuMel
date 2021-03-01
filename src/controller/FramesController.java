package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FramesController {
	
	Parent mainParent;
	Stage stage = new Stage();

	public final void loginFrame() throws IOException {
		mainParent = FXMLLoader.load(getClass().getResource("../view/LoginUI.fxml"));
		Scene scene = new Scene(mainParent);
		this.stage = new Stage();
		this.stage.hide();
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle("LoginUI");
	}
	
	public final void signUpFrame() {
		
	}
	
	public final void mainFrame() throws IOException {
		mainParent = FXMLLoader.load(getClass().getResource("../view/MainUI.fxml"));
		Scene scene = new Scene(mainParent);
		this.stage.hide();
		this.stage.setScene(scene);
		this.stage.close();
		this.stage.show();
		this.stage.setTitle("mainUI");
	}
}
