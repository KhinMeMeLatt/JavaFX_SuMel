package controller.subuController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.effects.JFXDepthManager;

import alert.AlertMaker;
import controller.FramesController;
import controller.accountController.MainController;
import database.GoalDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.subuModel.Goal;
import model.subuModel.Subu;

public class HomeController implements Initializable {

	@FXML
	private GridPane mySubus;

	@FXML
	private HBox subuScrollPane;

	@FXML
	private HBox history;

	@FXML
	private Label userName;

	static List<Subu> subus = new ArrayList<Subu>();

	SubuController subuController = new SubuController();

	private Subu subu;

	GoalDBModel goalDbModel = new GoalDBModel();
	
	FramesController frameController = new FramesController();

	int row = 1;
	int column = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		JFXDepthManager.setDepth(history, 1);
		JFXDepthManager.setDepth(subuScrollPane, 1);

		getSubus();
		loadSubuData();
	}

	@FXML
    void goalCreateAction(ActionEvent event) throws IOException {
		Parent mainParent;
		Stage stage = new Stage();
		mainParent = FXMLLoader.load(getClass().getResource("../../view/subuView/TargetGoalUI.fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		stage.setTitle("TargetGoal");
		stage.getIcons().add(new Image("/assets/icon/sumel.png"));
    }

    @FXML
    void justSaveAction(ActionEvent event) throws IOException {
    	Parent mainParent;
		Stage stage = new Stage();
		mainParent = FXMLLoader.load(getClass().getResource("../../view/subuView/JustSaveUI.fxml"));
		Scene scene = new Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("../../view/main.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		stage.setTitle("TargetGoal");
		stage.getIcons().add(new Image("/assets/icon/sumel.png"));
    }
    
	private void loadSubuData() {
		subus.clear();
		subus = getSubus();
		for (Subu subu : subus) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../view/subuView/Subu.fxml"));
				VBox mySubu = loader.load();
				SubuController subuController = loader.getController();
				subuController.setSubuDataToUI(subu);
				JFXDepthManager.setDepth(mySubu, 1);
				mySubus.add(mySubu, column++, row);
				GridPane.setMargin(mySubu, new Insets(10, 10, 5, 10));
			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<Subu> getSubus() {
		// get goals data
		List<Goal> goals = new ArrayList<Goal>();
		goals = goalDbModel.selectAllSubu();
		List<Subu> subus = new ArrayList<Subu>();
		for (int i = 0; i < goals.size(); i++) {
			subu = new Subu();
			subu.setSbName(goals.get(i).getGoalName());
			subu.setSbImageSrc(goals.get(i).getGoalImgName());
			double curretAmt = goalDbModel.getCurrentAmountByID(goals.get(i).getGoalId());
			subu.setCurrentPrice(curretAmt);
			subus.add(subu);
		}
		return subus;
	}

	public void handleGoalEdit(String sbName) {
		Goal goalForEdit = goalDbModel.selectSubuBySubuName(sbName);
		if (goalForEdit == null) {
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Goal loaded Failed!");
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
			stage.show();
			stage.getIcons().add(new Image("/assets/icon/sumel.png"));

		} catch (IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void handleGoalDelete(String sbName, Node node) {
		/*
		 * Goal subuForDelete = goalDbModel.selectSubuBySubuName(sbName.getText()); int
		 * currentPrice = Integer.valueOf(sbCurrentPrice.getText()); subu = new
		 * Subu(subuForDelete.getGoalName(),currentPrice,subuForDelete.getGoalImgName())
		 * ;
		 */
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Deleting Subu");
		alert.setContentText("Are you sure want to delete the Subu " + sbName + " ?");
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			Boolean result = goalDbModel.deleteSubuBySubuName(sbName);
			if (result) {
				AlertMaker.showAlert(AlertType.INFORMATION,"Subu deleted", null, sbName + " was deleted successfully.");
				int i = GridPane.getColumnIndex(node);
				subus.remove(i);
				System.out.println(subus);
			} else {
				AlertMaker.showAlert(AlertType.INFORMATION,"Failed", null, sbName + " could not be deleted");
			}
		} else {
			AlertMaker.showAlert(AlertType.INFORMATION,"Deletion cancelled", null, "Deletion process cancelled");
		}
	}

}