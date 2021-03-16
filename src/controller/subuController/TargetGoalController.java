package controller.subuController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import alert.AlertMaker;
import database.GoalDBModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.accountModel.User;
import model.subuModel.Goal;

public class TargetGoalController implements Initializable {

	@FXML
	private JFXTextField txtGoalName;

	@FXML
	private Label nameLengthLbl;

	@FXML
	private JFXTextField txtObjAmount;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private JFXTextField txtSaveAmount;

	@FXML
	private JFXRadioButton rbDaily;

	@FXML
	private ToggleGroup tgSaveType;

	@FXML
	private JFXRadioButton rbWeekly;

	@FXML
	private JFXRadioButton rbMonthly;

	@FXML
	private JFXButton btnCreateGoal;

	@FXML
	private Label nameExistLabel;

	@FXML
	private Label nameAmount;

	@FXML
	private ImageView imViewGoal;


	private LocalDate startDate = LocalDate.now();

	private LocalDate endDate = LocalDate.now().plus(1, ChronoUnit.DAYS);

	private String saveType = "Daily";

	private int objAmount = 0;

	private double period = 1;

	private boolean changeDate = true;

	private boolean isStartProgram = true;

	private String imgSrc;

	private String imageName;

	private File imgFile;

	private int goalId;

	private Boolean isInEditMode = false;

	private GoalDBModel goalDbModel;

	HomeController home = new HomeController();

	@FXML
	void processClose(ActionEvent event) {
		Stage stage = (Stage) txtGoalName.getScene().getWindow();
		stage.close();
	}
        @FXML
	void nameExist(KeyEvent event) {
		if (goalDbModel.isSubuNameExists(txtGoalName.getText())) {
			nameExistLabel.setTextFill(Color.RED);
			nameExistLabel.setText("***Subu name is already exist!!!Please Enter Different name.");
		} else {
			nameExistLabel.setTextFill(Color.WHITE);
			nameExistLabel.setText("");
		}


		if(txtGoalName.getText().length() > 10) {
			nameLengthLbl.setTextFill(Color.RED);
			nameLengthLbl.setText("***Subu name length must not be greater than 10.");
		}else {
			nameLengthLbl.setTextFill(Color.WHITE);
			nameLengthLbl.setText("");
		}
	}

	@FXML
	void createGoal(ActionEvent event) throws IOException {
		if (isInEditMode) {
			handleUpdateTargetGoal();
			return;
		}

		double amountToSave = Double.valueOf(txtSaveAmount.getText());

		String goalName = txtGoalName.getText();

		if (imgFile == null) {
			imgFile = new File("src/assets/goal.png");
			imageName = "goal.png";
		}

		BufferedImage bImage = ImageIO.read(imgFile);

		String mimeType = URLConnection.guessContentTypeFromName(imageName);

		ImageIO.write(bImage, mimeType.substring(mimeType.indexOf("/") + 1),
				new File("src/assets/goals/" + imageName));

		Goal newGoal = new Goal(goalName, this.imageName, this.objAmount, this.startDate.toString(),
				this.endDate.toString(), this.saveType, amountToSave, false, User.userId);

		if (!(goalName.length() > 10)) {

			if (!goalDbModel.isSubuNameExists(goalName)) {
				goalDbModel.insertGoal(newGoal);
			} else {
				AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Please Enter Different Subu name!");
			}

		} else {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Subu Name length must not be larger than 10.");
		}
		clearData();


	}

	private void clearData() {
		txtGoalName.setText("");
		txtObjAmount.setText("");
		txtSaveAmount.setText("");
		rbDaily.setSelected(true);
		rbWeekly.setDisable(true);
		rbMonthly.setDisable(true);
		dpStartDate.setValue(LocalDate.now()); // Set current date in date picker for start date
		dpEndDate.setValue(LocalDate.now().plus(1, ChronoUnit.DAYS));
	}
	@FXML
	void addImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();

		// define initial directory
		fileChooser.setInitialDirectory(new File("C:\\"));

		// define image extension
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.ico"));
		imgFile = fileChooser.showOpenDialog(null);

