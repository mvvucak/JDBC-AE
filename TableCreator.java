import javax.swing.*;
import java.sql.*;

public class TableCreator {
	
	private String [] headings;
	private String [][] data;
	int cols, rows;
	private JTable results;
	
	public TableCreator(DatabaseInteractor dint, String query)
	{
		ResultSet rs = dint.tableQuery(query);
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount();
			headings = populateHeadings(rsmd, cols);
			rows = 40;
			data = populateData(rs, rows);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
		/*for (int i = 0; i<cols;i++)
		{
			System.out.print(headings[i]+" ");
		}
		for(int i = 0; i<rows; i++)
		{
			System.out.println("");
			System.out.print(data[i][1]+" ");
			System.out.print(data[i][2]+" ");
			System.out.print(data[i][3]+" ");
			System.out.print(data[i][4]+" ");
			System.out.println(data[i][5]+" ");
		}*/
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
				temp[i][j] = rs.getString(j+1);
			}
			i++;
		}
		return temp;
		
		
	}
	
	
	
	
	
	
	

	
}
