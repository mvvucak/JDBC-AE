import java.sql.*;

import javax.swing.JOptionPane;

public class Booker {

	private String memFirstName, memLastName, courseName;
	private int memID, courseID;
	
	public Booker(DatabaseInteractor dint, String f, String l, String c)
	{
		//Initiliase member and course name variables.
		memFirstName = f;
		memLastName = l;
		courseName = c;
		
		//Get IDs for selected member and course.
		memID = fetchID(dint, true);
		courseID = fetchID(dint, false);
		
		//Get course capacity.
		String maxCapacityQuery = "SELECT capacity FROM course WHERE courseid=" + courseID+";";
		ResultSet rs = dint.selectQuery(maxCapacityQuery);
		int maxBookings = 0;
		try
		{
			while(rs.next())
			{
				maxBookings = rs.getInt("capacity");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + maxCapacityQuery);
		}
		
		//Get number of members booked on course.
		String currentCapacityQuery = "SELECT COUNT (memberid) AS bookings FROM membercourse WHERE courseid =" + courseID +";";
		rs = dint.selectQuery(currentCapacityQuery);
		int currentBookings = 0;
		try
		{
			while(rs.next())
			{
				currentBookings = rs.getInt("bookings");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + maxCapacityQuery);
		}
		
		//Get number of members with the same id on the same course (duplicates). 
		String duplicateQuery = "SELECT memberid, courseid FROM membercourse WHERE memberid="+ memID +" AND courseid=" + courseID+";";
		rs = dint.selectQuery(duplicateQuery);
		int check = 0;
		try
		{
			//For each row in the result set, increase duplicate checker by one.
			while(rs.next())
			{
				{
					check++;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + duplicateQuery);
		}
		
		//Return error dialog if the member is already booked on selected course.
		if(check != 0)
		{
			JOptionPane.showMessageDialog(null, "The chosen member is already booked on the chosen course.", "Duplicate Booking", JOptionPane.ERROR_MESSAGE);
		}
		//Return error if the course is already full.
		else if (currentBookings >= maxBookings)
		{
			JOptionPane.showMessageDialog(null, "This course is full. You cannot create more bookings for it.", "Course full.", JOptionPane.ERROR_MESSAGE);
		}
		//Book selected member on selected course.
		else
		{
			String insertQuery = "INSERT INTO membercourse (memberid, courseid) VALUES (" + memID + ", " + courseID +");";
			dint.performInsertion(insertQuery);
		}
		
		dint.closeConnection();
		
	}
	
	
	private int fetchID(DatabaseInteractor dint, boolean member)
	{
		//Alternate query and column name depending on whether looking for member or course ID.
		String searchQuery;
		String targetColumn;
		
		if(member)
		{
			searchQuery = "SELECT memid FROM member WHERE fname='" + memFirstName+"' AND lname='" + memLastName+"';";
			targetColumn = "memid";
		}
		else
		{
			searchQuery = "SELECT courseid FROM course WHERE name='" + courseName+"';";
			targetColumn = "courseid";
		}
		
		//Execute query and iterate through result set to retrieve ID.
		ResultSet rs = dint.selectQuery(searchQuery);
		int temp=0;
		try
		{
			//Result set should have only one row.
			while(rs.next())
			{
				temp = rs.getInt(targetColumn);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + searchQuery);
		}
		return temp;
	}
	
}
