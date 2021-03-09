package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FramesController {
	
	Parent mainParent;
	Stage stage = new Stage();

	public final void openFrame(String path, String frameName) throws IOException {
		mainParent = FXMLLoader.load(getClass().getResource("../../view/"+path+"/"+frameName+".fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
		this.stage = new Stage();
		this.stage.hide();
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setTitle(frameName);
		this.stage.getIcons().add(new Image(getClass().getResourceAsStream("../../assets/pig.PNG")));
	}
	
}
