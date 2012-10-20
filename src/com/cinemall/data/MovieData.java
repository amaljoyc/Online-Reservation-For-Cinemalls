package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @author Vignesh
 *
 */
public class MovieData {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = null;
	
	/**
	 * Constructer of the class {@link MovieData}.
	 * Retrves the database name from the properties file.
	 * Also retrves the database username and password.
	 * Establishes connection to the database.
	 */
	public MovieData() {
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

	/**
	 * 
	 * @param fname
	 * @return
	 */
		public boolean addNewFilm(String fname) {
			boolean val = false;
			try {
				preparedStatement = connect.prepareStatement("INSERT INTO " + dB
						+ ".Movie VALUES(default,?)");
				preparedStatement.setString(1, fname);
				int i = preparedStatement.executeUpdate();
				if (i != 0) {
					val = true;
				} else {
					val = false;
				}
			} catch (Exception e) {
				System.out.print(e);
			}
			return val;
			
		}
		/**
		 * 
		 * @param fname
		 * @return
		 */
		public boolean filmNameExist(String fname) {
			boolean val=false;
			try {

				preparedStatement = connect.prepareStatement("select filmid from "
						+ dB + ".MOVIE where lower(fname)=lower(?)");
				preparedStatement.setString(1, fname);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next())
					val = true;
			} catch (Exception e) {
				System.out.print(e);
			}
			return val;
		}
		
		public String getFilmName(int fid){
			String fname = null;
			try {

				preparedStatement = connect.prepareStatement("select fname from "
						+ dB + ".MOVIE where filmid=?");
				preparedStatement.setLong(1, fid);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next())
					fname = resultSet.getString("fname");
			} catch (Exception e) {
				System.out.print(e);
			}
			return fname;
		}
		
		/**
		 * 
		 * @param fn
		 * @return
		 */
		public int getFilmId(String fn){
			int filmid = -1;
			try {

				preparedStatement = connect.prepareStatement("select filmid from "
						+ dB + ".MOVIE where fname=?");
				preparedStatement.setString(1, fn);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next())
					filmid = resultSet.getInt("filmid");
			} catch (Exception e) {
				System.out.print(e);
			}
			return filmid;
		}
		/**
		 * This function is similler to the delete operator in C++.
		 * It cleans up after the {@link MovieData} is used.
		 * IT closes the data base conection, {@link Statement} object, {@link ResultSet} object are closed.
		 * Be carefull to use this function if this Class has been used, before you exit the page.  
		 */
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
