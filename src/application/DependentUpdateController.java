package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DependentUpdateController {
    @FXML
    private Button addDependentBtn;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField relationshipTF;

    @FXML
    private TextField bdateTF;

    @FXML
    private TextField sexTF;
    
	Company company;
	Connection c;

    @FXML
    void addDependentBtnClick(ActionEvent event) throws SQLException, IOException {
	    String dependent_name,sex,bdate,relationship;
	    
    	company = new Company();
		c = Company.initiateConnection();

	    String essn = Employee.ssn;

	    System.out.println("Adding a new Dependent! \n");

	    // get data for query 
	    dependent_name = nameTF.getText();
	    sex = sexTF.getText();
	    bdate = bdateTF.getText();
	    relationship = relationshipTF.getText();
	    
	    Statement statement = c.createStatement();
	
	    // create query
	    String query = "insert into dependent values('"+essn+"' , '"+dependent_name+"' , '"+sex+"' , '"+bdate+"', "+relationship+" )";  
	    System.out.println(query);
	
	    statement.executeQuery(query);
	    
    	Alert errorAlert = new Alert(AlertType.NONE);
    	errorAlert.setHeaderText("Dependent");
    	errorAlert.setContentText("Dependent Added!");
    	errorAlert.showAndWait();
    	
    	nextScreenPrintReport(event);
    }

	public void nextScreenPrintReport(ActionEvent event) throws IOException {
	   	 Parent report = FXMLLoader.load(getClass().getResource("Report.fxml"));
	   	 Scene reportScene = new Scene(report);
	   	 
	   	 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   	 
	   	 window.setScene(reportScene);
	   	 window.show();
		
	}
}
