package controller;

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
import model.Subu;

public class MainController implements Initializable {

	@FXML
	private GridPane mySubus;


	@FXML
	private HBox subuScrollPane;
	

	@FXML
	private HBox history;
	
	private List<Subu> subus;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		JFXDepthManager.setDepth(history, 1);
		JFXDepthManager.setDepth(subuScrollPane, 1);
		subus = setSubus();
		int row = 1;
		int column = 0;
		for (Subu subu : subus) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../view/Subu.fxml"));
				VBox mySubu = loader.load();
				SubuController subuController = loader.getController();
				subuController.setData(subu);
			 
                JFXDepthManager.setDepth(mySubu, 1);
				mySubus.add(mySubu, column++, row);
				GridPane.setMargin(mySubu, new Insets(10, 10, 5, 10));
			
				
		  
		  } catch (IOException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		  
		  }

		
	}
	

	private List<Subu> setSubus() {
		List<Subu> subus = new ArrayList<Subu>();
		Subu travel = new Subu();
		travel.setSbName("Travel");
		travel.setSbImageSrc("/assets/img/travel.png");
		travel.setCurrentPrice(5000);

		Subu food = new Subu();
		food.setSbName("Food");
		food.setSbImageSrc("/assets/img/food.png");
		food.setCurrentPrice(5000);

		Subu movie = new Subu();
		movie.setSbName("Movie");
		movie.setSbImageSrc("/assets/img/movie.png");
		movie.setCurrentPrice(5000);

		Subu bus = new Subu();
		bus.setSbName("Bus");
		bus.setSbImageSrc("../assets/img/bus.png");
		bus.setCurrentPrice(5000);

		Subu taxi = new Subu();
		taxi.setSbName("Taxi");
		taxi.setSbImageSrc("../assets/img/taxi.png");
		taxi.setCurrentPrice(5000);

		Subu bus1 = new Subu();
		bus1.setSbName("Bus");
		bus1.setSbImageSrc("../assets/img/bus.png");
		bus1.setCurrentPrice(5000);

		Subu taxi1 = new Subu();
		taxi1.setSbName("Taxi");
		taxi1.setSbImageSrc("../assets/img/taxi.png");
		taxi1.setCurrentPrice(5000);
		
		subus.add(taxi);
		subus.add(movie);
		subus.add(travel);
		subus.add(food);
		subus.add(bus);
		subus.add(bus1);
		subus.add(taxi1);

		return subus;
	}

}
