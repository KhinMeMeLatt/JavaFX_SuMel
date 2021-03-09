package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FramesController {
	

	public final void openFrame(String path, String frameName) throws IOException {
		Parent mainParent;
		Stage stage = new Stage();
		mainParent = FXMLLoader.load(getClass().getResource("../../view/"+path+"/"+frameName+".fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
		stage = new Stage();
		stage.hide();
		stage.setScene(scene);
		stage.show();
		stage.setTitle(frameName);
		stage.getIcons().add(new Image("../../assets/pig.PNG"));
	}
  
	public final void openFrame(String path, String frameName,String title) throws IOException {
		Parent mainParent;
		Stage stage = new Stage();
		mainParent = FXMLLoader.load(getClass().getResource("/view/"+path+"/"+frameName+".fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("/view/main.css").toExternalForm());
		stage = new Stage();
		stage.hide();
		stage.setScene(scene);
		stage.show();
		stage.setTitle(title);
		stage.getIcons().add(new Image("../../assets/pig.PNG"));

	}
	
	public final void openCurrencyFrame(String frameName,String title) throws IOException {
		Parent mainParent;
		Stage stage = new Stage();
		mainParent = FXMLLoader.load(getClass().getResource("/view/"+frameName+".fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("/view/main.css").toExternalForm());
		stage = new Stage();
		stage.hide();
		stage.setScene(scene);
		stage.show();
		stage.setTitle(title);
		stage.getIcons().add(new Image("../../assets/pig.PNG"));
	}
	
}
