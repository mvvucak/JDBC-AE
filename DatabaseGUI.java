import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;


//This class assumes that no two members will have the same full name for the purpose of creating new course bookings.
public class DatabaseGUI extends JFrame implements ActionListener{
	
	private JButton viewCourses, viewBookings, createBooking, home, viewSelected, book;
	private JLabel welcome, courseView;
	private JTable results;
	private JScrollPane scroller;
	private JPanel top, bottom;
	private JComboBox chooseMember, chooseCourse;
	
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
			//Create and execute query to select course details: course name, instructor name, capacity and number of bookings.
			String courseQuery = "SELECT c.name AS \"Course\", c.capacity AS \"Capacity\", i.fname AS \"Instructor First Name\", i.lname AS \"Instructor Last Name\", mc.nof AS \"Number of Members Booked\" FROM Course AS c INNER JOIN Instructor AS i ON c.instructor=i.instructorNumber LEFT JOIN (SELECT COUNT (memberid) AS nof, courseid FROM membercourse GROUP BY courseid) AS mc ON c.courseid=mc.courseid;"; 
			TableCreator tabler = new TableCreator(new DatabaseInteractor(), courseQuery);
			
			scroller = new JScrollPane(tabler.getTable());
			this.add(scroller, BorderLayout.SOUTH);
			this.repaint();
			this.revalidate();
			
		}
		else if(e.getSource()==viewBookings)
		{
			//Remove current components and replace them with the individual course details screen.
			layoutCourseScreen();
		}
		else if(e.getSource()==createBooking)
		{
			//Remove current components and replace them with the booking screen
			layoutBookScreen();
		}
		else if(e.getSource()==home)
		{
			//Remove current components and replace them with the start screen.
			this.getContentPane().removeAll();
			this.layoutStartScreen();
		}
		else if(e.getSource()==viewSelected)
		{
			//Remove previously displayed course details, if any.
			if(scroller!=null)
			{
				this.remove(scroller);
			}
			
			//Get selected course and use it to create slect query and JTable with required data.
			String courseName = (String) chooseCourse.getSelectedItem();
			String courseQuery = "SELECT c.name AS \"Course\", m.memid AS \"Member ID\", m.fname AS \"First Name\", m.lname AS \"Last Name\" FROM course AS c INNER JOIN membercourse AS mc ON c.courseid=mc.courseid INNER JOIN member AS m ON m.memid = mc.memberid WHERE c.name='"+courseName+"';";
			TableCreator tabler = new TableCreator(new DatabaseInteractor(), courseQuery);
			
			//Create and add scroll name to hold table data.
			scroller = new JScrollPane(tabler.getTable());
			this.add(scroller, BorderLayout.SOUTH);
			this.repaint();
			this.revalidate();
		}
		else if (e.getSource() == book)
		{
			//Get member and course from respective combo-boxes.
			String memberName = (String) chooseMember.getSelectedItem();
			String memberLastName = memberName.split(",")[0];
			String memberFirstName = memberName.split(",")[1].substring(1);
			String courseName = (String) chooseCourse.getSelectedItem();
			
			//Check that member or course selected is valid i.e. not an empty choice in the combo box.
			//Error message if either member or course is invalid.
			if(memberName==null || courseName==null)
			{
				JOptionPane.showMessageDialog(null, "Please choose a valid member or course.", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
			}
			//If both member and course are valid, create booking.
			else
			{
				System.out.println(memberFirstName+memberLastName+"a");
				Booker b = new Booker(new DatabaseInteractor(), memberFirstName, memberLastName, courseName);
			}
			
			
		}
	}
	
	public void layoutStartScreen()
	{
		//Create panels.
		top = new JPanel();
		bottom = new JPanel();
		
		//Create welcome label and 3 main buttons.
		welcome = new JLabel ("Welcome to the Gym Databse. What would you like to do?");
		viewCourses = new JButton("View Courses");
		viewBookings = new JButton("View Course Bookings");
		createBooking = new JButton("Book a Member on a Course");
		
		//Add action listeners to the main buttons.
		viewCourses.addActionListener(this);
		viewBookings.addActionListener(this);
		createBooking.addActionListener(this);
		
		//Add panels to frame.
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
		
		//Add other components to the panels.
		top.add(welcome);
		bottom.add(viewCourses);
		bottom.add(viewBookings);
		bottom.add(createBooking);
		this.repaint();
		this.revalidate();
	}
	
	public void layoutCourseScreen()
	{
		//Clear frame.
		this.getContentPane().removeAll();
		
		top = new JPanel();
		bottom = new JPanel();
		
		String courseListQuery = "SELECT name FROM course";
		ComboBoxCreator boxMaker = new ComboBoxCreator(new DatabaseInteractor(), courseListQuery, "name");
		
		chooseCourse = boxMaker.getComboBox();
		
		viewSelected = new JButton("View Bookings for Course");
		home = new JButton("Back To Start");
		courseView = new JLabel("Please select the course you wish to see bookings for");
		
		viewSelected.addActionListener(this);
		home.addActionListener(this);
		
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
		
		top.add(courseView);
		bottom.add(chooseCourse);
		bottom.add(viewSelected);
		bottom.add(home);
		
		this.repaint();
		this.revalidate();
		
	}
	
	public void layoutBookScreen()
	{
		this.getContentPane().removeAll();
		
		top = new JPanel();
		bottom = new JPanel();
		
		String courseListQuery = "SELECT name FROM course";
		String memberListQuery = "SELECT fname, lname FROM member";
		
		ComboBoxCreator boxMaker = new ComboBoxCreator(new DatabaseInteractor(), courseListQuery, "name");
		chooseCourse = boxMaker.getComboBox();
		
		boxMaker = new ComboBoxCreator(new DatabaseInteractor(), memberListQuery, "fname", "lname");
		chooseMember = boxMaker.getComboBox();
		
		book = new JButton("Book member on course");
		home = new JButton("Back To Start");
		welcome = new JLabel("Choose a member and the course you want to book them on.");
		
		book.addActionListener(this);
		home.addActionListener(this);
		
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
		
		top.add(welcome);
		bottom.add(chooseMember);
		bottom.add(chooseCourse);
		bottom.add(book);
		bottom.add(home);
		
		this.repaint();
		this.revalidate();
	}
	
	public void fetchTable(String statement)
	{
		//Will create instance of DatabaseInteractor and pass it a query, then extract the relevant data set.
		//May take query statement as a String parameter
		//Will return complete JTable
		DatabaseInteractor dbInt = new DatabaseInteractor();
		dbInt.selectQuery(statement);
		
		
	}
	
	public static void main(String[] args)
	{
		DatabaseGUI gui = new DatabaseGUI();
		//TableCreator tabler = new TableCreator(new DatabaseInteractor(), "SELECT * FROM course");
		//gui.fetchTable("SELECT * FROM member");
		//DatabaseInteractor dint = new DatabaseInteractor();
		
	}
	
	

}
