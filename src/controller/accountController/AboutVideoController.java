package controller.accountController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class AboutVideoController implements Initializable {
	
	@FXML
    private MediaView mediaView;
	private Media media;
	private MediaPlayer mediaPlayer;
	//private final String Media_Url = "C:\\JavaFXWorkSpace\\SuMel\\src\\assets\\Video.mp4";
	

    @FXML
    private JFXButton btnClose;
    
    

    @FXML
    void processClose(ActionEvent event) {
    	mediaPlayer.stop();
    	Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
    }


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		File videoFile = new File("C:\\JavaFXWorkSpace\\SuMel\\src\\assets\\Video.mp4");
		String videoPath = videoFile.toURI().toString();
		media = new Media(videoPath);
	    mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		// this.mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.setAutoPlay(true);
	}  

}
