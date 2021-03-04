package controller.subuController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.effects.JFXDepthManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.subuModel.Subu;

public class HomeController implements Initializable {

	@FXML
	private GridPane mySubus;

	@FXML
	private HBox subuScrollPane;

	@FXML
	private HBox history;
	
	private List<Subu> subus;
	
	private SubuController subuController;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		JFXDepthManager.setDepth(history, 1);
		JFXDepthManager.setDepth(subuScrollPane, 1);
		subuController = new SubuController();
		subus = subuController.getSubus();
		int row = 1;
		int column = 0;
		for (Subu subu : subus) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../view/Subu.fxml"));
				VBox mySubu = loader.load();
				SubuController subuController = loader.getController();
				subuController.setSubuDataToUI(subu);		 
                JFXDepthManager.setDepth(mySubu, 1);
				mySubus.add(mySubu, column++, row);
				GridPane.setMargin(mySubu, new Insets(10, 10, 5, 10));
		  } catch (IOException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); }
		  }		 
	}
}