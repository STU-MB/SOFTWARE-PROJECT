/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.hms;


import java.nio.file.Paths;
import java.sql.*;
import javax.swing.JTable;

public class Nurse {
   
    String DBSLocation;
    Connection conn;
    Statement s;
    ResultSet rs;
    JTable table;

    public Nurse(String DBSLocation) {
        this.DBSLocation = "jdbc:ucanaccess://" + Paths.get(DBSLocation).toAbsolutePath().toString();
        
        System.out.println(this.DBSLocation);
    }//////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    public void setTable(JTable table) {// Setter method to pass the JTable from the GUI
        this.table = table;  // Set the JTable, which will be used in your GUI
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    public void viewAllAppointments(String nurseId) {// Method to view all appointments for a specific nurse and update the JTable directly
        String query = "SELECT * FROM Appointments WHERE NurseCode = '" + nurseId + "'";
        try {
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);

            int i = 0;  // Index for the row of the table
           
            
            while (rs.next()) {// Loop through the ResultSet
               
                String PCODE = rs.getString("PatientCode");
                String patientQuery = "SELECT * FROM Patients WHERE ID = '" + PCODE + "'";
                ResultSet patientDetails = s.executeQuery(patientQuery);
                String det[] = new String[3];
                patientDetails.next();
                det[1] = patientDetails.getString("Name");
                det[0] = patientDetails.getString("Surname");
                det[2] = patientDetails.getString("RoomNumber");;
                
                for (int j = 0; j < 3; j++) {// Loop through each column of the current row and update the table
                  
                    table.setValueAt(det[j], i, j); // Set the value in row `i`, column `j` of the JTable
                }

                i++;  // Move to the next row for each record
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    public void markAppointmentAsCompleted(String appointmentId) {// Method to mark an appointment as completed
        String query = "UPDATE Appointments SET Completed = true WHERE AppointmentID = '" + appointmentId + "'";
        try {
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            int rowsAffected = s.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Appointment ID " + appointmentId + " has been marked as completed.");
            } else {
                System.out.println("No appointment found with ID " + appointmentId);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
   
     public void viewDeterioratingConditions(String nurseId) {
    
    String query = "SELECT PatientCode FROM Appointments WHERE NurseCode = '" + nurseId + "'";//  Get all patient codes from the Appointments table for the specified nurse
    
    try {
        conn = DriverManager.getConnection(DBSLocation); 
        s = conn.createStatement(); 
        rs = s.executeQuery(query); 
        int i = 0;
        
       
        while (rs.next()) { //  Look through each patient code
            String patientCode = rs.getString("PatientCode"); // Get the patient code

            
            String conditionQuery = "SELECT Surname, Name, Status, RoomNumber FROM Patients WHERE ID = '" + patientCode + "' AND Status > 5";// Get patient details from the DeterioratingConditions table for this patient
            ResultSet conditionRS = s.executeQuery(conditionQuery); // Run the query for patient conditions
            
             
            while (conditionRS.next()) {//If the patient has a bad condition (State < 5), get their details
                String surname = conditionRS.getString("Surname");
                String name = conditionRS.getString("Name");
                int state = conditionRS.getInt("Status");
                String room = conditionRS.getString("RoomNumber");

                // Determine the condition description based on the state
                String statusDescription = (state  >= 8) ? "Critical" : "Bad"; // "Critical" for 1-2, "Bad" for 3-4

                
                String[] details = {surname, name, room, statusDescription};// patient details to display in the table

              
                for (int j = 0; j < details.length; j++) {
                    table.setValueAt(details[j], i, j); 
                }

                i++; // Move to the next row for the next patient
            }
        }

    } catch (SQLException e) {
        System.out.println(e); 
    }
}

    
}

