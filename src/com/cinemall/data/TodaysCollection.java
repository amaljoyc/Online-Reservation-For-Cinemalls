package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TodaysCollection {
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = null;
	
	public TodaysCollection() {
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
   public int totalAmt(String dat){
	   int res=0;
	   try{
		    preparedStatement = connect.prepareStatement("Select SUM(bamt) from " + dB
					+ ".booking where bdate=?");
			preparedStatement.setString(1, dat);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			res = resultSet.getInt("SUM(bamt)");
		}
	   catch (Exception e) {
			System.out.print(e);
        }
       return res;
    }
   public String convert(){
	   String s=null;
	   try{
	       DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	       Date today = Calendar.getInstance().getTime();
	       s = df.format(today);
	       System.out.println("Report Date: " + s);
	   }
	   catch (Exception e) {
			System.out.print(e);
       }
	   return s;
   }
   public List<Integer> getFilmid(String dat) {
		List<Integer> filmList = new LinkedList<Integer>();
		try {
			preparedStatement = connect.prepareStatement("select distinct filmid from "
					+ dB + ".SHOW where showid in "
					+ "(select showid from " + dB + ".booking where bdate=?)");
			preparedStatement.setString(1, dat);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				filmList.add(resultSet.getInt("filmid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filmList;
	}
   public int getAmt(int fid,String dat){
	   int amt=0;
	   try{
			preparedStatement = connect.prepareStatement("select SUM(bamt) from "
					+ dB + ".booking where bdate=? AND showid in "
					+ "(select showid from " + dB + ".show where filmid=?)");
            preparedStatement.setString(1, dat);
            preparedStatement.setInt(2, fid);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			amt = resultSet.getInt("SUM(bamt)");
		}
	   catch (Exception e) {
			System.out.print(e);
        }
       return amt;
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
