package application;


import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import databasePackage.*;

//Link forlist view 
//https://www.youtube.com/watch?v=gvBGu3mw7YU


//Following code based off Sample code form Charles 
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			String currentDirectory = System.getProperty("user.dir");
		
			Parent root = FXMLLoader.load(getClass().getResource("/view/formTeamsGUI.fxml"));
			Scene scene = new Scene(root,2000,1500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
