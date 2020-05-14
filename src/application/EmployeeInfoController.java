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

public class EmployeeInfoController {
    @FXML
    private TextField dnoTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField superssnTF;

    @FXML
    private TextField bdateTF;

    @FXML
    private TextField addressTF;

    @FXML
    private TextField salaryTF;

    @FXML
    private TextField fnameTF;

    @FXML
    private TextField lnameTF;

    @FXML
    private TextField ssnTF;

    @FXML
    private TextField minitTF;

    @FXML
    private TextField sexTF;

    @FXML
    private Button addEmployeeBtn;
    
	Company company;
	Connection c;

    @FXML
    @SuppressWarnings("unused")
    void addEmployeeBtnClick(ActionEvent event) throws IOException, SQLException {
    	String fname,minit,lname,ssn,bdate,address,sex, superssn,dno, salary,email;
		String answer = "no";
	
	    System.out.println("Adding new Employee \n");
	
	    // get data for query
	    fname = fnameTF.getText();
	    Employee.fname = fname;
	    minit = minitTF.getText();	
	    Employee.minit = minit;
	    lname = lnameTF.getText();
	    Employee.lname = lname;
	    ssn = ssnTF.getText();	
	    Employee.ssn = ssn;
	    bdate = bdateTF.getText();	
	    Employee.bdate = bdate;
	    address = addressTF.getText();
	    Employee.address = address;
	    sex = sexTF.getText();
	    Employee.sex = sex;
	    salary = salaryTF.getText();
	    Employee.salary = salary;
	    superssn = superssnTF.getText();
	    Employee.superssn = superssn;
	    dno = dnoTF.getText();	
	    Employee.dno = dno;
	    email = emailTF.getText();
	    Employee.email = email;
	    
    	// get connection to server/DB
    	company = new Company();
		c = Company.initiateConnection();
	    
	    // Make query
	    Statement statement = c.createStatement();
	
	    String query = "insert into employee values('"+fname+"' , '"+minit+"' , '"+lname+"' , '"+ssn+"' , '"+bdate+"' , '"+address+"' , '"+sex+"' , '"+salary+"' , '"+superssn+"' , '"+dno+"' , '"+email+"' )";  
	    System.out.println(query);
	
	    statement.executeQuery(query);
	
	    // Show Confirmation: New Employee added!
	    System.out.println("New Employee inserted! \n");
    	Alert errorAlert = new Alert(AlertType.NONE);
    	errorAlert.setHeaderText("Employee added");
    	errorAlert.setContentText("New Employee added!\nfname: " + fname + "\nminit: " + minit + "\nLname: " + lname + "\nSSN: " + ssn + "\nBdate: " + bdate + "\nAddress: " +
    							  address + "\nSex: " + sex + "\nSalary: " + salary + "\nSuperSSN: " + superssn + "\nDno: " + dno + "\nEmail: " + "email");
    	errorAlert.showAndWait();
	    
	
	    // Conditions
	    // Assign employee to project
	    nextScreenProjectAssign(event);
    }
    
    public void nextScreenProjectAssign(ActionEvent event) throws IOException {
   	 Parent projectAssign = FXMLLoader.load(getClass().getResource("ProjectAssign.fxml"));
   	 Scene projectAssignScene = new Scene(projectAssign);
   	 
   	 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
   	 
   	 window.setScene(projectAssignScene);
   	 window.show();
   }
 
    
}
