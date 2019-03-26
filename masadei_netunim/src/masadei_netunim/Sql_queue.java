package masadei_netunim;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
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

	public void insert_queue(int appointment_id , Date actual_time)
	{
		String queue = "insert into Queue values("+appointment_id +" , " + actual_time + ")";
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
}
