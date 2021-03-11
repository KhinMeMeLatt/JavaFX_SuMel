package controller.subuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.subuModel.Subu;

public class SubuController {

	@FXML
	private ImageView sbImgView;

	@FXML
	private Label sbName;

	@FXML
	private VBox subuBox;

	@FXML
	private Label sbCurrentPrice;

	public void setSubuDataToUI(Subu subu) {
		Image image = null;
		if (subu.getSbImageSrc() == null) {
			image = new Image(getClass().getResourceAsStream("../../assets/goal.png"));
		} else {
			try {
				image = new Image(new FileInputStream("src/assets/goals/" + subu.getSbImageSrc()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		sbImgView.setImage(image);
		sbName.setText(subu.getSbName());
		sbCurrentPrice.setText(String.valueOf(subu.getCurrentPrice()));
	}

}
