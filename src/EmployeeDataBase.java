

import java.sql.*;
import javax.swing.*;

public class EmployeeDataBase {

	public static Connection ConnectDB()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection
					("jdbc:sqlite:C:\\Users\\mfaro\\eclipse\\EmployeeDataBase\\Employee.db");
					JOptionPane.showMessageDialog(null,"Connection sucessfull");
					return conn;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Connection Error");
			return null;
		}
	}
}
