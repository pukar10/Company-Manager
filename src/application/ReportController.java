package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportController {
	
    @FXML
    private Label employeeWorksOn;

    @FXML
    private Label employeeInfo;

    @FXML
    private Label employeeDependent;
	
	Company company;
	Connection c;
	
	public ReportController() throws SQLException, IOException {
		
		// EMPLOYEE INFO
    	company = new Company();
		c = Company.initiateConnection();
		
	    String tempString = "";
		
	    // Create query and result
	    String query = "SELECT fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno "
				    + "FROM employee "
				    + "WHERE ssn = " + Employee.ssn;
	
	    Statement statement = c.createStatement();
	    ResultSet result = statement.executeQuery(query);
	
	    tempString += "New Employee Info\n\n";
	
	    while (result.next()) {
		    tempString += "First Name: "  + result.getString(1) + "\n";
		    tempString += "Midial Int: "  + result.getString(2) + "\n";
		    tempString += "Last Name: "  + result.getString(3) + "\n";
		    tempString += "SSN: "  + result.getString(4) + "\n";
		    tempString += "Bdate: "  + result.getString(5) + "\n";
		    tempString += "Address: "  + result.getString(6) + "\n";
		    tempString += "Sex: "  + result.getString(7) + "\n";
		    tempString += "Salary: "  + result.getString(8) + "\n";
		    tempString += "Superssn: "  + result.getString(9) + "\n";
		    tempString += "Dno: "  + result.getString(10) + "\n\n";
	    }
	
	    // print result
	    System.out.println(tempString);
	    employeeInfo.setText(tempString);
	    
	    
	    
	    // EMPLOYEE WORKS ON -------------------
	    String tempString2 = "";
		
	    // query
	    String query2 = "SELECT essn, pno, hours "
	    				+ "FROM works_on "
	    				+ "WHERE essn = " + Employee.ssn;
	
	    Statement statement2 = c.createStatement();
	    ResultSet result2 = statement2.executeQuery(query2);
	
	    tempString2 += "New Employee Works_on Info\n\n";
	
	    while (result2.next()) {
		    tempString2 += "Essn: "  + result2.getString(1) + "\n";
		    tempString2 += "Project Number: "  + result2.getString(2) + "\n";
		    tempString2 += "Hours: "  + result2.getString(3) + "\n\n";
	    }
		    
	    System.out.println(tempString2);
	    employeeWorksOn.setText(tempString2);
	    
	    
	    
	    // EMPLOYEE DEPENDENTS -----------------------
	    if (Employee.dependent) {
		    String tempString3 = "";
			
		    // query
		    String query3 = "SELECT essn, dependent_name, sex, bdate, relationship "
		    			+ "FROM dependent "
		    			+ "WHERE essn = " + Employee.ssn;
		
		    Statement statement3 = c.createStatement();
		    ResultSet result3 = statement3.executeQuery(query3);
		
		    tempString3 += "New Employee Dependent Info\n\n";
		
		    while (result3.next()) {
			    tempString3 += "Essn : "  + result3.getString(1) + "\n";
			    tempString3 += "Dependent_name : "  + result3.getString(2) + "\n";
			    tempString3 += "Sex : "  + result3.getString(3) + "\n";
			    tempString3 += "Bdate : "  + result3.getString(4) + "\n";
			    tempString3 += "Relationship : "  + result3.getString(5) + "\n\n";
		    }
		
		    System.out.println(tempString3);
		    employeeDependent.setText(tempString3);
	    }
	    
	}
}
