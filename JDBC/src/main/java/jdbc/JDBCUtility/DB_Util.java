package jdbc.JDBCUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_Util {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultset;

	public static void main(String[] args) throws Exception {

		// Establish Connection
		String query = "SELECT * FROM EMPLOYEES";

		createConnection();

		int rowCount = getRowCount(query);
		System.out.println(rowCount);

		List<Map<String, Object>> resultList = getQueryResult(query);
		System.out.println(resultList);

		// myResult.goToRow(2).getValueByColumnName("my name") --> value

		System.out.println(resultList.get(2).get("FIRST_NAME"));

		// Create Statement Run Query to store result to collection object

		// get row count

		// Close The connection
		destroyConnection();

	}

	public static void createConnection() {

		String url = ConfigurationReader.getProperty("connectionString"); // "jdbc:oracle:thin:@localhost:1521:xe";
		String user = ConfigurationReader.getProperty("DB_user");// "HR"; // or your username
		String password = ConfigurationReader.getProperty("DB_pass");// "HR";// or your password

		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// store the resultset is collection of some kind so we can directly work with
	// myResult.goToRow(2).getValueByColumnName("my name") --> value

	// List of rows ---> Map of colName-value
	/// List< Map<String, Object> >

	public static List<Map<String, Object>> getQueryResult(String query) {

		List<Map<String, Object>> rowList = new ArrayList<>();

		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultset = statement.executeQuery(query);

			ResultSetMetaData rsmd = resultset.getMetaData();
			while (resultset.next()) {

				Map<String, Object> colNameValueMap = new HashMap<>();
				//
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					//
					// //System.out.println( "ColumnName : " + rsmd.getColumnName(i));
					// //System.out.println( "value : " + rs.getObject(i) );
					colNameValueMap.put(rsmd.getColumnName(i), resultset.getObject(i));
					//
				}

				rowList.add(colNameValueMap);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rowList;

	}

	public static void destroyConnection() {

		try {

			resultset.close();
			statement.close();
			connection.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getRowCount(String query) throws Exception {

		int rowCount = 0;

		//// when using Try with resources
		/// resource declaration and creation should be in same line ;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultset = statement.executeQuery(query);

			resultset.last();
			rowCount = resultset.getRow();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowCount;

	}

}
