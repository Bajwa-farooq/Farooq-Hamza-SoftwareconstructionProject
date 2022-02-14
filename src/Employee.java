import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JLabel lblCNICNumber;
	private JTextField jtxtCNICNumber;
	private JLabel lblFirstname;
	private JTextField jtxtFirstname;
	private JLabel lblSurname;
	private JTextField jtxtSurname;
	private JLabel lblGender;
	private JTextField jtxtGender;
	private JLabel lblDOB;
	private JTextField jtxtDOB;
	private JLabel lblAge;
	private JTextField jtxtAge;
	private JLabel lblSalary;
	private JTextField jtxtSalary;
	private JButton btnPrint;
	private JButton btnReset;
	private JButton btnExit;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs =null;
	
	DefaultTableModel model = new DefaultTableModel();
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	
	public void updateTable()
	{
		conn = EmployeeDataBase.ConnectDB();
		
		if(conn !=null)
		{
			String sql = " Select EmpID , CNICNumber, Firstname, Surname,Gender, DOB, Age, Salary";
		
		
		try 
		{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Object[] columnData = new Object[8];
			
			while(rs.next()) {
				columnData [0] = rs.getString("EmpID");
				columnData [1] = rs.getString("CNICNumber");
				columnData [2] = rs.getString("Firstname");
				columnData [3] = rs.getString("Surname");
				columnData [4] = rs.getString("Gender");
				columnData [5] = rs.getString("DOB");
				columnData [6] = rs.getString("Age");
				columnData [7] = rs.getString("Salary");
				
				model.addRow(columnData);
				
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		  }
		
		}
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();
		
		conn = EmployeeDataBase.ConnectDB();
		Object col[]= {"EmpID" , "CNICNumber", "Firstname", "Surname","Gender", "DOB", "Age", "Salary"};
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 18));
		frame.setBounds(0, 0, 1450, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmployeeID = new JLabel("Employee ID");
		lblEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEmployeeID.setBounds(78, 166, 121, 25);
		frame.getContentPane().add(lblEmployeeID);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtEmployeeID.setBounds(225, 163, 215, 28);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String  sql = "INSERT INTO employee(EmpID , CNICNumber, Firstnam, Surname,Gender, DOB, Age,Salary)VALUES(?,?,?,?,?,?,?,?)";
				
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, jtxtEmployeeID.getText());
					pst.setString(2, jtxtCNICNumber.getText());
					pst.setString(3, jtxtFirstname.getText());
					pst.setString(4, jtxtSurname.getText());
					pst.setString(5, jtxtGender.getText());
					pst.setString(6, jtxtDOB.getText());
					pst.setString(7, jtxtAge.getText());
					pst.setString(8, jtxtSalary.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
				}
				catch(Exception ev) {
					JOptionPane.showMessageDialog(null, "System Updated");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {
						 jtxtEmployeeID.getText(),
						 jtxtCNICNumber.getText(),
						 jtxtFirstname.getText(),
						 jtxtSurname.getText(),
						 jtxtGender.getText(),
						 jtxtDOB.getText(),
						 jtxtAge.getText(),
						 jtxtSalary.getText(),
				});
				if(table.getSelectedRow()== -1) {
					if (table.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "Membership Update confirmed","Employee Database System",
							JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnAddNew.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAddNew.setBounds(78, 547, 182, 42);
		frame.getContentPane().add(btnAddNew);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(585, 163, 680, 317);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpID", "CNICNumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		lblCNICNumber = new JLabel("CNIC Number");
		lblCNICNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCNICNumber.setBounds(78, 208, 139, 25);
		frame.getContentPane().add(lblCNICNumber);
		
		jtxtCNICNumber = new JTextField();
		jtxtCNICNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtCNICNumber.setColumns(10);
		jtxtCNICNumber.setBounds(225, 205, 215, 28);
		frame.getContentPane().add(jtxtCNICNumber);
		
		lblFirstname = new JLabel("Firstname");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFirstname.setBounds(78, 247, 108, 25);
		frame.getContentPane().add(lblFirstname);
		
		jtxtFirstname = new JTextField();
		jtxtFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtFirstname.setColumns(10);
		jtxtFirstname.setBounds(225, 244, 215, 28);
		frame.getContentPane().add(jtxtFirstname);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSurname.setBounds(78, 286, 108, 25);
		frame.getContentPane().add(lblSurname);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(225, 283, 215, 28);
		frame.getContentPane().add(jtxtSurname);
		
		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGender.setBounds(78, 325, 108, 25);
		frame.getContentPane().add(lblGender);
		
		jtxtGender = new JTextField();
		jtxtGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(225, 322, 215, 28);
		frame.getContentPane().add(jtxtGender);
		
		lblDOB = new JLabel("DOB");
		lblDOB.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDOB.setBounds(78, 364, 108, 25);
		frame.getContentPane().add(lblDOB);
		
		jtxtDOB = new JTextField();
		jtxtDOB.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtDOB.setColumns(10);
		jtxtDOB.setBounds(225, 361, 215, 28);
		frame.getContentPane().add(jtxtDOB);
		
		lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAge.setBounds(78, 403, 108, 25);
		frame.getContentPane().add(lblAge);
		
		jtxtAge = new JTextField();
		jtxtAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(225, 400, 215, 28);
		frame.getContentPane().add(jtxtAge);
		
		lblSalary = new JLabel("Salary");
		lblSalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSalary.setBounds(78, 442, 108, 25);
		frame.getContentPane().add(lblSalary);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(225, 439, 215, 28);
		frame.getContentPane().add(jtxtSalary);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				MessageFormat header = new MessageFormat("Printing In Progress");
				MessageFormat footer = new MessageFormat("Page  {0, number, integer}");
				try
				{
					table.print();
					
				}
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No printer found", ev.getMessage());
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnPrint.setBounds(301, 547, 182, 42);
		frame.getContentPane().add(btnPrint);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jtxtEmployeeID.setText(null);
				jtxtCNICNumber.setText(null);
				jtxtFirstname.setText(null);
				jtxtSurname.setText(null);
				jtxtGender.setText(null);
				jtxtDOB.setText(null);
				jtxtAge.setText(null);
				jtxtSalary.setText(null);
			    
				
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(521, 547, 182, 42);
		frame.getContentPane().add(btnReset);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame =new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System",
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(743, 547, 182, 42);
		frame.getContentPane().add(btnExit);
		
		lblNewLabel = new JLabel("Employee Dataase Management System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(429, 43, 839, 50);
		frame.getContentPane().add(lblNewLabel);
	}
}
