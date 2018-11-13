package jdbc.PosgressDBS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class DB_RUN_TryWithResources {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		// String url = "jdbc:oracle:thin:@YOUR-EC2-HOSTNAME-GOES-HERE:1521:xe";
		String url = "room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com/room_reservation_qa?:5432";
		String user = "qa_user"; // or your username
		String password = "Cybertek11!";// or your password

		try (Connection conn = DriverManager.getConnection(url, user, password);
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st.executeQuery("SELECT * FROM USERS WHERE FIRSTNAME LIKE 'A%'")) {

			rs.next();
			System.out.println(rs.getObject(2));

			System.out.println("connected ");

		} catch (Exception e) {

			System.out.println("Hello Posgress :)");
		}

	}
}