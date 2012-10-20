package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class AdminData {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = "cinimall";

	public AdminData() {
		try {
			Properties ht = new Properties();
			String name = null, pass = null;
			FileInputStream fin = null;
			// Try to open phonebook.dat file.
			try {
				fin = new FileInputStream("data.properties");
			} catch (FileNotFoundException e) {
			}
			try {
				if (fin != null) {
					ht.load(fin);
					dB = (String) ht.get("dbname");
					name = (String) ht.get("dbuser");
					pass = (String) ht.get("dbpass");
					fin.close();
				}
			} catch (IOException e) {
				System.out.println("Error reading file.");
			}
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/"
					+ dB + "?user=" + name + "&password=" + pass);
			statement = connect.createStatement();
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public ArrayList<String> getSizeInfo() {
		ArrayList<String> info = new ArrayList<String>();
		String row="";
		try {

			preparedStatement = connect
					.prepareStatement("SELECT table_name, table_rows, data_length, index_length,"
							+ "round(((data_length + index_length) / 1024 / 1024),2) \"Size in MB\""
							+ "FROM information_schema.TABLES WHERE table_schema = ?");
			preparedStatement.setString(1, dB);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				row += resultSet.getString("table_name")+";"+resultSet.getInt("table_rows")+";"+resultSet.getInt("data_length")+";"+resultSet.getInt("index_length")+";"+resultSet.getDouble("Size in MB")+";";
				info.add(row);
				row="";
				}
		} catch (Exception e) {
			System.out.print(e);

		}
		return info;
	}

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
