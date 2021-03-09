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
                //Initialising path of the media file,
		File videoFile = new File("src/assets/about.mp4");
		String videoPath = videoFile.toURI().toString();
                //Instantiating Media class  
		media = new Media(videoPath);
		//Instantiating MediaPlayer class  
	        mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaView.setMediaPlayer(mediaPlayer);
		
		//by setting this property to true, the Video will be played   
		mediaPlayer.setAutoPlay(true);
	}  

}
