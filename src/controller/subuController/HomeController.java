package controller.subuController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import database.AccountDBModel;
import database.GoalDBModel;
import database.SaveDBModel;
import database.WithdrawDBModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	private JFXButton goalCreateBtn;

	@FXML
	private JFXButton justSaveBtn;
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

	@FXML
	private LineChart<String, Double> lcMoney;

	private JFXPopup popup, actionPopup;

	private VBox actionBox,vbox;

	static ObservableList<Goal> goalList = FXCollections.observableArrayList();

	ObservableList<SaveAndWithdrawHistory> swhList = FXCollections.observableArrayList();

	SubuController subuController = new SubuController();

	private Subu subu;
	private Goal actionGoal;

	private GoalDBModel goalDbModel;
	private SaveDBModel saveDBModel;
	private WithdrawDBModel withdrawDBModel;
	private AccountDBModel account;

	FramesController frameController = new FramesController();

	List<SaveAndWithdrawHistory> sortedList = new ArrayList<SaveAndWithdrawHistory>();

	int row = 1;
	int column = 0;

	JFXButton action = new JFXButton("Action");
	JFXButton save = new JFXButton("Save");
	JFXButton withdraw = new JFXButton("WithDraw");
	JFXButton edit = new JFXButton("Edit");
	JFXButton delete = new JFXButton("Delete");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		goalDbModel = GoalDBModel.getInstance();
		saveDBModel = SaveDBModel.getInstance();
		withdrawDBModel = WithdrawDBModel.getInstance();
		account = AccountDBModel.getInstance();
		
		ScrollPane sp2 = new ScrollPane(subuHistory);
        sp2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		try {
			userName.setText(account.getUser());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

		tcDate.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory, String>("atTime"));
		tcAction.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory, String>("action"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<SaveAndWithdrawHistory, Double>("amount"));

		goalList = goalDbModel.selectAllSubu();
		loadSubuDataToGrid(goalList);
		initPopup();
		actionPopup();

		goalCreateBtn.setOnAction(e -> {
			try {
				frameController.openFrame("subuView", "TargetGoalUI", "Target Goal");
				goalList = goalDbModel.selectAllSubu();
				loadSubuDataToGrid(goalList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		justSaveBtn.setOnAction(e -> {
			try {
				frameController.openFrame("subuView", "JustSaveUI", "Just Save");
				goalList = goalDbModel.selectAllSubu();
				loadSubuDataToGrid(goalList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		save.setOnMouseClicked(e -> {
			loadSaveUI();
			goalList = goalDbModel.selectAllSubu();
			loadSubuDataToGrid(goalList);
		});

		withdraw.setOnMouseClicked(e -> {
			loadWithdrawUI();
			goalList = goalDbModel.selectAllSubu();
			loadSubuDataToGrid(goalList);
		});
		
		action.setOnMouseClicked(e -> {
			actionPopup.show(vbox, PopupVPosition.TOP, PopupHPosition.LEFT, action.getLayoutX() + 80,
					action.getLayoutY());
		});

		edit.setOnMouseClicked(e -> {
			handleGoalEdit(actionGoal);
			goalList = goalDbModel.selectAllSubu();
			loadSubuDataToGrid(goalList);
		});

		delete.setOnMouseClicked(e -> {
			handleGoalDelete(actionGoal);
			goalList = goalDbModel.selectAllSubu();
			loadSubuDataToGrid(goalList);

		});

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
		Stage home = (Stage) lblExpense.getScene().getWindow();
		home.close();
		frameController.openFrame("expenseView", "HomeUI", "Sumel");
	}

	@FXML
	void userUpdateForm(MouseEvent event) throws IOException {
		frameController.openFrame("accountView", "EditProfileUI", "Edit Profile");
	}

	@FXML
	void logout(MouseEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setContentText("Are you sure want to Logout?");
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			lblmySubu.getScene().getWindow().hide();
			frameController.openFrame("accountView", "MainUI", "Main UI");
		}

	}

	public void loadSubuDataToGrid(ObservableList<Goal> goalList) {
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
					GoalDBModel.goalName = goal.getGoalName();
					GoalDBModel.goalImg = goal.getGoalImgName();
					setTable();
					drawLineChart();

					if (e.getButton() == MouseButton.SECONDARY) {
						setTable();
						drawLineChart();
						popup.show(subuBox, PopupVPosition.TOP, PopupHPosition.LEFT, e.getX(), e.getY());
						actionGoal = new Goal();

						actionGoal = goal;
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
		double saveAmt = saveDBModel.selectAllWithdrawDataById(goal.getGoalId());
		double withdrawAmt = withdrawDBModel.selectAllWithdrawDataById(goal.getGoalId());
		double curretAmt = saveAmt - withdrawAmt;
		subu.setCurrentPrice(curretAmt);
		return subu;
	}

	private void handleGoalEdit(Goal goal) {
		if (goal == null) {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Goal loaded Failed!");
			return;
		}
		try {
			Parent parent = null;
            Stage stage = null;
			if (goal.getSaveType() == null) {
				FXMLLoader justSaveLoader = new FXMLLoader(
						getClass().getResource("../../view/subuView/JustSaveUI.fxml"));
				parent = justSaveLoader.load();
				JustSaveController justSaveController = (JustSaveController) justSaveLoader.getController();
				justSaveController.updateUntargetGoalUI(goal);
				goal.getStartDate();
				stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("Edit Just Save Goal");
			} else {
				FXMLLoader targetGoalLoader = new FXMLLoader(
						getClass().getResource("../../view/subuView/TargetGoalUI.fxml"));
				parent = targetGoalLoader.load();
				TargetGoalController targetGoalController = (TargetGoalController) targetGoalLoader.getController();
				targetGoalController.updateTargetGoalUI(goal);
			    stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("Edit Goal");
			}

			
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../../assets/icon/sumel.png")));
			stage.setScene(scene);
			stage.showAndWait();
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
		Separator separator2 = new Separator(Orientation.HORIZONTAL);
		// separator.setStyle("-fx-border-color:
		// black;-fx-border-style:solid;fx-border-width:0 0 1 0");
		Font font = Font.font("Georgia", 16);

		action.setFont(font);
		save.setFont(font);
		withdraw.setFont(font);

		action.setMaxWidth(Double.MAX_VALUE);
		save.setMaxWidth(Double.MAX_VALUE);
		withdraw.setMaxWidth(Double.MAX_VALUE);

	    vbox = new VBox(action, separator1, save, separator2, withdraw);

		

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

	private void setTable() {
		subuHistory.getItems().clear();
		List<SaveAndWithdrawHistory> swhArrayList = new ArrayList<SaveAndWithdrawHistory>();
		swhArrayList = saveDBModel.selectAllSaveData(GoalDBModel.goalId);
		swhArrayList.addAll(withdrawDBModel.selectAllWithdrawData(GoalDBModel.goalId));
		sortedList = swhArrayList.stream().sorted(Comparator.comparing(SaveAndWithdrawHistory::getAtTime))
				.collect(Collectors.toList());

		swhList.addAll(sortedList);
		subuHistory.setItems(swhList);
	}

	private void drawLineChart() {
		lcMoney.getData().clear();
		double total[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (SaveAndWithdrawHistory money : sortedList) {
			String swDateTime = money.getAtTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime lswDateTime = LocalDateTime.parse(swDateTime, formatter);
			switch (lswDateTime.getMonth()) {
			case JANUARY:
				total[0] = (money.getAction() == "Save") ? total[0] + money.getAmount() : total[0] - money.getAmount();
				break;
			case FEBRUARY:
				total[1] = (money.getAction() == "Save") ? total[1] + money.getAmount() : total[1] - money.getAmount();
				break;
			case MARCH:
				total[2] = (money.getAction() == "Save") ? total[2] + money.getAmount() : total[2] - money.getAmount();
				break;
			case APRIL:
				total[3] = (money.getAction() == "Save") ? total[3] + money.getAmount() : total[3] - money.getAmount();
				break;
			case MAY:
				total[4] = (money.getAction() == "Save") ? total[4] + money.getAmount() : total[4] - money.getAmount();
				break;
			case JUNE:
				total[5] = (money.getAction() == "Save") ? total[5] + money.getAmount() : total[5] - money.getAmount();
				break;
			case JULY:
				total[6] = (money.getAction() == "Save") ? total[6] + money.getAmount() : total[6] - money.getAmount();
				break;
			case AUGUST:
				total[7] = (money.getAction() == "Save") ? total[7] + money.getAmount() : total[7] - money.getAmount();
				break;
			case SEPTEMBER:
				total[8] = (money.getAction() == "Save") ? total[8] + money.getAmount() : total[8] - money.getAmount();
				break;
			case OCTOBER:
				total[9] = (money.getAction() == "Save") ? total[9] + money.getAmount() : total[9] - money.getAmount();
				break;
			case NOVEMBER:
				total[10] = (money.getAction() == "Save") ? total[10] + money.getAmount()
						: total[10] - money.getAmount();
				break;
			case DECEMBER:
				total[11] = (money.getAction() == "Save") ? total[11] + money.getAmount()
						: total[11] - money.getAmount();
				break;
			}
		}
		Series<String, Double> goalLineChart = new Series<String, Double>();
		goalLineChart.getData().add(new Data<String, Double>("Jan", total[0]));
		goalLineChart.getData().add(new Data<String, Double>("Feb", total[1]));
		goalLineChart.getData().add(new Data<String, Double>("Mar", total[2]));
		goalLineChart.getData().add(new Data<String, Double>("Apr", total[3]));
		goalLineChart.getData().add(new Data<String, Double>("May", total[4]));
		goalLineChart.getData().add(new Data<String, Double>("Jun", total[5]));
		goalLineChart.getData().add(new Data<String, Double>("Jul", total[6]));
		goalLineChart.getData().add(new Data<String, Double>("Aug", total[7]));
		goalLineChart.getData().add(new Data<String, Double>("Sep", total[8]));
		goalLineChart.getData().add(new Data<String, Double>("Oct", total[9]));
		goalLineChart.getData().add(new Data<String, Double>("Nov", total[10]));
		goalLineChart.getData().add(new Data<String, Double>("Dec", total[11]));
		lcMoney.getData().add(goalLineChart);
	}

}