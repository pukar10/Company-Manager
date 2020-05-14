package application;
import java.sql.*;
import java.util.Scanner;
import java.io.*;
// import java.util.*;

public class Company {
    public static void main(String args[]) throws SQLException, IOException {
    	Connection c = initiateConnection();
    	if (c == null) {
    		System.out.println("Null Connection!");
    	}
        Company temp = new Company();

        System.out.println(temp.researchDept(c));
        System.out.println(temp.houstonP(c));
        manager(c);
    }

    public static Connection initiateConnection() throws SQLException, IOException {
        // Load Driver
    	try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Could not be loaded");
        }
    	
        // Connect to DB 
        try {
        	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "psubedi4", "sortamsy");
        	System.out.println("Connection Successful! " + conn);
        	return conn;
        }catch(Exception e) {
        	System.out.println(e);
        }
        System.out.println("Null connection!");
		return null;
    }

    
    // Retrieves the employees that work in the research department 
    // returns employee last names and SSN
    public String researchDept(Connection c) throws SQLException {
        String tempString = "";
        
        // create query get results
        String query = "SELECT lname, ssn "
                		+ "FROM employee JOIN department ON dno=dnumber "
                		+ "WHERE dname = 'Research'";

        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(query);

        tempString += "RESEARCH DEPARTMENT Employee\n\n";

        while (result.next())
        {
            tempString += "Last Name: "  + result.getString(1) + "\n";
            tempString += "SSN: "  + result.getString(2) + "\n\n";
        }
        return tempString;
    }

    
    // Retrieves the employees that work in departments located in Houston AND work on project ProductZ. 
    // Returns their last names, SSN, # of hours worked. 
    public String houstonP(Connection c) throws SQLException {
        String tempString = "";
        
        // create query get results
        String query = "SELECT DISTINCT lname, ssn, hours "
                		+ "FROM employee, dept_locations, project, works_on "
                		+ "WHERE dlocation = 'Houston' AND pname = 'ProductZ' AND dno = dnumber AND dnumber = dnum AND pnumber = pno AND ssn = essn" ;

        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(query);

        tempString += "ProductZ\n\n";

        while (result.next())
        {
            tempString += "Last Name: "  + result.getString(1) + "\n";
            tempString += "SSN: "  + result.getString(2) + "\n";
            tempString += "Hours: "  + result.getDouble(3) + "\n\n";
        }

        return tempString;
    }
    
    
    
    // PART TWO -------------
    
    
    // Allows department manager to add new employees
    private static void manager(Connection c) throws SQLException {
	    String mssn;
	
	    // get input/data
	    Scanner scan = new Scanner(System.in);
	
	    System.out.println("Welcome to the Manager section!\n");
	
	    System.out.println("Please enter your Manager SSN");
	    mssn = scan.next();
	
	    //check if manager and add new employee
	    checkMGRssn(mssn, c);
	    addNewEmp(c, scan);
    }
    
    
    // checks if the SSN is that of a manager
    private static void checkMGRssn(String mssn, Connection c) throws SQLException {
	    boolean valid = false;
	    String tempString = "";
	
	    // create query to check manger SSN
	    String query = "SELECT mgrssn "
	    				+ "FROM department " ;
	
	    Statement statement = c.createStatement();
	    ResultSet result = statement.executeQuery(query);
	
	    System.out.println("Manager SSN inputed is " + mssn);
	
	    // Set valid = 0 (not manager) valid = 1 (manager)
	    while(result.next()) {
	    	tempString += result.getString(1);
	
	    	if(tempString.compareTo(mssn) == 0) {
			    valid = true;
			    break;
	    	}
	    	
	    	tempString = "";
	    }
	
	    // check if manager or not
	    if(valid == true) {
	    	System.out.println("Valid Manager SSN!");
	    }else{
		    System.out.println("Invalid Manager SSN!");
		    System.exit(0);
	    }
    }
    
    // adds new employee to table
    private static void addNewEmp(Connection c, Scanner scan) throws SQLException {
	    
    	String fname,minit,lname,ssn,bdate,address,sex, superssn,dno, salary,email;
	    String answer = "no";
	
	    System.out.println("Adding new Employee \n");
	
	    // get data for query
	    fname = scan.nextLine();
	    System.out.println("Enter Employee fname: ");
	
	    minit = scan.nextLine();
	    System.out.println("Enter Employee minit: ");
	
	    lname = scan.nextLine();
	    System.out.println("Enter Employee lname: ");
	
	    ssn = scan.nextLine();
	    System.out.println("Enter Employee ssn: ");
	
	    bdate = scan.nextLine();
	    System.out.println("Enter Employee bdate: ");
	
	    address = scan.nextLine();
	    System.out.println("Enter Employee address: ");
	
	    sex = scan.nextLine();
	    System.out.println("Enter Employee sex: ");
	
	    salary = scan.nextLine();
	    System.out.println("Enter Employee Salary: ");
	
	    superssn = scan.nextLine();
	    System.out.println("Enter Employee Superssn: ");
	
	    dno = scan.nextLine();
	    System.out.println("Enter Employee Dno: ");
	
	    email = scan.nextLine();
	    System.out.println("Enter Employee email: ");
	
	    // Make query
	    Statement statement = c.createStatement();
	
	    String query = "insert into employee values('"+fname+"' , '"+minit+"' , '"+lname+"' , '"+ssn+"' , '"+bdate+"' , '"+address+"' , '"+sex+"' , '"+salary+"' , '"+superssn+"' , '"+dno+"' , '"+email+"' )";  
	    System.out.println(query);
	
	    statement.executeQuery(query);
	
	    System.out.println("New Employee inserted! \n");
	
	    // Conditions
	    // Assign employee to project
	    projectAssign(c, scan, ssn);
	
	    // Does the employee have dependents?
	    System.out.println("New Employee have dependents? (yes or no)");
	    answer = scan.next();
	
	    if(answer.equals("yes")) {
	    	dependentUpdate(c, scan, ssn);
	    }
	    
	    // Done print report!
	    printReport(c, scan, ssn, answer);
    }
    
    // assign new project(s) for new employee
    private static void projectAssign(Connection c, Scanner scan, String ssn) throws SQLException {
	    String pno, hours, essn;
	    String answer = "yes";
	
	    essn = ssn;
	
	    System.out.println("Adding project(s) for the new Employee! \n");
	    System.out.println("Max hours is 40 \n");
	
	    do
	    {
	    	// get data for query
		    System.out.println("Enter project number for New Employee: ");
		    pno = scan.next();
		
		    System.out.println("Enter number of hours worked on project. (Nothing over 40): ");
		    hours = scan.next();
	
		    while(Integer.parseInt(hours) < 0 || Integer.parseInt(hours) > 40) {
			    System.out.println("Invalid input! What did I say! Re-enter a valid hour!");
			    hours = scan.next();
		    }
	
		    System.out.println("Total hours working = " + hours);
		
		    // create query
		    Statement statement = c.createStatement();
		
		    String query = "insert into works_on values('"+essn+"' , '"+pno+"' , "+hours+" )";  
		    System.out.println(query);
		
		    statement.executeQuery(query);
		
		    System.out.println("Assign another Project? (yes or no): ");
		    answer = scan.next();
	
	    }while(answer.equals("yes"));
	
	    System.out.println("Done assigning Projects!");
    }
    
    // Assign new dependent for new employee
    private static void dependentUpdate(Connection c, Scanner scan, String ssn) throws SQLException {
	    String dependent_name,sex,bdate,relationship;

	    String essn = ssn;

	    System.out.println("Adding a new Dependent! \n");

	    // get data for query 
	    dependent_name = scan.nextLine();
	    System.out.println("Enter Dependent name: ");
	
	    sex = scan.nextLine();
	    System.out.println("Enter Dependent sex: ");
	
	    bdate = scan.nextLine();
	    System.out.println("Enter Dependent bdate: ");
	
	    relationship = scan.nextLine();
	    System.out.println("Enter Dependent relationship: ");
	
	    Statement statement = c.createStatement();
	
	    // create query
	    String query = "insert into dependent values('"+essn+"' , '"+dependent_name+"' , '"+sex+"' , '"+bdate+"', "+relationship+" )";  
	    System.out.println(query);
	
	    statement.executeQuery(query);

    }
    
    // print report of new employee (with dependent or without dependent)
    private static void printReport(Connection c, Scanner k, String ssn, String answer) throws SQLException {
	    // if yes (has dependents) print info, works_on, dependents
    	if(answer.equals("yes")) {
		    printEmployeeInfo(c, ssn);
		    printEmployeeWorks_on(c, ssn);
		    PrintEmployeeDependent(c, ssn);
	    }
    	// if no (no dependents) just print info and works_on
	    else {
		    printEmployeeInfo(c, ssn);
		    printEmployeeWorks_on(c, ssn);
	    }

    }
    
    private static void printEmployeeInfo(Connection c, String ssn) throws SQLException
    {
	    String tempString = "";
	
	    // Create query and result
	    String query = "SELECT fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno "
				    + "FROM employee "
				    + "WHERE ssn = " + ssn;
	
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
    }
    
    private static void printEmployeeWorks_on(Connection c, String ssn) throws SQLException {
	    String tempString = "";
	
	    // query
	    String query = "SELECT essn, pno, hours "
	    				+ "FROM works_on "
	    				+ "WHERE essn = " + ssn;
	
	    Statement statement = c.createStatement();
	    ResultSet result = statement.executeQuery(query);
	
	    tempString += "New Employee Works_on Info\n\n";
	
	    while (result.next()) {
		    tempString += "Essn: "  + result.getString(1) + "\n";
		    tempString += "Project Number: "  + result.getString(2) + "\n";
		    tempString += "Hours: "  + result.getString(3) + "\n\n";
	    }
	
	    System.out.println(tempString);
    }

    
    private static void PrintEmployeeDependent(Connection c, String ssn) throws SQLException{
	    String tempString = "";
	
	    // query
	    String query = "SELECT essn, dependent_name, sex, bdate, relationship "
	    			+ "FROM dependent "
	    			+ "WHERE essn = " + ssn;
	
	    Statement statement = c.createStatement();
	    ResultSet result = statement.executeQuery(query);
	
	    tempString += "New Employee Dependent Info\n\n";
	
	    while (result.next()) {
		    tempString += "Essn : "  + result.getString(1) + "\n";
		    tempString += "Dependent_name : "  + result.getString(2) + "\n";
		    tempString += "Sex : "  + result.getString(3) + "\n";
		    tempString += "Bdate : "  + result.getString(4) + "\n";
		    tempString += "Relationship : "  + result.getString(5) + "\n\n";
	    }
	
	    System.out.println(tempString);

    }
    

}


// added OJDBC-Full to project library
// added lib to project library