package alert;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.accountModel.User;

public class AlertMaker {

	  public static boolean showAlert(AlertType alertType, String title, String header,String content) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        if(alertType == AlertType.CONFIRMATION) {
	        	Optional<ButtonType> result = alert.showAndWait();
	        	if(result.get() == ButtonType.OK) {
	        		return true;
	        	}
	        }else {
	        	alert.showAndWait();
	        }
	        return false;
	  }
	  
	  //Target Expense Setting
	  public static int createTextDialog() {
		  TextInputDialog dialog = new TextInputDialog(String.valueOf(User.expectedExpense));
		  dialog.setTitle("Expense Target");
		  dialog.setHeaderText("Set your expense target");
		  dialog.setContentText("Amount:");
		  Optional<String> result = dialog.showAndWait();
		  if (result.isPresent()){
			  try {
				  return Integer.parseInt(result.get());  
			  }catch(NumberFormatException e) {
				  showAlert(AlertType.ERROR,"Error", "Error", "Input Amount is invalid");
				  createTextDialog();
			  }
		  }
		  return -1;
	  }
}
