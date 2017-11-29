import java.sql.*;

public class DatabaseInteractor {
	
	private Connection connection = null;
	/*String databaseName = "m_17_2077975v";
	String username = "m_17_2077975v";
	String password = "2077975v";
	*/
	String databaseName = "postgres";
	String username = "postgres";
	String password = "thunder";
	
	public DatabaseInteractor()
	{
		connectToDB();
	}
	
	//Opens connection to the database.
	public void connectToDB()
	{
		try
		{
			//connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + databaseName, username, password);
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, username, password);
			Statement stmt = connection.createStatement();
			stmt.execute("SET SCHEMA 'Gym Exercise'");
		}
		catch(SQLException e)
		{
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		
		if(connection!=null)
		{
			System.out.println("Connection successful!");
		}
		else
		{
			System.out.println("Failed to make connection!");
		}
	}
	
	//Performs a selection given a specific query and returns the resulting ResultSet.
	public ResultSet selectQuery(String query)
	{
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			
			//while(rs.next())
			//{
				//String course_name=rs.getString("fname");
				//System.out.println(course_name);
			//}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
		return rs;
	}
	
	//Performs an Insert query given a specific query.
	public void performInsertion(String query)
	{
		Statement stmt = null;
		try
		{
			stmt = connection.createStatement();
			int temp = stmt.executeUpdate(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
	}
	
	//End connection.
	public void closeConnection()
	{
		try
		{
			connection.close();
			System.out.println("Connection Closed");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Connection could not be closed - SQL Exception");
		}
	}
	
	
}
