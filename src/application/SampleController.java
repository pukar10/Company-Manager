package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class SampleController {
	
	Company company;
	Connection c;
    @FXML
    private Button mgrBtn;
    @FXML
    private TextField mgrSSNTextField;
    
    @FXML
    void checkMgr(ActionEvent event) throws SQLException, IOException {
    	
    	// get connection to server/DB
    	company = new Company();
		c = Company.initiateConnection();
    	if (c == null) {
    		System.out.println("Null Connection!");
    	}else {
    		System.out.println("Connection!");
    	}
    	
    	
	    boolean valid = false;
	    String tempString = "";
	    String mgrSSN = mgrSSNTextField.getText();
	    
	    // create query to check manger SSN
	    String query = "SELECT mgrssn "
	    				+ "FROM department " ;
	
	    Statement statement = c.createStatement();
	    ResultSet result = statement.executeQuery(query);
	
	    System.out.println("Manager SSN inputed is " + mgrSSN);
	
	    // Set valid = 0 (not manager) valid = 1 (manager)
	    while(result.next()) {
	    	tempString += result.getString(1);
	
	    	if(tempString.compareTo(mgrSSNTextField.getText()) == 0) {
			    valid = true;
			    break;
	    	}
	    	
	    	tempString = "";
	    }
	
	    // check if manager or not
	    if(valid == true) {
	    	System.out.println("Valid Manager SSN!");
	    	
	    	nextScreenEmployeeInfo(event);
	    }else{
		    System.out.println("Invalid Manager SSN!");
		    // Show Error Alert
	    	Alert errorAlert = new Alert(AlertType.ERROR);
	    	errorAlert.setHeaderText("Invalid Manager SSN!");
	    	errorAlert.setContentText(mgrSSN + "\nThis is not a manager SSN, program will close!");
	    	errorAlert.showAndWait();
		    System.exit(0);
	    }
    }
    
    public void nextScreenEmployeeInfo(ActionEvent event) throws IOException {
    	 Parent employeeInfo = FXMLLoader.load(getClass().getResource("EmployeeInfo.fxml"));
    	 Scene employeeInfoScene = new Scene(employeeInfo);
    	 
    	 Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	 
    	 window.setScene(employeeInfoScene);
    	 window.show();
    }

}
