package masadei_netunim;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Sql_queue {
	public String run_queue(String queue) throws Exception
	{
		String result = "";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_N?useSSL=false","root" , "Syg90@work");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queue);
		int numOfColumns = rs.getMetaData().getColumnCount();
		while (rs.next()){
			for (int col = 1; col <= numOfColumns; col++)
			{
				result= result + rs.getString(col) + " ";
			}
			result = result + "\n";
		}

		stmt.close();
		con.close();
		return result;
		
	}
	public String run_queue_update(String queue) throws Exception
	{
		String result = "";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_N?useSSL=false","root" , "Syg90@work");
		Statement stmt = con.createStatement();
		stmt.executeUpdate(queue);
		stmt.close();
		con.close();
		return result;
		
	}
	public void insert_doctor(String doctor_id , String doctor_type , String doctor_name , double salary)
	{
		String queue = "insert into Doctors values(\""+doctor_id +"\",\"" + doctor_type + "\" , \"" + doctor_name +"\" ,"+ salary +")";
		try {
			System.out.println(queue);
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void insert_patient(String patient_id , String patient_name)
	{
		String queue = "insert into Patients values(\""+patient_id +"\",\"" + patient_name + "\")";
		try {
			System.out.println(queue);
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void insert_appointment(String patient_id , String doctor_id , Date rs)
	{
		Timestamp dateDB = new java.sql.Timestamp(rs.getTime());
		System.out.println(dateDB);
		String queue = "insert into Appointment (`patient_id` , `doctor_id` , `appointment_time`)values(\""+patient_id +"\",\"" + doctor_id + "\",\"" + dateDB + "\")";
		try {
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void insert_queue(int appointment_id) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String actual_time = dateFormat.format(get_now());
		String queue = "insert into Queue values("+appointment_id +" , \"" + actual_time + "\")";
		System.out.println(queue);
		try {
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void insert_queue_summary(Date date , String doctor_id ,int num_of_patients)
	{
		String queue = "insert into Queue_Summary values(" + date + " , \"" + doctor_id + "\" , " + num_of_patients + ")";
		try {
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public String select_all(String table)
	{
		String rs = null;
		String queue = "select * from " + table;
		try {
			rs = run_queue(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return rs;
	}
	public Date get_now() throws ParseException
	{
		String rs = null;
		String queue = "SELECT CURRENT_TIMESTAMP";
	    SimpleDateFormat result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			rs = run_queue(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	    Date now=result.parse(rs);  
		return now;
	}
	
	public String q1(String doctor_id)
	{
		String rs = null;
		String queue = "select Patients.patient_id , Patients.patient_name , appointment_time " + 
				"from Appointment join Patients on " + 
				"Appointment.patient_id = Patients.patient_id " + 
				"where doctor_id = "+ doctor_id + " " + 
				"ORDER BY appointment_time ASC";
		try {
			rs = run_queue(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return rs;
	}
	
	public void q2_create_precedure()
	{
		String queue = "CREATE PROCEDURE `M_N.spqueue_patient_in` (in patient_id varchar(9)) BEGIN insert into Queue values((select appointment_id from Appointment where patient_id=@patient_id), CURRENT_TIMESTAMP);END";
		try {
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void q2(String patient_id)
	{
		String queue = "exec M_N.spqueue_patient_in";
		try {
			run_queue_update(queue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
