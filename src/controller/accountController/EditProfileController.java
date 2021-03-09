package controller.accountController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.AccountDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.accountModel.Encryption;
import model.accountModel.User;

public class EditProfileController implements Initializable {

    @FXML
    private JFXTextField tfUserName;

    @FXML
    private JFXTextField tfUserEmail;

    @FXML
    private Button btnClose;
    
    @FXML
    private Button btnUpdate;

    @FXML
    private JFXPasswordField pfPassword;
    
    private User user = new User();
    
    private AccountDBModel accountdb;

    @FXML
    void processClose(ActionEvent event) {

    	Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
    }

    @FXML  //Update User info
    void processUpdate(ActionEvent event) throws SQLException {
    	final String secretKey = "ssshhhhhhhhhhh!!!!";
    	String password = pfPassword.getText();
		String encryptedString = Encryption.encrypt(password, secretKey) ;
    	accountdb = new AccountDBModel();
    	user.setUserName(tfUserName.getText());
    	user.setEmail(tfUserEmail.getText());
    	user.setPassword(encryptedString);
    	accountdb.updateProfile(user);
    	

    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			accountdb = new AccountDBModel();
			user = accountdb.selectUser();
			tfUserName.setText(user.getUserName());
	    	tfUserEmail.setText(user.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnUpdate.disableProperty().bind((tfUserName.textProperty().isNotEmpty()
				.and(tfUserEmail.textProperty().isNotEmpty()).and(pfPassword.textProperty().isNotEmpty())).not());
		
		
	}
	
	
}
