package game;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class EcoWars {

	public static void main(String[] args) {
		Sound s = new Sound();
		
		DatabaseReference database = null;
		
		// Database
		FileInputStream refreshToken;
		try {
			
			refreshToken = new FileInputStream("EcoWarsKey.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(refreshToken))
				    .setDatabaseUrl("https://ecowars-db164-default-rtdb.firebaseio.com")
				    .build();

				FirebaseApp.initializeApp(options);
				database = FirebaseDatabase.getInstance().getReference();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Setup
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(ch.qos.logback.classic.Level.ERROR);
		
		MenuScreen menu = new MenuScreen(database);
		menu.show();
		
	}
	
}
