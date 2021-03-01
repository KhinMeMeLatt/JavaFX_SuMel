package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FramesController {
	
	Parent mainParent;
	Stage stage = new Stage();

	public final void openFrame(String frameName) throws IOException {
		mainParent = FXMLLoader.load(getClass().getResource("../view/"+frameName+".fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
		this.stage = new Stage();
		this.stage.hide();
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle(frameName);
	}
	
}
