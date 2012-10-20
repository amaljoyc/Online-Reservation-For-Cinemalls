package com.cinemall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class BookingData {

	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String dB = null;

	public BookingData() {
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

	public int makResrv(String id, int showid, String clas, Date bdate,
			int notic, double f, String status) {
		int bid = 0;
		try {
			preparedStatement = connect.prepareStatement("insert into " + dB
					+ ".BOOKING values(default,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, id);
			preparedStatement.setInt(2, showid);
			preparedStatement.setDate(3, bdate);
			preparedStatement.setString(4, clas);
			preparedStatement.setDouble(5, notic);
			preparedStatement.setDouble(6, f);
			preparedStatement.setString(7, status);
			preparedStatement.executeUpdate();
			preparedStatement = connect
					.prepareStatement("SELECT max(bid) FROM " + dB
							+ ".BOOKING where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				bid = resultSet.getInt("max(bid)");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bid;
	}

	public boolean passCheck(String cno, String pass) {
		boolean check = false;
		try {
			preparedStatement = connect.prepareStatement("Select * from " + dB
					+ ".accounts where cardno=?");
			preparedStatement.setString(1, cno);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			String res = resultSet.getString("cpass");
			if (res.equals(pass))
				check = true;

		} catch (Exception e) {
			System.out.print(e);
		} finally {

		}
		return check;
	}

	public LinkedList<Integer> showResrv(String id) {
		LinkedList<Integer> flist = new LinkedList<Integer>();
		try {
			preparedStatement = connect.prepareStatement("select bid from "
					+ dB + ".BOOKING where id=?");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				flist.add(resultSet.getInt("bid"));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return flist;
	}

	public List<Integer> getAllBooking(String userid) {
		List<Integer> bookingList = new LinkedList<Integer>();
		try {
			preparedStatement = connect.prepareStatement("select bid from "
					+ dB + ".BOOKING where id = ?");
			preparedStatement.setString(1, userid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				bookingList.add(resultSet.getInt("bid"));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bookingList;

	}

	public Integer getShowid(int bid) {
		Integer showid = null;
		try {
			preparedStatement = connect.prepareStatement("select showid from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				showid = resultSet.getInt("showid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (showid);
	}

	public String getClass(int bid) {
		String cls = null;
		try {
			preparedStatement = connect.prepareStatement("select class from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				cls = resultSet.getString("class");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (cls);
	}

	public Date getDat(int bid) {
		Date date = null;
		try {
			preparedStatement = connect.prepareStatement("select bdate from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				date = resultSet.getDate("bdate");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (date);
	}

	public Double getAmt(int bid) {
		Double amt = null;
		try {
			preparedStatement = connect.prepareStatement("select bamt from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				amt = resultSet.getDouble("bamt");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (amt);
	}

	public String getStatus(int bid) {
		String stats = null;
		try {
			preparedStatement = connect.prepareStatement("select status from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				stats = resultSet.getString("status");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (stats);
	}

	public long calcAmt(int screen, String clas, int notic) {
		long amt = 0;
		try {
			preparedStatement = connect.prepareStatement("select rate from "
					+ dB + ".theatre where screen=? AND class=?");
			preparedStatement.setInt(1, screen);
			preparedStatement.setString(2, clas);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				amt = resultSet.getLong("rate");
		} catch (Exception e) {
			System.out.print(e);
		}
		amt *= notic;
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

	public void putSeat(String seatno, int bid) {
		try {
			preparedStatement = connect.prepareStatement("insert into " + dB
					+ ".SEATS values(?,?)");
			preparedStatement.setInt(1, Integer.parseInt(seatno));
			preparedStatement.setInt(2, bid);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getNumberOfSeats(int bid) {
		int seats = 0;
		try {
			preparedStatement = connect.prepareStatement("select notick from "
					+ dB + ".BOOKING where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				seats = resultSet.getInt("notick");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (seats);
	}

	public int getCountOfSeatsFor(int bid) {
		int seats = 0;
		try {
			preparedStatement = connect
					.prepareStatement("SELECT count(seatno) cno FROM " + dB
							+ ".seats where bid=?");
			preparedStatement.setInt(1, bid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				seats = resultSet.getInt("cno");
		} catch (Exception e) {
			System.out.print(e);
		}
		return (seats);

	}
	
	public boolean bookedForSameBId(int bid,int seatno){
		boolean val = false;
		try {
			preparedStatement = connect.prepareStatement("SELECT seatno FROM "
					+ dB + ".SEATS WHERE seatno=? AND bid=?");
			preparedStatement.setInt(2, bid);
			preparedStatement.setInt(1,seatno);
			resultSet = preparedStatement.executeQuery();
			//System.out.print(1);
			if (resultSet.next())
				val=true;
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}

	public boolean isSeatAloted(int seatno,Date dat, String clas, int screen,
			String show) {
		boolean val=false;
		try {
			preparedStatement = connect.prepareStatement("SELECT seatno FROM "
					+ dB + ".SEATS WHERE seatno=? AND bid in" + "(SELECT bid FROM " + dB
					+ ".booking WHERE class=? And showid in ("
					+ "SELECT showid FROM " + dB
					+ ".show WHERE date=? And screen=? and time=?))");
			preparedStatement.setInt(4, screen);
			preparedStatement.setString(2, clas);
			preparedStatement.setDate(3, dat);
			preparedStatement.setString(5,show);
			preparedStatement.setInt(1,seatno);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				val=true;
		} catch (Exception e) {
			System.out.print(e);
		}
		return val;
	}
	
	public int numberOfSeats(String clas,int screen){
		int nosets=-1;
		try {
			preparedStatement = connect.prepareStatement("select noseats from "
					+ dB + ".theatre where screen=? AND class=?");
			preparedStatement.setInt(1, screen);
			preparedStatement.setString(2, clas);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				nosets = resultSet.getInt("noseats");
		} catch (Exception e) {
			System.out.print(e);
		}
		return nosets;
	}

}
