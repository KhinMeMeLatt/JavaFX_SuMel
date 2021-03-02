package controller.subuController;


import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.effects.JFXDepthManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import model.subuModel.Subu;

public class SubuController implements Initializable {
	
	@FXML
	private ImageView sbImgView;

	@FXML
	private Label sbName;
	
	private JFXPopup popup;
	
    @FXML
    private VBox subuBox;

	@FXML
	private Label sbCurrentPrice;
	
	public void setData(Subu subu) {
		Image image = new Image(getClass().getResourceAsStream(subu.getSbImageSrc()));
		sbImgView.setImage(image);
		sbName.setText(subu.getSbName());
		sbCurrentPrice.setText(String.valueOf(subu.getCurrentPrice()));
	}
	
	private void initPopup() {
		JFXButton save = new JFXButton("Save");
		JFXButton withdraw = new JFXButton("WithDraw");
	    Separator separator = new Separator(Orientation.HORIZONTAL);
	   // separator.setStyle("-fx-border-color: black;-fx-border-style:solid;fx-border-width:0 0 1 0");
		Font font = Font.font("Georgia",16);

		save.setFont(font);
		withdraw.setFont(font);
		
		save.setPadding(new Insets(10));
		withdraw.setPadding(new Insets(10));
		
		save.setMaxWidth(Double.MAX_VALUE);
		withdraw.setMaxWidth(Double.MAX_VALUE);
		
		VBox vbox = new VBox(save,separator,withdraw);
		vbox.setAlignment(Pos.CENTER);
		
		JFXDepthManager.setDepth(vbox, 1);
		
		vbox.setStyle(
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1;" +
                "-fx-background-radius: 10px;"+
                "-fx-border-radius: 10px;" + 
                "-fx-border-color: black;");
		
		
    	popup = new JFXPopup(vbox);
		
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
	}
	

}
