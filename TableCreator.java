import javax.swing.*;
import java.sql.*;

public class TableCreator {
	
	private String [] headings;
	private String [][] data;
	int cols, rows;
	private JTable results;
	
	public TableCreator(DatabaseInteractor dint, String query)
	{
		ResultSet rs = dint.selectQuery(query);
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount();
			headings = populateHeadings(rsmd, cols);
			rows = 40;
			data = populateData(rs, rows);
			dint.closeConnection();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
		
		results = new JTable(data, headings);
	}
	
	public JTable getTable()
	{
		return results;
	}
	
	private String[] populateHeadings(ResultSetMetaData rsmd, int cols) throws SQLException
	{
		String[]temp = new String[cols];
		for(int i=0; i < cols; i++)
		{
			temp[i] = rsmd.getColumnName(i+1);
			System.out.println(temp[i]);
		}
		return temp;
	}
	
	private String[][] populateData(ResultSet rs, int rows) throws SQLException
	{
		
		String[][] temp = new String[rows][cols];
		int i=0;
		while(rs.next())
		{
			
			for(int j=0;j<cols;j++)
			{
				String datum = rs.getString(j+1);
				if(datum == null)
				{
					temp[i][j] = "0";
				}
				else
				{
					temp[i][j] = datum;
				}
				
			}
			i++;
		}
		return temp;
		
		
	}
	
	
	
	
	
	
	

	
}
