import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class DatabaseGUI extends JFrame implements ActionListener{
	
	private JButton viewCourses, viewBookings, createBooking, home;
	private JLabel welcome;
	private JTable results;
	private JScrollPane scroller;
	private JPanel top, bottom;
	
	public DatabaseGUI()
	{
		this.setSize(800,600);
		this.setLocation(100,100);
		this.setTitle("Gym Database GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutStartScreen();
		
		this.setVisible(true);
	}
	
	
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == viewCourses)
		{
			TableCreator tabler = new TableCreator(new DatabaseInteractor(), "SELECT name, instructor, capacity  FROM course");
			scroller = new JScrollPane(tabler.getTable());
			this.add(scroller, BorderLayout.SOUTH);
			this.repaint();
			this.revalidate();
			
		}
	}
	
	public void layoutStartScreen()
	{
		top = new JPanel();
		bottom = new JPanel();
		
		welcome = new JLabel ("Welcome to the Gym Databse. What would you like to do?");
		viewCourses = new JButton("View Courses");
		viewBookings = new JButton("View Course Bookings");
		createBooking = new JButton("Book a Member on a Course");
		
		viewCourses.addActionListener(this);
		viewBookings.addActionListener(this);
		createBooking.addActionListener(this);
		
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
		
		top.add(welcome);
		bottom.add(viewCourses);
		bottom.add(viewBookings);
		bottom.add(createBooking);
		
		
	}
	
	public void layoutCoursesScreen()
	{
		//Create and add relevant JTable filled with information. 
		//Use SELECT * FROM Courses statement to get required data.
	}
	
	public void fetchTable(String statement)
	{
		//Will create instance of DatabaseInteractor and pass it a query, then extract the relevant data set.
		//May take query statement as a String parameter
		//Will return complete JTable
		DatabaseInteractor dbInt = new DatabaseInteractor();
		dbInt.tableQuery(statement);
		
		
	}
	
	public static void main(String[] args)
	{
		DatabaseGUI gui = new DatabaseGUI();
		//TableCreator tabler = new TableCreator(new DatabaseInteractor(), "SELECT * FROM course");
		gui.fetchTable("SELECT * FROM member");
		
	}
	
	

}
