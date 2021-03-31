package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.TimeZone;



public class ConnectionClass {
	
	Connection conn;
	public Connection getConnection(){
		

		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

//			conn = DriverManager.getConnection("jdbc:mysql://proph.000webhostapp.com/id7421450_proph01?"
//			+"user=id7421450_proph&password=abcd1234");
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/femiolastoredb?serverTimezone="+ TimeZone.getDefault().getID(), "root", "");

//			conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2/femiolastoredb?serverTimezone="+ TimeZone.getDefault().getID(), "root", "");


		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return conn;
	}

}
