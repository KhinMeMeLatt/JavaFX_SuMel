package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class CurrencyController implements Initializable{

	@FXML
	private JFXComboBox<Label> fromCountry;

	@FXML
	private JFXComboBox<Label> toCountry;

	@FXML
	private JFXTextField txtAmount;

	private String[] countriesList = {"United Arab Emirates (AED)","Afghanistan (AFN)"
			,"Albania (ALL)","Armenia (AMD)","Netherlands Antilles (ANG)"};
	
	private float getExchangeRate(String baseCountryCode, String destinationCountryCode) {
		try {
			String url_str = "https://v6.exchangerate-api.com/v6/b43099997aab83f2874b91c6/latest/"+baseCountryCode;

			// Making Request
			URL url = new URL(url_str);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			
			JsonParserFactory factory = Json.createParserFactory(null);
			JsonParser jp = factory.createParser(new InputStreamReader((InputStream) request.getContent()));
			while(jp.hasNext()) {
				Event converterEvent = jp.next();
				if(converterEvent == JsonParser.Event.KEY_NAME) {
					String key = jp.getString();
					converterEvent = jp.next();
					if(key.equals(destinationCountryCode)) {
						return Float.valueOf(jp.getString());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@FXML
	void convert(ActionEvent event) {
		String fromCountrycb = String.valueOf(fromCountry.getValue());
		//Extract currency code from combo box data
		String baseCountryCode = fromCountrycb.substring(fromCountrycb.indexOf("(")+1, fromCountrycb.indexOf(")"));

		String toCountrycb = String.valueOf(toCountry.getValue());
		//Extract currency code from combo box data
		String destinationCountryCode = toCountrycb.substring(toCountrycb.indexOf("(")+1, toCountrycb.indexOf(")"));

		float rate = getExchangeRate(baseCountryCode,destinationCountryCode);
		float result = rate * Float.valueOf(txtAmount.getText());
		
		AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", "From "+baseCountryCode+" currency to "+destinationCountryCode+" currency", "Result: "+result);
	}

	private ObservableList<Label> getCountries() throws FileNotFoundException{
		ObservableList<Label> countries = FXCollections.observableArrayList();
		for(String country : countriesList) {
			Label lblcountry = new Label(country);
			Image icon = new Image(new FileInputStream("src/assets/countries/"+country.substring(country.indexOf("(")+1, country.indexOf(")"))+".png"));
			lblcountry.setGraphic(new ImageView(icon));
			countries.add(lblcountry);
		}
		return countries;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			fromCountry.setItems(getCountries());
			toCountry.setItems(getCountries());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
