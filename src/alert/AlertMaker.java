package alert;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
}
