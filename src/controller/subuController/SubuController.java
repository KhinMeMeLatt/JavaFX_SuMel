package controller.subuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.effects.JFXDepthManager;

import controller.FramesController;
import database.GoalDBModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.subuModel.Subu;

public class SubuController implements Initializable {

	@FXML
	private ImageView sbImgView;

	@FXML
	private Label sbName;


	@FXML
	private VBox subuBox;


	@FXML
	private Label sbCurrentPrice;

	private Subu subu;

	GoalDBModel goalDbModel = new GoalDBModel();
	
	FramesController frameController = new FramesController();

	public void setSubuDataToUI(Subu subu) {
		Image image = null;
		if (subu.getSbImageSrc() == null) {
			image = new Image(getClass().getResourceAsStream("../assets/goal.png"));
		} else {
			try {
				image = new Image(new FileInputStream("src/assets/img/" + subu.getSbImageSrc()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		sbImgView.setImage(image);
		sbName.setText(subu.getSbName());
		sbCurrentPrice.setText(String.valueOf(subu.getCurrentPrice()));
	}

	
	

	

	@FXML
	void showPopup(MouseEvent event) {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
