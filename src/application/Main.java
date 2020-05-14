package application;
import java.io.IOException;
import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws SQLException, IOException {
		// Make a connection to the server/DB
		Company company = new Company();
		Connection c = Company.initiateConnection();
    	if (c == null) {
    		System.out.println("Null Connection!");
    	}
    	
    	// Part 1 Question 1
    	System.out.println(company.researchDept(c));
    	
    	// Part 1 Question 2
    	System.out.println(company.houstonP(c));
    	
		launch(args);
	}
}
