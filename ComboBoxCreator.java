import javax.swing.*;
import java.sql.*;



public class ComboBoxCreator {
	
	private String [] list;
	private JComboBox options;
	private static final int MAX_COURSES=10;
	private static final int MAX_MEMBERS=100;
	private String attribute, attribute2;
	
	public ComboBoxCreator(DatabaseInteractor dint,String query, String att)
	{
		attribute = att;
		ResultSet rs = dint.selectQuery(query);
		try
		{	
			list = populateCourseList(rs);
			dint.closeConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
		options = new JComboBox(list);
	}
	
	public ComboBoxCreator(DatabaseInteractor dint,String query, String att1, String att2)
	{
		attribute = att1;
		attribute2 = att2;
		ResultSet rs = dint.selectQuery(query);
		try
		{	
			list = populateMemberList(rs);
			dint.closeConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}
		options = new JComboBox(list);
	}
	
	
	
	public String[] populateCourseList(ResultSet rs) throws SQLException
	{
		String[] temp = new String[MAX_COURSES];
		int i=0;
		while(rs.next())
		{
			temp[i] = rs.getString(attribute);
			i++;
		}
		return temp;
	}
	
	public String[] populateMemberList(ResultSet rs) throws SQLException
	{
		String[] temp = new String[MAX_MEMBERS];
		int i=0;
		while(rs.next())
		{
			String first = rs.getString(attribute);
			String last = rs.getString(attribute2);
			temp[i] = last + ", " + first;
			i++;
		}
		return temp;
	}
	
	public JComboBox getComboBox()
	{
		return options;
	}
	
	

}
