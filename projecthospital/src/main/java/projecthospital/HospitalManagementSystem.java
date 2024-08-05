package projecthospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
private static final String url = "jdbc:mysql://localhost:3306/hospital";
private static final String  username = "root";
private static final String  password = "root";

public static void main(String args[]) throws SQLException {
Scanner scanner = new Scanner(System.in);
Connection connection = DriverManager.getConnection(url,username,password);
patients patient = new patients(connection,scanner);
doctor Doctor = new doctor(connection); 
while(true) {
	System.out.println("HOSPITAL MANGEMENT SYSTEM");
	System.out.println("1.Add Patient");
	System.out.println("2.View Patient");
	System.out.println("3.View Doctor");
	System.out.println("4.Book Appointment");
	System.out.println("5.Exit");
	System.out.println("Enter your choice");
	int choice = scanner.nextInt();
	switch(choice) {
	case 1:
		patient.addPatient();
		System.out.println("");
	break;
	case 2:
		 patient.viewPatient();
		 System.out.println("");
		 break;
	case 3 :
  Doctor.viewdoctor();
  System.out.println("");
  break;
	case 4: 
		bookAppointment(patient, Doctor, connection, scanner);
	break;
	case 5:
		 return;
		 
		 default:
			 System.out.println("Enter valid choice");
	break;
	}
			}
}
public static void bookAppointment(patients patient , doctor Doctor,Connection connection,Scanner scanner) {
	System.out.println("Enter Patient Id:");
	int patientId = scanner.nextInt();
	System.out.println("Enter Doctor Id:");
	int doctorId = scanner.nextInt();
	System.out.print("Enter Appointment Date(YYYY-MM-DD):");
	String appointmentDate = scanner.next();
	if(patient.getpatientById(patientId) && Doctor.getdoctorById(doctorId)){
		if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
			
		
	String AppointmentQuery = "INSERT INTO appointment(patient_Id,doctor_Id,appointment_date) VALUES(?,?,?)";
try {
	
		PreparedStatement preparedstatement = connection.prepareStatement(AppointmentQuery);
		preparedstatement.setInt(1, patientId);
		preparedstatement.setInt(2, doctorId);
		preparedstatement.setString(3, appointmentDate);
		int rowsaffected = preparedstatement.executeUpdate();
		if(rowsaffected>0) {
			System.out.println("APPOINTMENT BOOKED");
		}
		else {
			System.out.println("Failed to book appointment");
		}
		}
catch(SQLException e) {
	e.printStackTrace();
}

	
	}else {
		System.out.println("Either doctor or patient doesn't exist!!!!");
	}
	
	
}
 
}
 
public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection) {
	String appointmentquery = "SELECT COUNT(*) FROM appointment where doctor_id = ? AND appointment_Date = ?";
	try {
		PreparedStatement preparedstatement = connection.prepareStatement(appointmentquery);
		preparedstatement.setInt(1, doctorId);
		preparedstatement.setString(2, appointmentDate);
	ResultSet rs = preparedstatement.executeQuery();
	if(rs.next())
	{
		int count = rs.getInt(1);
		if(count == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	}	

catch(SQLException e) {
	e.printStackTrace();
}
	
	return false;
}
}
