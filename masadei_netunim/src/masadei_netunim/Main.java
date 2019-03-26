package masadei_netunim;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {
	public static void main(String[] args) throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date rs = null;
		Sql_queue sql = new Sql_queue();
		rs = sql.get_now();
//		String temp = sql.select_all("Appointment");
//		System.out.println(temp);
//		sql.insert_appointment("543256754", "302537394", rs);

//		System.out.println(dateFormat.format(rs));
//		String temp = sql.select_all("Doctors");
//		System.out.println(temp);
//		sql.insert_appointment("543256754", "212132432", rs);
//		sql.insert_appointment("543256754", "212132432", rs);
//		sql.insert_doctor("212132432" , "heart surgen" , "david" , 72334.34);
//		sql.insert_patient("543256754", "osnat");
/*		sql.insert_appointment("1", "2", appointment_time);
		rs = sql.select_all("Patients");
			System.out.println(rs);
*/		
	}

}