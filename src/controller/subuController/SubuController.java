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

	private JFXPopup popup, actionPopup;

	@FXML
	private VBox subuBox;

	private VBox actionBox;

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

	private void initPopup() {

		JFXButton action = new JFXButton("Action");
		JFXButton save = new JFXButton("Save");
		JFXButton withdraw = new JFXButton("WithDraw");

		Separator separator1 = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL); //
		// separator.setStyle("-fx-border-color:
		// black;-fx-border-style:solid;fx-border-width:0 0 1 0");
		Font font = Font.font("Georgia", 16);

		action.setFont(font);
		save.setFont(font);
		withdraw.setFont(font);

		action.setMaxWidth(Double.MAX_VALUE);
		save.setMaxWidth(Double.MAX_VALUE);
		withdraw.setMaxWidth(Double.MAX_VALUE);
		
		save.setOnMouseClicked(e -> {
			loadSaveUI();
		});
		
		withdraw.setOnMouseClicked(e -> {
			loadWithdrawUI();
		});

		VBox vbox = new VBox(action, separator1, save, separator2, withdraw);

		action.setOnMouseClicked(e -> {
			actionPopup.show(vbox, PopupVPosition.TOP, PopupHPosition.LEFT, action.getLayoutX() + 80,
					action.getLayoutY());
		});
		

		vbox.setAlignment(Pos.CENTER);

		JFXDepthManager.setDepth(vbox, 1);

		vbox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-background-radius: 10px;"
				+ "-fx-border-radius: 10px;" + "-fx-border-color: black;");

		popup = new JFXPopup(vbox);

	}
	
	private void loadSaveUI(){
		Parent mainParent;
		Stage stage = new Stage();
		try {
			mainParent = FXMLLoader.load(getClass().getResource("../../view/subuView/SaveUI.fxml"));
			Scene scene = new Scene(mainParent);
			scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			stage.setTitle("Save");
			stage.getIcons().add(new Image("/assets/icon/sumel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void loadWithdrawUI() {
		Parent mainParent;
		Stage stage = new Stage();
		try {
			mainParent = FXMLLoader.load(getClass().getResource("../../view/subuView/WithdrawUI.fxml"));
			Scene scene = new Scene(mainParent);
			scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			stage.setTitle("Save");
			stage.getIcons().add(new Image("/assets/icon/sumel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		edit.setOnMouseClicked(e -> {
			HomeController home1 = new HomeController();
			home1.handleGoalEdit(sbName.getText());
		});

		delete.setOnMouseClicked(e -> {
			HomeController home2 = new HomeController();
			home2.handleGoalDelete(sbName.getText(), sbName.getParent());

		});

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

}
