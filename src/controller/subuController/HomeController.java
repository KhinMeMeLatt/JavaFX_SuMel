package controller.subuController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import alert.AlertMaker;
import controller.FramesController;
import controller.accountController.MainController;
import database.GoalDBModel;
import database.SaveDBModel;
import database.WithdrawDBModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.subuModel.Goal;
import model.subuModel.SaveAndWithdrawHistory;
import model.subuModel.Subu;

public class HomeController implements Initializable {

	@FXML
	private Label lblmySubu;

	@FXML
	private Label lblExpense;

	@FXML
	private Label lblCurrencyConverter;

	@FXML
	private Label lblAbout;
	
	@FXML
    private TableView<SaveAndWithdrawHistory> subuHistory;

	@FXML
	private GridPane mySubus;

	@FXML
	private HBox subuScrollPane;

	@FXML
	private HBox history;

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private Label userName;

	@FXML
	private TableColumn<SaveAndWithdrawHistory, String> tcDate;

	@FXML
	private TableColumn<SaveAndWithdrawHistory, String> tcAction;

	@FXML
	private TableColumn<SaveAndWithdrawHistory, Double> tcAmount;

	private JFXPopup popup, actionPopup;

	private VBox actionBox;

	static ObservableList<Goal> goalList = FXCollections.observableArrayList();
	
	ObservableList<SaveAndWithdrawHistory> swhList = FXCollections.observableArrayList();

	SubuController subuController = new SubuController();

	private Subu subu;
	private Goal actionGoal;

	GoalDBModel goalDbModel = new GoalDBModel();
	SaveDBModel saveDBModel = new SaveDBModel();
	WithdrawDBModel withdrawDBModel = new WithdrawDBModel();


	FramesController frameController = new FramesController();

	int row = 1;
	int column = 0;

	JFXButton action = new JFXButton("Action");
	JFXButton save = new JFXButton("Save");
	JFXButton withdraw = new JFXButton("WithDraw");
	JFXButton edit = new JFXButton("Edit");
	JFXButton delete = new JFXButton("Delete");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblmySubu.setTextFill(Color.RED);
		JFXDepthManager.setDepth(history, 1);
		JFXDepthManager.setDepth(subuScrollPane, 1);

