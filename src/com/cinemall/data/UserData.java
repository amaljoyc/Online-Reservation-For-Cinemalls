package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserData {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = "cinimall";

	public UserData() {
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

	public boolean getAccess(String name) {
		boolean ac = false;
		int val=0;
		try {

			preparedStatement = connect.prepareStatement("select access from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				val = resultSet.getInt("access");
			if(val>6)
				ac=true;
		} catch (Exception e) {
			System.out.print(e);

		}
		return ac;

	}

	public String getUserName(String id) {
		String ac = null;
		try {

			preparedStatement = connect.prepareStatement("select name from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				ac = resultSet.getString("name");
		} catch (Exception e) {
			System.out.print(e);

		}
		return ac;
	}

	public long getPhoneNumber(final String id) {
		long phoneNumber = 0;
		try {

			preparedStatement = connect.prepareStatement("select ph from " + dB
					+ ".USER where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				phoneNumber = resultSet.getLong("ph");
		} catch (Exception e) {
			System.out.print(e);

		}
		return phoneNumber;
	}

	public String getAddress(final String id) {
		String address = null;
		try {

			preparedStatement = connect.prepareStatement("select addr from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				address = resultSet.getString("addr");
		} catch (Exception e) {
			System.out.print(e);

		}
		return address;
	}

	public String getEmail(final String id) {
		String email = null;
		try {

			preparedStatement = connect.prepareStatement("select email from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				email = resultSet.getString("email");
		} catch (Exception e) {
			System.out.print(e);

		}
		return email;
	}

/**
 * Function tocheckif a user nameexistsin the database or not.
 * @param name the name to check
 * @return if the name exists the function returns true.
 */
	public boolean ifExists(String name) {
		boolean Exist = true;
		try {

			preparedStatement = connect.prepareStatement("select * from " + dB
					+ ".USER where id=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				Exist = false;
		} catch (Exception e) {
			System.out.print(e);

		}
		return Exist;
	}

	public boolean checkPassword(String pass, String name) {
		boolean val = false;
		try {
			preparedStatement = connect.prepareStatement("select pass from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			String rpas = resultSet.getString("pass");
			if (rpas.equals(pass))
				val = true;
		} catch (Exception e) {
			System.out.print(e);

		}
		return val;
	}

	public void addNewUser(String uname, String name, String pass,
			String email, String add, long ph) {
		try {
			preparedStatement = connect.prepareStatement("insert into " + dB
					+ ".USER values (?,?,?,?,?,?,default)");
			preparedStatement.setString(1, uname);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, add);
			preparedStatement.setLong(5, ph);
			preparedStatement.setString(6, email);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}
	}

	public ArrayList<String> getAllUsers() {
		ArrayList<String> userList = new ArrayList<String>();
		try {
			preparedStatement = connect.prepareStatement("select id from " + dB
					+ ".USER");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				userList.add(resultSet.getString("id"));
		} catch (Exception e) {
			System.out.print(e);

		}
		return userList;

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

	public void setPassword(String id, String newpass) {
		try {
			preparedStatement = connect.prepareStatement("update " + dB
					+ ".USER set pass=? where id=?");
			preparedStatement.setString(1, newpass);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}

	}

	public void setEmailId(String id, String email) {
		try {
			preparedStatement = connect.prepareStatement("update " + dB
					+ ".USER set email=? where id=?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}

	}

	public void setAddress(String id, String addr) {
		try {
			preparedStatement = connect.prepareStatement("update " + dB
					+ ".USER set addr=? where id=?");
			preparedStatement.setString(1, addr);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}

	}

	public void setPhoneNo(String id, long parseLong) {
		try {
			preparedStatement = connect.prepareStatement("update " + dB
					+ ".USER set ph=? where id=?");
			preparedStatement.setLong(1, parseLong);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}

	}

	public void setAccess(String id, int acc) {
		try {
		preparedStatement = connect.prepareStatement("update " + dB
				+ ".USER set access=? where id=?");
		preparedStatement.setInt(1, acc);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}
		
	}

	public boolean adminAccess(String name) {
		boolean ac = false;
		int val=0;
		try {

			preparedStatement = connect.prepareStatement("select access from "
					+ dB + ".USER where id=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				val = resultSet.getInt("access");
			if(val>8)
				ac=true;
		} catch (Exception e) {
			System.out.print(e);

		}
		return ac;
	}

	public void addNewUser(String uname, String name, String pass,
			String email, String add, long ph,int accLv) {
		try {
			preparedStatement = connect.prepareStatement("insert into " + dB
					+ ".USER values (?,?,?,?,?,?,?)");
			preparedStatement.setString(1, uname);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, add);
			preparedStatement.setLong(5, ph);
			preparedStatement.setString(6, email);
			preparedStatement.setLong(7, accLv);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.print(e);

		}
	}
}
