package com.pgm.util.report;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static Connection getConexao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "Paulo13");
			return DriverManager.getConnection("jdbc:mysql://localhost/pgm_oficios", props);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
