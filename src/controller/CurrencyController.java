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
			,"Albania (ALL)","Armenia (AMD)","Netherlands Antilles (ANG)"
			,"Angola (AOA)","Argentina (ARS)","Australia (AUD)","Aruba (AWG)","Azerbaijan (AZN)","Bosnia and Herzegovina (BAM)"
			,"Barbados (BBD)","Bangladesh (BDT)","Bulgaria (BGN)","Bahrain (BHD)","Burundi (BIF)","Bermuda (BMD)","Brunei (BND)"
			,"Bolivia (BOB)","Brazil (BRL)","Bahamas (BSD)","Bhutan (BTN)","Botswana (BWP)","Belarus (BYN)","Belize (BZD)"
			,"Canada (CAD)","Democratic Republic of the Congo (CDF)","Switzerland (CHF)","Chile (CLP)","China (CNY)","Colombia (COP)"
			,"Costa Rica (CRC)","Cuba (CUC)","Cuba (CUP)","Cape Verde (CVE)","Czech Republic (CZK)","Djibouti (DJF)","Denmark (DKK)"
			,"Dominican Republic (DOP)","Algeria (DZD)","Egypt (EGP)","Eritrea (ERN)","Ethiopia (ETB)","European Union (EUR)","Fiji (FJD)"
			,"Falkland Islands (FKP)","Faroe Islands (FOK)","United Kingdom (GBP)","Georgia (GEL)","Guernsey (GGP)","Ghana (GHS)","Gibraltar (GIP)"
			,"The Gambia (GMD)","Guinea (GNF)","Guatemala (GTQ)","Guyana (GYD)","Hong Kong (HKD)","Honduras (HNL)","Croatia (HRK)","Haiti (HTG)"
			,"Hungary (HUF)","Indonesia (IDR)","Israel (ILS)","Isle of Man (IMP)","India (INR)","Iraq (IQD)","Iran (IRR)","Iceland (ISK)","Jamaica (JMD)"
			,"Jordan (JOD)","Japan (JPY)","Kenya (KES)","Kyrgyzstan (KGS)","Cambodia (KHR)","Kiribati (KID)","Comoros (KMF)","South Korea (KRW)","Kuwait (KWD)"
			,"Cayman Islands (KYD)","Kazakhstan (KZT)","Laos (LAK)","Lebanon (LBP)","Sri Lanka (LKR)","Liberia (LRD)","Lesotho (LSL)","Libya (LYD)","Morocco (MAD)"
			,"Moldova (MDL)","Madagascar (MGA)","North Macedonia (MKD)","Myanmar (MMK)","Mongolia (MNT)","Macau (MOP)","Mauritania (MRU)","Mauritius (MUR)","Maldives (MVR)"
			,"Malawi (MWK)","Mexico (MXN)","Malaysia (MYR)","Mozambique (MZN)","Namibia (NAD)","Nigeria (NGN)","Nicaragua (NIO)","Norway (NOK)","Nepal (NPR)","New Zealand (NZD)"
			,"Oman (OMR)","Panama (PAB)","Peru (PEN)","Papua New Guinea (PGK)","Philippines (PHP)","Pakistan (PKR)","Poland (PLN)","Paraguay (PYG)","Qatar (QAR)","Romania (RON)"
			,"Serbia (RSD)","Russia (RUB)","Rwanda (RWF)","Saudi Arabia (SAR)","Solomon Islands (SBD)","Seychelles (SCR)","Sudan (SDG)","Sweden (SEK)","Singapore (SGD)","Saint Helena (SHP)"
			,"Sierra Leone (SLL)","Somalia (SOS)","Suriname (SRD)","South Sudan (SSP)","Sao Tome and Principe (STN)","Syria (SYP)","Eswatini (SZL)","Thailand (THB)","Tajikistan (TJS)"
			,"Turkmenistan (TMT)","Tunisia (TND)","Tonga (TOP)","Turkey (TRY)","Trinidad and Tobago (TTD)","Tuvalu (TVD)","Taiwan (TWD)","Tanzania (TZS)","Ukraine (UAH)","Uganda (UGX)"
			,"United States (USD)","Uruguay (UYU)","Uzbekistan (UZS)","Venezuela (VES)","Vietnam (VND)","Vanuatu (VUV)","Samoa (WST)","Yemen (YER)","South Africa (ZAR)","Zambia (ZMW)"};
	
	private float getExchangeRate(String baseCountryCode, String destinationCountryCode) {
		try {
			String url_str = "https://v6.exchangerate-api.com/v6/588e05b36428d517c77d7639/latest/"+baseCountryCode;

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
		try {

		float rate = getExchangeRate(baseCountryCode,destinationCountryCode);
		float result = rate * Float.valueOf(txtAmount.getText());
		
		AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", "From "+baseCountryCode+" currency to "+destinationCountryCode+" currency", "Result: "+result);
		}
		catch(NumberFormatException e){
			txtAmount.setText("Please Enter Numbers!");
		}
	}

	private ObservableList<Label> getCountries() throws FileNotFoundException{
		ObservableList<Label> countries = FXCollections.observableArrayList();
		for(String country : countriesList) {
			Label lblcountry = new Label(country);
			Image icon = new Image(new FileInputStream("src/assets/countries/"+country.substring(country.indexOf("(")+1, country.indexOf(")"))+".png"));
			ImageView img = new ImageView(icon);
			img.setFitHeight(15);
			img.setFitWidth(15);
			lblcountry.setGraphic(img);
			countries.add(lblcountry);
		}
		return countries;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try { 
			fromCountry.setItems(getCountries());
			fromCountry.setPromptText("From");
			toCountry.setItems(getCountries());
			toCountry.setPromptText("To");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