		if (imgFile != null) {
			this.imgSrc = imgFile.toURI().toString();
			imViewGoal.setImage(new Image(this.imgSrc));
			this.imageName = imgFile.getName();
		}
	}

	@FXML
	void processStartDate(ActionEvent event) {
		this.startDate = dpStartDate.getValue();
	}

	private void disableSaveTypes(boolean weekly, boolean monthly) {
		rbMonthly.setDisable(monthly);
		rbWeekly.setDisable(weekly);
	}

	private void availableSaveType() {
		if (this.period < 7) {
			this.disableSaveTypes(true, true);// first argument is for weekly save type radio button, second
			// argument is for monthly save type radio button
		} else if (this.period < 30) {
			this.disableSaveTypes(false, true);
		} else {
			this.disableSaveTypes(false, false);
		}
	}

	@FXML
	void processEndDate(ActionEvent event) {
		if (this.changeDate || this.isStartProgram) {
			this.isStartProgram = !isStartProgram;
			this.endDate = dpEndDate.getValue();

			// Total number of target days
			this.period = ChronoUnit.DAYS.between(this.startDate, this.endDate);
			this.availableSaveType();

			processSaveType(event);
		} else {
			changeDate = !changeDate;
		}

	}

	@FXML
	void processSaveType(ActionEvent event) {
		this.changeDate = !changeDate;
		if (rbWeekly.isSelected()) {
			this.saveType = "Weekly";
		} else if (rbMonthly.isSelected()) {
			this.saveType = "Monthly";
		} else {
			this.saveType = "Daily";
		}
		this.calculateSaveAmount();
	}

	private void calculateSaveAmount() {
		int noOfPeriod = 1;
		switch (this.saveType) {
		case "Daily":
			txtSaveAmount.setText(String.valueOf(this.objAmount / this.period));
			break;
		case "Weekly":
			noOfPeriod = (int) this.period / 7;
			txtSaveAmount.setText(String.valueOf(this.objAmount / noOfPeriod));
			break;
		case "Monthly":
			noOfPeriod = (int) this.period / 30;
			txtSaveAmount.setText(String.valueOf(this.objAmount / noOfPeriod));
			break;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		goalDbModel = GoalDBModel.getInstance();
		dpStartDate.setValue(LocalDate.now()); // Set current date in date picker for start date
		dpEndDate.setValue(LocalDate.now().plus(1, ChronoUnit.DAYS));

		this.disableSaveTypes(true, true);

		txtSaveAmount.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
				double saveAmount = 0;
				if (changeDate) {
					try {
						saveAmount = Double.valueOf(txtSaveAmount.getText());
					}catch(NumberFormatException e) {
						nameAmount.setText("Please Enter Numbers!");
					}
					LocalDate today = LocalDate.now();
					LocalDate amountToAdd = null;
					int noOfDays = (int) Math.ceil(objAmount / saveAmount);
					if (rbDaily.isSelected()) {
						amountToAdd = today.plus(noOfDays, ChronoUnit.DAYS);
					} else if (rbWeekly.isSelected()) {
						amountToAdd = today.plus(noOfDays * 7, ChronoUnit.DAYS);
					} else if (rbMonthly.isSelected()) {
						amountToAdd = today.plus(noOfDays * 30, ChronoUnit.DAYS);
					}
					changeDate = !changeDate;
					dpEndDate.setValue(amountToAdd);

				} else {
					changeDate = !changeDate;
				}
			}
		});

		// get Object Amount when user is entering in text field
		txtObjAmount.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
				try {
					objAmount = (txtObjAmount.getText() == "") ? 0 : Integer.valueOf(txtObjAmount.getText());
					calculateSaveAmount();


				} catch (NumberFormatException e) {
					nameAmount.setText("Please Enter Numbers!");
				}

			}
		});

		btnCreateGoal.disableProperty().bind((txtGoalName.textProperty().isNotEmpty()
				.and(txtObjAmount.textProperty().isNotEmpty()).and(txtSaveAmount.textProperty().isNotEmpty())).not());

	}

	public void updateTargetGoalUI(Goal goal) {
		goalId = goal.getGoalId();
		txtGoalName.setText(goal.getGoalName());
		txtObjAmount.setText(String.valueOf(goal.getGoalAmount()));
		Image image = null;
		if (goal.getGoalImgName() == null) {
			image = new Image(getClass().getResourceAsStream("../assets/goal.png"));
		} else {
			try {
				image = new Image(new FileInputStream("src/assets/goals/" + goal.getGoalImgName()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		imageName = goal.getGoalImgName();
		imViewGoal.setImage(image);

		dpStartDate.setValue(null);
		dpEndDate.setValue(null);

		String start = goal.getStartDate();
		LocalDate sDate = LocalDate.parse(start.substring(0, start.indexOf(" ")));
		dpStartDate.setValue(sDate);

		String end = goal.getEndDate();
		LocalDate eDate = LocalDate.parse(end.substring(0, end.indexOf(" ")));
		dpEndDate.setValue(eDate);

		if (this.changeDate || this.isStartProgram) {
			this.isStartProgram = !isStartProgram;
			this.endDate = dpEndDate.getValue();

			this.period = ChronoUnit.DAYS.between(sDate, eDate);
			this.availableSaveType();
		} else {
			changeDate = !changeDate;
		}

		txtSaveAmount.setText(String.valueOf(goal.getAmountToSave()));
		btnCreateGoal.setText("Update Goal");
		isInEditMode = true;
	}

	private void handleUpdateTargetGoal() {
		String goalName = txtGoalName.getText();
		double amountToSave = Double.valueOf(txtSaveAmount.getText());
		BufferedImage bImage;
		try {

			if (imgFile == null) {
				imgFile = new File("src/assets/goal.png");
			}

			bImage = ImageIO.read(imgFile);
			String mimeType = URLConnection.guessContentTypeFromName(imageName);

			ImageIO.write(bImage, mimeType.substring(mimeType.indexOf("/") + 1),
					new File("src/assets/goals/" + imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Goal newGoal = new Goal(this.goalId, goalName, this.imageName, this.objAmount,
				dpStartDate.getValue().toString(), dpEndDate.getValue().toString(), this.saveType, amountToSave, false,
				User.userId);

		if (!(goalName.length() > 10)) {
			if (!goalDbModel.isSubuNameExists(goalName)) {
				if (goalDbModel.updateTargetGoal(newGoal)) {
					AlertMaker.showAlert(AlertType.INFORMATION, "Successful Message", null,
							"Goal is updated successfully!");
					/*
					 * Stage homeStage = (Stage) txtGoalName.getScene().getWindow();
					 * homeStage.close();
					 */
				} else {
					AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Goal Update Failed!");
				}
			} else {
				AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Please Enter Different Subu name!");
			}

		} else {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Subu Name length must not be larger than 10.");
		}


	}

}