		txtSearch.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
				String keyword = txtSearch.getText();
				if (keyword.equals("")) {
					goalList = goalDbModel.selectAllSubu();
				} else {
					goalList = goalDbModel.searchSubuByName(keyword);
				}
				loadSubuDataToGrid(goalList);
			}
		});
		
		tcDate.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory,String>("atTime")); 
		tcAction.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory, String>("action"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory ,Double>("amount"));
		 

		goalList = goalDbModel.selectAllSubu();
		loadSubuDataToGrid(goalList);
		initPopup();
		actionPopup();
		refresh(false);
		edit.setOnMouseClicked(e -> {
			handleGoalEdit(actionGoal);
		});

		delete.setOnMouseClicked(e -> {
			handleGoalDelete(actionGoal);

		});
		
		
	}

	@FXML
	void goalCreateAction(ActionEvent event) throws IOException {
		frameController.openFrame("subuView", "TargetGoalUI", "Target Goal");
	}

	@FXML
	void justSaveAction(ActionEvent event) throws IOException {
		frameController.openFrame("subuView", "JustSaveUI", "Just Save");

	}

	@FXML
	void aboutFrame(MouseEvent event) throws IOException {
		frameController.openFrame("accountView", "AboutUI", "About");
	}

	@FXML
	void currencyCoventerFrame(MouseEvent event) throws IOException {
		frameController.openCurrencyFrame("CurrencyConverterUI", "Currency Converter");
	}

	@FXML
	void expenseFrame(MouseEvent event) throws IOException {
		frameController.openFrame("expenseView", "HomeUI", "Sumel");
		Stage home = (Stage) lblExpense.getScene().getWindow();
		home.close();
	}

	@FXML
	void showHistory(ActionEvent event) throws IOException {
		frameController.openFrame("expenseView", "HistoryUI", "Expense History");
	}

	public void loadSubuDataToGrid(ObservableList<Goal> goalList) {
		System.out.println(mySubus.getChildren());
		mySubus.getChildren().clear();
		row = 1;
		column = 0;
		for (Goal goal : goalList) {
			subu = new Subu();
			subu = getSubu(goal);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../view/subuView/Subu.fxml"));
			VBox subuBox;
			try {
				subuBox = loader.load();
				subuBox.setOnMouseClicked((e) -> {
					subuHistory.getItems().clear();
					GoalDBModel.goalId = goal.getGoalId();
					
                    setTable(GoalDBModel.goalId);
					if (e.getButton() == MouseButton.SECONDARY) {
						popup.show(subuBox, PopupVPosition.TOP, PopupHPosition.LEFT, e.getX(), e.getY());
						actionGoal = new Goal();
						this.actionGoal = goal;
					}
				});
				SubuController subuController = loader.getController();
				subuController.setSubuDataToUI(subu);
				JFXDepthManager.setDepth(subuBox, 1);
				mySubus.add(subuBox, column++, row);
				GridPane.setMargin(subuBox, new Insets(10, 10, 5, 10));
			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private Subu getSubu(Goal goal) {
		// get goals data
		subu = new Subu();
		subu.setSbName(goal.getGoalName());
		subu.setSbImageSrc(goal.getGoalImgName());
		double curretAmt = goalDbModel.getCurrentAmountByID(goal.getGoalId());
		subu.setCurrentPrice(curretAmt);
		return subu;
	}

	private void handleGoalEdit(Goal goal) {
		Goal goalForEdit = goalDbModel.selectSubuBySubuName(goal.getGoalName());
		if (goalForEdit == null) {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Goal loaded Failed!");
			return;
		}
		try {
			Parent parent = null;

			if (goalForEdit.getSaveType() == null) {
				FXMLLoader justSaveLoader = new FXMLLoader(
						getClass().getResource("../../view/subuView/JustSaveUI.fxml"));
				parent = justSaveLoader.load();
				JustSaveController justSaveController = (JustSaveController) justSaveLoader.getController();
				justSaveController.updateUntargetGoalUI(goalForEdit);
			} else {
				FXMLLoader targetGoalLoader = new FXMLLoader(
						getClass().getResource("../../view/subuView/TargetGoalUI.fxml"));
				parent = targetGoalLoader.load();
				TargetGoalController targetGoalController = (TargetGoalController) targetGoalLoader.getController();
				targetGoalController.updateTargetGoalUI(goalForEdit);

			}

			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Edit Goal");
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
			stage.setScene(scene);
			stage.showAndWait();
			stage.getIcons().add(new Image("/assets/icon/sumel.png"));

		} catch (IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void handleGoalDelete(Goal goal) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Deleting Subu");
		alert.setContentText("Are you sure want to delete the Subu " + goal.getGoalName() + " ?");
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			Boolean result = goalDbModel.deleteSubuBySubuName(goal.getGoalName());
			if (result) {
				AlertMaker.showAlert(AlertType.INFORMATION, "Subu deleted", null,
						goal.getGoalName() + " was deleted successfully.");
				goalList.remove(goal);
				loadSubuDataToGrid(goalList);
			} else {
				AlertMaker.showAlert(AlertType.INFORMATION, "Failed", null,
						goal.getGoalName() + " could not be deleted");
			}
		} else {
			AlertMaker.showAlert(AlertType.INFORMATION, "Deletion cancelled", null, "Deletion process cancelled");
		}
	}

	private void initPopup() {

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

	private void actionPopup() {

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
			handleGoalEdit(actionGoal);
		});

		delete.setOnMouseClicked(e -> {
			handleGoalDelete(actionGoal);

		});

	}

	private void loadSaveUI() {
		try {
			frameController.openFrame("subuView", "SaveUI", "Save");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadWithdrawUI() {
		try {
			frameController.openFrame("subuView", "WithdrawUI", "Withdraw");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refresh(boolean isneed) {
		if (isneed) {
			goalList = goalDbModel.selectAllSubu();
			System.out.println(goalList);
			loadSubuDataToGrid(goalList);
			System.out.println("True");
		} else {
			System.out.println("false");
		}
	}
	
	void setTable(int goalId) {
		List<SaveAndWithdrawHistory> swhArrayList = new ArrayList<SaveAndWithdrawHistory>();	

		swhArrayList = saveDBModel.selectAllSaveData(goalId);
		swhArrayList.addAll(withdrawDBModel.selectAllWithdrawData(goalId));
		for (SaveAndWithdrawHistory s : swhArrayList) {
			System.out.println(s.getAmount());
		}
		List<SaveAndWithdrawHistory> sortedList = swhArrayList.stream()
	            .sorted(Comparator.comparing(SaveAndWithdrawHistory::getAtTime))
	            .collect(Collectors.toList());
		
		swhList.addAll(sortedList);	    
		subuHistory.setItems(swhList);
	}

}