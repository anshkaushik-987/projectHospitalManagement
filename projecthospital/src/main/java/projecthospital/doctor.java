package projecthospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class doctor {

private Connection connection;
public  doctor(Connection connection) {
 	this.connection = connection;
	 
} 
 public void viewdoctor() {
	 String query = "select * from  doctor";
	 
	 try {
		 PreparedStatement ps = connection.prepareStatement(query);
	ResultSet rs = 	 ps.executeQuery();
	System.out.println("DOCTOR'S");
	 System.out.println("+---------------+---------------+----------------+");
	 System.out.println("| Doctor   Id   |Doctor name    |Specialization  |");
	 System.out.println("+---------------+---------------+----------------+");
	 while(rs.next())
	 {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		 String specialization = rs.getString("specialization");
	 System.out.printf("|%-15s|%-15s|%-15s |\n",id,name,specialization);
	 System.out.println("+---------------+---------------+----------------+");
	 }
	 }
	 catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 public  boolean getdoctorById(int id) {
	 String query = "Select * from  doctor where id = ?";
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
