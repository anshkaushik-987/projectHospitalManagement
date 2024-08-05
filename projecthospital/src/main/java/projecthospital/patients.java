package projecthospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patients {
private Connection connection;
private Scanner scanner;
public patients(Connection connection, Scanner scanner) {
 	this.connection = connection;
	this.scanner = scanner;
}
public void addPatient() 
{
  System.out.println("Enter Patient Name:");
  String name = scanner.next();
   System.out.println("Enter Patient Age : ");
   int age = scanner.nextInt();
   System.out.println("Enter Patient  Gender : ");
   String Gender = scanner.next();
   
   
   try {
	 String query = "insert into patients(name,age,gender)VALUES(?,?,?)" ;
	  PreparedStatement preparedStatement = connection.prepareStatement(query); 
	  preparedStatement.setString(1,  name);
	  preparedStatement.setInt(2, age);
	  preparedStatement.setString(3, Gender);
	  int affectedRows = preparedStatement.executeUpdate();
 
   if(affectedRows>0) {
	   System.out.println("PATIENT ADD SUCCESSFULY");
   }
   else {
	   System.out.println("Failed to add Patient");
   }
   
   }
   catch(SQLException e) {
	   e.printStackTrace();
   }
}
 public void viewPatient() {
	 String query = "select * from patients";
	 
	 try {
		 PreparedStatement ps = connection.prepareStatement(query);
	ResultSet rs = 	 ps.executeQuery();
	System.out.println("PATIENT");
	 System.out.println("+---------------+---------------+----------------+----------+");
	 System.out.println("| Patient   Id  |Name           |Age             | Gender   |");
	 System.out.println("+---------------+---------------+----------------+----------+");
	 while(rs.next())
	 {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int age = rs.getInt("age");
		String gender = rs.getString("Gender");
	 System.out.printf("|%-17s|%-12s|%-10s|%-14s\n",id,name,age,gender);
	 System.out.println("+---------------+---------------+----------------+----------+");

	 }
	 }
	 catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 public  boolean getpatientById(int id) {
	 String query = "Select * from patients where id = ?";
	 try {
		 PreparedStatement ps = connection.prepareStatement(query);
		 ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return true;
		}
		else {
			return false;
		}
	 }
	 catch(SQLException e) {
		 e.printStackTrace();
	 }
	return false;
 } 
}