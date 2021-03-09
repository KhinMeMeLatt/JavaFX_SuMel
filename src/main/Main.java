package main;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/accountView/EditProfileUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../view/main.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../assets/pig.PNG")));
			primaryStage.setTitle("SUMEL");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
