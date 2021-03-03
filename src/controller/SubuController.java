package controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.effects.JFXDepthManager;

import database.GoalDBModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Goal;
import model.Subu;

public class SubuController implements Initializable {
	
	@FXML
	private ImageView sbImgView;

	@FXML
	private Label sbName;
	
	private JFXPopup popup,actionPopup;
	
    @FXML
    private VBox subuBox;
    
    private VBox actionBox;
    
	private GoalDBModel goalDbModel;


	@FXML
	private Label sbCurrentPrice;
	
	private Subu subu;
	
	public void setSubuDataToUI(Subu subu) {
		Image image = null;
		if(subu.getSbImageSrc() == null) {
			image = new Image(getClass().getResourceAsStream("../assets/goal.png"));
		}else {
			System.out.println(subu.getSbImageSrc());
			try {
				image = new Image(new FileInputStream(subu.getSbImageSrc()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		sbImgView.setImage(image);
		sbName.setText(subu.getSbName());
		sbCurrentPrice.setText(String.valueOf(500));
	}
	
	public List<Subu> getSubus(){
		//get goals data
		List<Goal> goals = new ArrayList<Goal>();
		goalDbModel = new GoalDBModel();
		goals = goalDbModel.selectAllGoal(2);
		
		//set goal data to subu
		List<Subu> subus = new ArrayList<Subu>();
		for (int i = 0; i < goals.size(); i++) {
			subu = new Subu();
			subu.setSbName(goals.get(i).getGoalName());
			subu.setSbImageSrc(goals.get(i).getGoalImgPath());
			subus.add(subu);
		}
		return subus;
	}
	
	private void initPopup() {
		
		
		JFXButton action = new JFXButton("Action");
		JFXButton save = new JFXButton("Save");
		JFXButton withdraw = new JFXButton("WithDraw");
		
		
		Separator separator1 = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL); // 
		//separator.setStyle("-fx-border-color: black;-fx-border-style:solid;fx-border-width:0 0 1 0");
		Font font = Font.font("Georgia", 16);

		action.setFont(font);
		save.setFont(font);
		withdraw.setFont(font);

		action.setMaxWidth(Double.MAX_VALUE);
		save.setMaxWidth(Double.MAX_VALUE);
		withdraw.setMaxWidth(Double.MAX_VALUE);

		VBox vbox = new VBox(action,separator1, save, separator2, withdraw);
		
		action.setOnMouseClicked(e -> {
			actionPopup.show(vbox, PopupVPosition.TOP, PopupHPosition.LEFT, action.getLayoutX()+80, action.getLayoutY());
		});
		
		vbox.setAlignment(Pos.CENTER);

		JFXDepthManager.setDepth(vbox, 1);

		vbox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-background-radius: 10px;"
				+ "-fx-border-radius: 10px;" + "-fx-border-color: black;");

		popup = new JFXPopup(vbox);

	}
	
	
	private void actionPopup() {

		JFXButton edit = new JFXButton("Edit");
		JFXButton delete = new JFXButton("Delete");
		Separator separator = new Separator(Orientation.HORIZONTAL);
		Font font = Font.font("Georgia", 16);

		edit.setFont(font);
		delete.setFont(font);

		edit.setMaxWidth(Double.MAX_VALUE);
		delete.setMaxWidth(Double.MAX_VALUE);

		actionBox = new VBox(edit, separator, delete);
		actionBox.setAlignment(Pos.CENTER);

		JFXDepthManager.setDepth(actionBox, 1);

		actionBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-background-radius: 10px;"
				+ "-fx-border-radius: 10px;" + "-fx-border-color: black;");

		actionPopup = new JFXPopup(actionBox);

	}
		
	
	@FXML
	void showPopup(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY) {
			popup.show(subuBox, PopupVPosition.TOP, PopupHPosition.LEFT, event.getX(), event.getY());
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
       initPopup();	
       actionPopup();
	}
	
	
	/*
	 * private List<Subu> setSubus() { List<Subu> subus = new ArrayList<Subu>();
	 * Subu travel = new Subu(); travel.setSbName("Travel");
	 * travel.setSbImageSrc("/assets/img/travel.png"); travel.setCurrentPrice(5000);
	 * 
	 * Subu food = new Subu(); food.setSbName("Food");
	 * food.setSbImageSrc("/assets/img/food.png"); food.setCurrentPrice(5000);
	 * 
	 * Subu movie = new Subu(); movie.setSbName("Movie");
	 * movie.setSbImageSrc("/assets/img/movie.png"); movie.setCurrentPrice(5000);
	 * 
	 * Subu bus = new Subu(); bus.setSbName("Bus");
	 * bus.setSbImageSrc("../assets/img/bus.png"); bus.setCurrentPrice(5000);
	 * 
	 * Subu taxi = new Subu(); taxi.setSbName("Taxi");
	 * taxi.setSbImageSrc("../assets/img/taxi.png"); taxi.setCurrentPrice(5000);
	 * 
	 * Subu bus1 = new Subu(); bus1.setSbName("Bus");
	 * bus1.setSbImageSrc("../assets/img/bus.png"); bus1.setCurrentPrice(5000);
	 * 
	 * Subu taxi1 = new Subu(); taxi1.setSbName("Taxi");
	 * taxi1.setSbImageSrc("../assets/img/taxi.png"); taxi1.setCurrentPrice(5000);
	 * 
	 * subus.add(taxi); subus.add(movie); subus.add(travel); subus.add(food);
	 * subus.add(bus); subus.add(bus1); subus.add(taxi1);
	 * 
	 * return subus; }
	 */

}
