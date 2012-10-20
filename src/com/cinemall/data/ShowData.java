package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Properties;

/**
 * {@link ShowData} is a class containing all the function, that might be needed
 * to handle data related to a show.
 * 
 * @author Vignesh
 * 
 */
public class ShowData {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = null;

	/**
	 * Constructer of the class {@link ShowData}. Retrves the database name from
	 * the properties file. Also retrves the database username and password.
	 * Establishes connection to the database.
	 */
	public ShowData() {
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
	 * @param fn
	 * @return
	 */
	public int getFilmId(String fn) {
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
	 * returns the name of allthe movies in the data-base.
	 * 
	 * @return a {@link LinkedList} containing all the names of the movies.
	 */
	public LinkedList<String> getAllFilms() {
		LinkedList<String> flist = new LinkedList<String>();
		try {
			preparedStatement = connect.prepareStatement("select fname from "
					+ dB + ".MOVIE");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				flist.add(resultSet.getString("fname"));
		} catch (Exception e) {
			System.out.print(e);

		}
		return flist;
	}

	/**
	 * Check whether a show exists for agiven set of paremeters
	 * 
	 * @param cal
	 *            dae of show
	 * @param screen
	 *            screen
	 * @param time
	 *            time of show
	 * @return returns true if thre exists a show whith these values.
	 */
	public boolean showExists(Date cal, int screen, String time) {
		boolean val = false;
		try {

			preparedStatement = connect.prepareStatement("select showid from "
					+ dB + ".SHOW where screen=? AND date=? AND time=?");
			preparedStatement.setInt(1, screen);
			preparedStatement.setDate(2, cal);
			preparedStatement.setString(3, time);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				val = true;
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	/**
	 * 
	 * @param cal
	 * @param screen
	 * @param time
	 * @param filmid
	 */
	public void addShow(Date cal, int screen, String time, int filmid) {
		try {

			preparedStatement = connect.prepareStatement("INSERT INTO " + dB
					+ ".SHOW VALUES(default,?,?,?,?)");
			preparedStatement.setInt(1, screen);
			preparedStatement.setDate(2, cal);
			preparedStatement.setInt(3, filmid);
			preparedStatement.setString(4, time);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);
		}

	}

	/**
	 * 
	 * @param cal
	 * @param screen
	 * @param time
	 * @param filmid
	 */

	public void updateShow(Date cal, int screen, String time, int filmid) {
		try {

			preparedStatement = connect
					.prepareStatement("UPDATE "
							+ dB
							+ ".SHOW SET filmid=? where screen=? AND date=? AND time=?");
			preparedStatement.setInt(2, screen);
			preparedStatement.setDate(3, cal);
			preparedStatement.setInt(1, filmid);
			preparedStatement.setString(4, time);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);
		}

	}

	/**
	 * 
	 * @param screen
	 * @param clas
	 * @param option
	 * @param value
	 */
	public void theatreInfo(int screen, String clas, int option, String value) {
		try {
			if (option == 1) {
				float rate = Float.parseFloat(value);
				preparedStatement = connect.prepareStatement("UPDATE " + dB
						+ ".theatre SET rate=? where screen=? and class=?");
				preparedStatement.setFloat(1, rate);
				preparedStatement.setInt(2, screen);
				preparedStatement.setString(3, clas);
				preparedStatement.executeUpdate();
			} else if (option == 2) {
				int noseats = Integer.parseInt(value);
				preparedStatement = connect.prepareStatement("UPDATE " + dB
						+ ".theatre SET noseats=? where screen=? and class=?");
				preparedStatement.setInt(1, noseats);
				preparedStatement.setInt(2, screen);
				preparedStatement.setString(3, clas);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public String getScreenFor(Date shdate, int filmid) {
		String val = new String();
		try {

			preparedStatement = connect
					.prepareStatement("select DISTINCT screen from " + dB
							+ ".SHOW where date=? AND filmid=?");
			preparedStatement.setDate(1, shdate);
			preparedStatement.setInt(2, filmid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				val += resultSet.getString("screen") + ";";
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	public String getShowFor(Date shdate, int filmid, String screen) {
		String val = new String();
		try {

			preparedStatement = connect
					.prepareStatement("select DISTINCT time from " + dB
							+ ".SHOW where date=? AND filmid=? AND screen=?");
			preparedStatement.setDate(1, shdate);
			preparedStatement.setInt(2, filmid);
			preparedStatement.setString(3, screen);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				val += resultSet.getString("time") + ";";
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	public String getFilmsFor(Date shdate) {
		String val = new String();
		try {

			preparedStatement = connect.prepareStatement("select fname from "
					+ dB + ".MOVIE " + "where filmid in(select filmid from "
					+ dB + ".SHOW where date=?)");
			preparedStatement.setDate(1, shdate);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				val = val + resultSet.getString("fname") + ";";
				// System.out.println(1);
			}

		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	public boolean fimNotShown(String fname, Date shdate) {
		boolean val = true;
		try {

			preparedStatement = connect.prepareStatement("select showid from "
					+ dB + ".SHOW where date=? AND filmid in "
					+ "(select filmid from " + dB + ".movie where fname=?)");
			preparedStatement.setDate(1, shdate);
			preparedStatement.setString(2, fname);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				val = false;
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	@SuppressWarnings("unused")
	private String getFilmName(int int1) {
		String fname = null;
		try {

			preparedStatement = connect.prepareStatement("select fname from "
					+ dB + ".MOVIE where filmid=?");
			preparedStatement.setInt(1, int1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				fname = resultSet.getString("fname");
		} catch (Exception e) {
			System.out.print(e);
		}
		return fname;
	}

	/**
	 * This function is similler to the delete operator in C++. It cleans up
	 * after the {@link ShowData} is used. IT closes the data base conection,
	 * {@link Statement} object, {@link ResultSet} object are closed. Be
	 * carefull to use this function if this Class has been used, before you
	 * exit the page.
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

	public int getShowId(Date bdate, int screen, String fname, String show) {
		int showid=0;
		try {

			preparedStatement = connect.prepareStatement("select showid from "
					+ dB + ".SHOW where screen=? AND date=? AND time=? ");
			preparedStatement.setInt(1, screen);
			preparedStatement.setDate(2, bdate);
			preparedStatement.setString(3, show);
			//preparedStatement.setString(4, fname);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				showid = resultSet.getInt("showid");
		} catch (Exception e) {
			System.out.print(e);
		}
		return showid;
		
	}

}
