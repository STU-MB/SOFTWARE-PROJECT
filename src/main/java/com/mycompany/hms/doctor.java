package main.java.com.mycompany.hms;

import java.nio.file.Paths;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import javax.swing.JComboBox;

public class doctor {
    private String DBSLocation;
    private Connection conn;
    private Statement s;
    private ResultSet rs;
    private JTable table; // Placeholder for the table to display results

    // Constructor to initialize the database connection string
    public doctor(String DBSLocation) {
        this.DBSLocation = "jdbc:ucanaccess://" + Paths.get(DBSLocation).toAbsolutePath().toString();
    }

    // Method to set the JTable (GUI component) for displaying results
    public void setTable(JTable table) {
        this.table = table;
    }

    // Example method to view data from a table
  

public void viewData() {
    String[] data = new String[4]; // Adjust based on the number of columns
    boolean completed = false;
    String queryAppointments = "SELECT * FROM Appointments WHERE DoctorCode = '" + user + "'"; // Main query
    int rowIndex = 0;

    try {
        conn = DriverManager.getConnection(DBSLocation);
        s = conn.createStatement();
        rs = s.executeQuery(queryAppointments);

        // Date formatter for StartTime column
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Adjust format as needed

        while (rs.next()) {
            // Populate the data array with values from the result set               
            String PatientCode = rs.getString("PatientCode");
            String queryPatients = "SELECT * FROM Patients WHERE ID = '" + PatientCode + "'";
            Statement s1 = null;
            ResultSet rs1 = null;

            try {
                s1 = conn.createStatement();
                rs1 = s1.executeQuery(queryPatients);

                if (rs1.next()) {
                    data[0] = rs1.getString("Surname");                
                    data[1] = rs1.getString("Name");
                    data[2] = rs1.getString("RoomNumber");

                    // Retrieve and format the StartTime
                    Timestamp startTime = rs.getTimestamp("StartTime");
                    data[3] = startTime != null ? timeFormat.format(startTime) : "N/A";

                    // Update the JTable with the retrieved data
                    for (int i = 0; i < data.length; i++) {
                        table.setValueAt(data[i], rowIndex, i);
                    }
                    completed = rs.getBoolean("Completed");
                    table.setValueAt(completed, rowIndex, 4);
                    rowIndex++;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error fetching patient data: " + e.getMessage());
            } finally {
                // Close the inner resources
                if (rs1 != null) rs1.close();
                if (s1 != null) s1.close();
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    } finally {
        // Clean up outer database resources
        try {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
    }
}


   
  public void viewPatient(String userid) {
    String[] data = new String[3];  
    String query = "SELECT * FROM Patients WHERE DoctorCode = '" + userid + "'";
    int rowIndex = 0;
     
    try {
        conn = DriverManager.getConnection(DBSLocation);
        s = conn.createStatement();
        rs = s.executeQuery(query);
        
        while (rs.next()) {
            data[0] = rs.getString("Surname"); 
            data[1] = rs.getString("Name");
            data[2] = rs.getString("Status"); 

            // Populate the JTable
            for (int i = 0; i < data.length; i++) {
                table.setValueAt(data[i], rowIndex, i);
            }
            rowIndex++;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    } finally {
        // Clean up resources
        try {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
    }
}

  public String getUser(String user){
      
      String query = "SELECT * FROM DOCTORS";
      String name = "";
      try {
          
        conn = DriverManager.getConnection(DBSLocation);
        s = conn.createStatement();
        rs = s.executeQuery(query);
        
        while (rs.next()) {          
        
          if (user.equals(rs.getString("ID"))){
             name = rs.getString("Name") +" "+ rs.getString("Surname");
          }  
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    } finally {
        // Clean up resources
        try {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
      
      }
      return name;
  }
 String user;
  public void setUser(String s){
      user = s;
      
  }     



public String getOption() {
    // Define the options for the JComboBox
    String[] options = {"S1", "S2", "S3", "S4", "S5", "S6", "S7"};

    // Create a JComboBox with these options
    JComboBox<String> comboBox = new JComboBox<>(options);

    // Show the JOptionPane with the JComboBox
    int result = JOptionPane.showConfirmDialog(null, comboBox, "Select an Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

    // Declare the variable outside the if block
    String selectedOption = null;

    // Check if the user clicked OK
    if (result == JOptionPane.OK_OPTION) {
        // Get the selected item
        selectedOption = (String) comboBox.getSelectedItem();
    } else {
        JOptionPane.showMessageDialog(null, "No option selected.");
    }

    return selectedOption;
}


  
  }//end of class
    // Add more methods as needed to handle other database interactions (insert, update, delete)

