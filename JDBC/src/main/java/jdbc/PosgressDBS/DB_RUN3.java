package jdbc.PosgressDBS;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.stream.events.StartElement;

/**
 * Hello world!
 *
 */
public class DB_RUN3 {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		/*
		 * connection string: 1. jdbc, 2.vendor name for example: oracle, mysql,
		 * mariadb, progress 3. sub type:driver type 4. host name 5.port name 6.SID /
		 * Service
		 * 
		 */
		// String url = "jdbc:oracle:thin:@YOUR-EC2-HOSTNAME-GOES-HERE:1521:xe";

		String url = "jdbc:oracle:thin:@ec2-18-207-188-95.compute-1.amazonaws.com:1521:xe";

		// String url = "jdbc:oracle:thin:@localhost:49161:xe";
		String user = "hr"; // or your username
		String password = "hr";// or your password
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = st.executeQuery("SELECT * FROM JOBS");

		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();

		rs.afterLast();

		System.out.println("PREVIOUS: ");
		while (rs.previous()) {
			for (int i = 1; i <= colCount; i++) {
				// System.out.print(rs.getObject(i) + " ");
				System.out.printf("%25s	", rs.getObject(i));
			}
			System.out.println();
		}

		
		System.out.println("Absolute: ");
		for (int i = 1; i <= colCount; i++) {
			rs.absolute(i);
			for (int j = 1; j <= colCount; j++) {
				System.out.printf("%25s	", rs.getObject(j));
			}
			System.out.println();
		}

		rs.close();
		st.close();
		conn.close();

	}
}
