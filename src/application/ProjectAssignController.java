package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ProjectAssignController {

    @FXML
    private Button apFinishBtn;

    @FXML
    private TextField hoursworkedTF;

    @FXML
    private Button assignProjectBtn;

    @FXML
    private TextField pnumTF;
    
	Company company;
	Connection c;
    
    @FXML
    void projectAssginBtnClick(ActionEvent event) throws SQLException, IOException {
    	String pno, hours, essn;
	    
    	company = new Company();
		c = Company.initiateConnection();
	
	    essn = Employee.ssn;
	
    	// get data for query
	    pno = pnumTF.getText();
	    hours = hoursworkedTF.getText();

	    if (Integer.parseInt(hours) < 0 || Integer.parseInt(hours) > 40) {
	    	Alert errorAlert = new Alert(AlertType.ERROR);
	    	errorAlert.setHeaderText("Input not valid");
	    	errorAlert.setContentText(hours + " greater than 40 or less than 0. \n resubmit.");
	    	errorAlert.showAndWait();
	    }else {
		    // create query
		    Statement statement = c.createStatement();
		
		    String query = "insert into works_on values('"+essn+"' , '"+pno+"' , "+hours+" )";  
		    System.out.println(query);
		
		    statement.executeQuery(query);
		
		    System.out.println("Done assigning Projects!");
		    
	    	Alert errorAlert = new Alert(AlertType.NONE);
	    	errorAlert.setHeaderText("Project Assigned!");
	    	errorAlert.setContentText("Project Number: " + pno + "\nProject Hours: " + hours);
	    	errorAlert.showAndWait();
	    }
    }
    
    @FXML
    void apFinishBtnClick(ActionEvent event) throws IOException {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Dependents");
    	alert.setHeaderText("New Employee have dependents? (yes or no)");
    	alert.setContentText("New Employee have dependents? (YES or NO)");
    	
    	ButtonType buttonYes = new ButtonType("YES");
    	ButtonType buttonNo = new ButtonType("NO");
    	
    	alert.getButtonTypes().setAll(buttonYes, buttonNo);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	
    	if (result.get() == buttonYes){
    		// Launch Dependent update Screen
    		Employee.dependent = true;
	   	   	Parent dependentUpdate = FXMLLoader.load(getClass().getResource("DependentUpdate.fxml"));
	   	   	Scene dependentUpdateScene = new Scene(dependentUpdate);
	   	   	 
	   	   	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   	   	 
	   	   	window.setScene(dependentUpdateScene);
	   	   	window.show();
    	}else if (result.get() == buttonNo) {
    		Employee.dependent = false;
    		// Launch Print report screen
//   	   	 Parent first = FXMLLoader.load(getClass().getResource("First.fxml"));
//   	   	 Scene firstScene = new Scene(first);
//   	   	 
//   	   	 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//   	   	 
//   	   	 window.setScene(firstScene);
//   	   	 window.show();
    	}
    }
}
