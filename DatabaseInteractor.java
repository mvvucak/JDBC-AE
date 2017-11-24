import java.sql.*;

public class DatabaseInteractor {
	
	private Connection connection = null;
	String databaseName = "m_17_2077975v";
	String username = "m_17_2077975v";
	String password = "2077975v";
	
	
	public DatabaseInteractor()
	{
		connectToDB();
	}
	
	public void connectToDB()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + databaseName, username, password);
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
	
	public ResultSet tableQuery(String query)
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
	
	
}
