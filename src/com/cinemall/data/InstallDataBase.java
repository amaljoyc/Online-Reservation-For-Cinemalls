package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import org.apache.catalina.User;

@SuppressWarnings("unused")

/**
 * This class is used for installig the databse and creatingsome of the default 
 * data for few tables.After this the class becomes useless.  
 */
public class InstallDataBase {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB;
	Properties properties;

	/**
	 * This is the constuctor of the {@link InstallDataBase} class.
	 * This constructor is used to connect to the MySql server with root passsword.
	 * Only root has the permission to do some of these tasks. 
	 * @param pass the password of the root user.
	 */
	public InstallDataBase(String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/mysql"
							+ "?user=root&password=" + pass);
			statement = connect.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is the mos prominem\nt function of the class {@link InstallDataBase}.
	 * This function creates all the tables required for the working of the site. 
	 * This function also creates a 'data.properties' file which stores the name of the database,Username and password needed to access thisdatabase.
	 *   
	 * @param dbuser a new MySql user created only to access data from this databse. 
	 * @param dbpass password for this new user.
	 * @param dbname This the name of the new database.This databse is created inside this function.If the database allredy exist the err string will contain an error message.
	 * @return err This string will contain all the error messages if something went wrong
	 * else this string will be 'success'. 
	 */
	public String install(String dbuser, String dbpass,String dbname) {
		String err = "success";
		dB=dbname;
		try {

			properties = new Properties();
			properties.put("dbname", dB);
			properties.put("dbuser", dbuser);
			properties.put("dbpass", dbpass);
			FileOutputStream fout = new FileOutputStream("data.properties");
			properties.store(fout, "Try");
			fout.close();
			try {
				statement = connect.createStatement();
				statement.executeUpdate("CREATE DATABASE " + dB);
			} catch (Exception e) {
				err="database alredy exists.";
throw e;
			}
			try {
				preparedStatement = connect
						.prepareStatement("CREATE USER ?@? IDENTIFIED BY ?");
				preparedStatement.setString(1, dbuser);
				preparedStatement.setString(2, "localhost");
				preparedStatement.setString(3, dbpass);
				preparedStatement.executeUpdate();
			} catch (Exception e) {
				err="user alredy exist";
				throw e;
			}
			try {
				preparedStatement = connect
						.prepareStatement("GRANT ALL PRIVILEGES ON " + dB
								+ ".* TO ?@?" + "IDENTIFIED BY ?");
				preparedStatement.setString(1, dbuser);
				preparedStatement.setString(2, "localhost");
				preparedStatement.setString(3, dbpass);
				preparedStatement.executeUpdate();
			} catch (Exception e) {
				err="error";
				throw e;
			}

			try {
				/*
				 * CREATE TABLE `cinimall`.`User` ( `id` VARCHAR(30) NOT NULL,
				 * `pass` VARCHAR(45) NOT NULL, `name` VARCHAR(50) NOT NULL,
				 * `addr` VARCHAR(60) NOT NULL, `ph` INTEGER(10) UNSIGNED NOT
				 * NULL, `email` VARCHAR(45) NOT NULL, `access` BOOL NOT NULL
				 * DEFAULT 0, PRIMARY KEY (`id`) ) ENGINE = InnoDB;
				 */
				preparedStatement = connect.prepareStatement("CREATE TABLE "
						+ dB + ".USER (`id` VARCHAR(30) NOT NULL  PRIMARY KEY,"
						+ "`pass` VARCHAR(45) NOT NULL,"
						+ "`name` VARCHAR(45) NOT NULL,"
						+ "`addr` VARCHAR(60) NOT NULL,"
						+ "`ph` BIGINT UNSIGNED NOT NULL,"
						+ "`email` VARCHAR(45) NOT NULL unique,"
						+ "`access` INTEGER UNSIGNED NOT NULL DEFAULT 0) ENGINE = InnoDB");
				preparedStatement.executeUpdate();

				/*
				 * CREATE TABLE `cinimall`.`MOVIE` ( `filmid` INTEGER UNSIGNED
				 * NOT NULL AUTO_INCREMENT,`fname` VARCHAR(45) NOT NULL PRIMARY
				 * KEY (`filmid`))ENGINE = InnoDB;
				 */
				preparedStatement = connect
						.prepareStatement("CREATE TABLE "
								+ dB
								+ ".`MOVIE` (`filmid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
								+ "`fname` VARCHAR(45) NOT NULL,"
								+ "PRIMARY KEY (`filmid`)" + ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();

				preparedStatement = connect
						.prepareStatement("CREATE TABLE "
								+ dB
								+ ".`THEATRE` ( `screen` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
								+ "`class` VARCHAR(3) NOT NULL,"
								+ "`noseats` INTEGER UNSIGNED NOT NULL,"
								+ "`rate` DOUBLE(5,2) NOT NULL,"
								+ "PRIMARY KEY (`screen`, `class`)"
								+ ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();

				/*
				 * CREATE TABLE `cinimall`.`Show` ( `showid` INTEGER UNSIGNED
				 * NOT NULL AUTO_INCREMENT, `date` DATETIME NOT NULL, `screen`
				 * INTEGER UNSIGNED NOT NULL, `filmid` INTEGER UNSIGNED NOT
				 * NULL, `time` VARCHAR(10) NOT NULL, PRIMARY KEY (`showid`),
				 * CONSTRAINT `FK_Show_1` FOREIGN KEY `FK_Show_1` (`filmid`)
				 * REFERENCES `MOVIE` (`filmid`) ON DELETE RESTRICT ON UPDATE
				 * RESTRICT )ENGINE = InnoDB;
				 */
				preparedStatement = connect
						.prepareStatement("CREATE TABLE "
								+ dB
								+ ".`SHOW` (`showid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
								+ "`screen` INTEGER UNSIGNED NOT NULL,"
								+ " `date` DATETIME NOT NULL,"
								+ "`filmid` INTEGER UNSIGNED NOT NULL,"
								+ "`time` VARCHAR(10) NOT NULL,"
								+ "PRIMARY KEY (`showid`),"
								+ "CONSTRAINT `FK_Show_1` FOREIGN KEY `FK_Show_1` (`filmid`)"
								+ "REFERENCES `MOVIE` (`filmid`)"
								+ "ON DELETE RESTRICT "
								+ " ON UPDATE RESTRICT,"
								+ "CONSTRAINT `FK_Show_2` FOREIGN KEY `FK_Show_2` (`screen`)"
								+ "REFERENCES `THEATRE` (`screen`)"
								+ "ON DELETE RESTRICT " + " ON UPDATE RESTRICT"
								+ ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();
				/*
				 * CREATE TABLE `cinimall`.`BOOKING` ( `bid` INTEGER UNSIGNED
				 * NOT NULL AUTO_INCREMENT, `id` VARCHAR(45) NOT NULL, `showid`
				 * INTEGER UNSIGNED NOT NULL, `class` CHAR NOT NULL, `bdate`
				 * DATETIME NOT NULL, `bamt` FLOAT(5,2) NOT NULL, `status`
				 * VARCHAR(45) NOT NULL, PRIMARY KEY (`bid`), CONSTRAINT
				 * `FK_BOOKING_1` FOREIGN KEY `FK_BOOKING_1` (`id`) REFERENCES
				 * `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
				 * CONSTRAINT `FK_BOOKING_2` FOREIGN KEY `FK_BOOKING_2`
				 * (`showid`) REFERENCES `show` (`showid`) ON DELETE RESTRICT ON
				 * UPDATE RESTRICT ) ENGINE = InnoDB;
				 */
				preparedStatement = connect
						.prepareStatement("CREATE TABLE "
								+ dB
								+ ".`BOOKING` ( `bid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,"
								+ "`id` VARCHAR(45) NOT NULL,"
								+ " `showid` INTEGER UNSIGNED NOT NULL,"
								+ " `bdate` DATETIME NOT NULL,"
								+ "`class` VARCHAR(3) NOT NULL,"
								+ "`notick` INTEGER UNSIGNED NOT NULL,"
								+ "`bamt` DOUBLE(5,2) NOT NULL,"
								+ "  `status` VARCHAR(10) NOT NULL,"
								+ "PRIMARY KEY (`bid`),"
								+ "CONSTRAINT `FK_BOOKING_1` FOREIGN KEY `FK_BOOKING_1` (`id`)"
								+ "REFERENCES `user` (`id`)"
								+ " ON DELETE RESTRICT"
								+ " ON UPDATE RESTRICT,"
								+ "CONSTRAINT `FK_BOOKING_2` FOREIGN KEY `FK_BOOKING_2` (`showid`)"
								+ "REFERENCES `show` (`showid`)"
								+ " ON DELETE RESTRICT" + " ON UPDATE RESTRICT"
								// +
								// ",CONSTRAINT `FK_BOOKING_3` FOREIGN KEY `FK_BOOKING_3` (`class`)"
								// + "REFERENCES `THEATRE` (`class`)"
								// + " ON DELETE RESTRICT" +
								// " ON UPDATE RESTRICT"
								+ ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();

				preparedStatement = connect.prepareStatement("CREATE TABLE "
						+ dB
						+ ".`ACCOUNTS` ( `cardno` INTEGER UNSIGNED NOT NULL,"
						+ "`cpass` VARCHAR(10) NOT NULL,"
						+ " `balance` DOUBLE(6,2) NOT NULL,"
						+ "PRIMARY KEY (`cardno`)" + ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();
				/*
				 * CREATE TABLE `cinimall`.`SEATS` ( `seatno` INTEGER UNSIGNED
				 * NOT NULL AUTO_INCREMENT, `bid` INTEGER UNSIGNED NOT NULL,
				 * PRIMARY KEY (`seatno`),CONSTRAINT `FK_SEATS_1` FOREIGN KEY
				 * `FK_SEATS_1` (`bid`) REFERENCES `booking` (`bid`) ON DELETE
				 * RESTRICT ON UPDATE RESTRICT ) ENGINE = InnoDB;
				 */
				preparedStatement = connect
						.prepareStatement("CREATE TABLE "
								+ dB
								+ ".`SEATS` (`seatno` INTEGER UNSIGNED NOT NULL,"
								+ "`bid` INTEGER UNSIGNED NOT NULL,"
								+ "CONSTRAINT `FK_SEATS_1` FOREIGN KEY `FK_SEATS_1` (`bid`)"
								+ "REFERENCES `booking` (`bid`)"
								+ " ON DELETE RESTRICT" + " ON UPDATE RESTRICT"
								+ ")ENGINE = InnoDB");
				preparedStatement.executeUpdate();

				setupTheater();
				UserData d=new UserData();
				d.addNewUser("admin", "admin", "pass", "cinimall7@gmail.com", "nill", 00000000000,9);
				d.close();

			} catch (Exception e) {
				err=err+"eRROR IN TABLE CREATION";
				throw e;
			}
		} catch (Exception e) {
			err=err+e;
			e.printStackTrace();
		} finally {
		}
		return err;
	}
/**
 * The function creates a set of values in the table THEATRE. 
 * This is a  private function and is called inside {@link InstallDataBase}->install function only.
 */
	private void setupTheater() {
		for (int i = 1; i <= 5; i++) {
			for (char k = 'A'; k <= 'C'; k++) {
				try {
					preparedStatement = connect.prepareStatement("INSERT INTO "
							+ dB + ".`THEATRE` VALUES(?,?,?,?)");
					preparedStatement.setInt(1, i);
					preparedStatement.setString(2, Character.toString(k));
					preparedStatement.setInt(3, 50);
					preparedStatement.setDouble(4, 50.00);
					int j = preparedStatement.executeUpdate();
					if (j != 0) {

					} else {

					}
				} catch (Exception e) {
					System.out.print(e);
					e.printStackTrace();

				}
			}
		}
	}
/**
 * This function is similler to the delete operator in C++.
 * It cleans up after the {@link InstallDataBase} is used.
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
