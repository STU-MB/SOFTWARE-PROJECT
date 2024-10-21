/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kevol
 */
package main.java.com.mycompany.hms;

import java.nio.file.Paths;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class Admin {
    private String DBSLocation;
    private Connection conn;
    private Statement s;
    private ResultSet rs;
    private JTable HealthVisitorTable;
    
         public Admin(String DBSLocation){
        this.DBSLocation = "jdbc:ucanaccess://" + Paths.get(DBSLocation).toAbsolutePath().toString();
      
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void setDoctorTable(JTable  HealthVisitorTable){
        this.HealthVisitorTable =  HealthVisitorTable;
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
         
         public void ViewAllDoctors(){
        String doctorInfo[] = new String[4];
        String query = "SELECT * FROM Doctors";   
            int j = 0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            while(rs.next()){
                doctorInfo[0] = (String)rs.getString("ID");               
                doctorInfo[1] = (String)rs.getString("Surname");
                doctorInfo[2] = (String)rs.getString("Name");
                
                
                query = "SELECT * FROM DoctorSpecialisations WHERE DoctorID = '" + (String)rs.getString("ID") + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                query = "SELECT * FROM Specialisations WHERE SpecCode = '" + (String)rs1.getString("SpecCode") + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                doctorInfo[3] = (String)rs1.getString("Specialisation");
                
                    for(int i = 0; i<4; i++){
                   HealthVisitorTable.setValueAt(doctorInfo[i], j, i);
                }
                j++;
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e + "\nUnable to load Doctors");
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void ViewADoctor(String search){//method to enter a specific doctor ID to view doctors with that ID
         String doctorInfo[] = new String[3];
        String query = "SELECT * FROM Doctors WHERE ID = '"+search+"'";   
        int j=0;
        boolean flag=false;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            if(rs.next()){
                doctorInfo[0] = (String)rs.getString("ID");
                doctorInfo[1] = (String)rs.getString("Name");
                doctorInfo[2] = (String)rs.getString("Surname");
                flag=true;
                for(int i = 0; i<3; i++){
                    HealthVisitorTable.setValueAt(doctorInfo[i], j, i);
                }
                
            }
               if(flag ==false)
            {  JOptionPane.showMessageDialog(null, "Doctor not found");}
            
            
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null,e);
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void DeleteDoctor(String ID) {
        
 
        String sql = "DELETE FROM Doctors WHERE ID = ?";

        try {
            conn = DriverManager.getConnection(DBSLocation);  // Manually connect to the database
            PreparedStatement pstmt = conn.prepareStatement(sql);  // Create the PreparedStatement
            pstmt.setString(1, ID);
            
            int affectedRows = pstmt.executeUpdate();  // Execute the delete operation
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null,"Entry deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null,"No entry found with the given ID.");
            }

        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e);

        } 
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void AddDoctor(String ID, String Name, String Surname) {
         String sql = "INSERT INTO Doctors (ID, Name, Surname) VALUES (?, ?, ?)";
        try {
            // 1. Establish a connection
            conn = DriverManager.getConnection(DBSLocation);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 4. Set the values for the placeholders
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, Surname);
            

            // 5. Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Doctor added successfully!");
            } else {
              JOptionPane.showMessageDialog(null,"Failed to add the doctor.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
         
        }
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void UpdateDoctor(String ID, String newName, String newSurname) {
        String sql= "UPDATE Doctors SET Name = ?, Surname = ? WHERE ID = ?";   
        try{ 
            conn = DriverManager.getConnection(DBSLocation);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, newSurname);
            pstmt.setString(3, ID);
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"update completed!");
            } else {
               JOptionPane.showMessageDialog(null,"update failed.");
            }
         } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e);
        } 
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
         public void ViewAllPatients(){
         String PatientInfo[] = new String[6];
        String query = "SELECT * FROM Patients";  
        int Status;
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                PatientInfo[0] = (String)rs.getString("ID");
                PatientInfo[2] = (String)rs.getString("Name");
                PatientInfo[1] = (String)rs.getString("Surname");
                PatientInfo[4] = (String)rs.getString("DoctorCode");
                PatientInfo[5] = (String)rs.getString("Status");
                query = "SELECT * FROM PatientIllnesses WHERE PatientID = '" + (String)rs.getString("ID") + "'";
                //query = "SELECT * FROM PatientIllnesses WHERE PatientID = 'P001'";
                rs1 = s.executeQuery(query);
                rs1.next();
                query = "SELECT * FROM Illnesses WHERE IllnessCode = '" + (String)rs1.getString("IllnessID") + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                PatientInfo[3] = (String)rs1.getString("Illness");//should be condition
                
                for(int i = 0; i<6; i++){
                     HealthVisitorTable.setValueAt(PatientInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e + "\nUnable to view Patients");
            
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void ViewAPatient(String search){//method to enter a specific patient ID to view PATIENTS with that ID
                    String PatientInfo[] = new String[5];
        String query = "SELECT * FROM Patients WHERE ID = '"+search+"'";    
        int j=0;
        boolean flag= false;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            if(rs.next()){
                PatientInfo[0] = (String)rs.getString("ID");
                PatientInfo[1] = (String)rs.getString("Name");
                PatientInfo[2] = (String)rs.getString("Surname");
                 PatientInfo[3] = (String)rs.getString("DoctorCode");
                PatientInfo[4] = (String)rs.getString("Address");
                int Status = (int)rs.getInt("Status");
                String room=(String)rs.getString("RoomNumber");
                flag=true;
                 for(int i = 0; i<5; i++){
                 HealthVisitorTable.setValueAt(PatientInfo[i], j, i); 
                } 
                 HealthVisitorTable.setValueAt(Status, j, 6);
                 HealthVisitorTable.setValueAt(room, j, 7);
            }
            if(flag ==false)
            {  JOptionPane.showMessageDialog(null, "Patient not found");}
            
       
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void DeletePatient(String ID) {
       
        String sql = "DELETE FROM Patients WHERE ID = ?";

        try {
            conn = DriverManager.getConnection(DBSLocation);  // Manually connect to the database
             PreparedStatement pstmt = conn.prepareStatement(sql);  // Create the PreparedStatement
            pstmt.setString(1, ID);
            int affectedRows = pstmt.executeUpdate();  // Execute the delete operation

            if (affectedRows > 0) {
              JOptionPane.showMessageDialog(null, "Entry deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No entry found with the given id.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void AddPatient(String ID, String Name, String Surname,String DoctorCode,String Address,int Status,String room) {
         String sql = "INSERT INTO Patient (ID, Name, Surname,DoctorCode,Address,Status,RoomNumber) VALUES (?, ?, ?,?,?,?,?)";
       

        try {
            // 1. Establish a connection
            conn = DriverManager.getConnection(DBSLocation);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. Set the values for the placeholders
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, Surname);
            pstmt.setString(4, DoctorCode);
            pstmt.setString(5, Address);
            pstmt.setInt(6, Status);
            pstmt.setString(7, room);

            // 5. Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
               JOptionPane.showMessageDialog(null,"Patient added successfully!");
            } else {
                JOptionPane.showMessageDialog(null,"Failed to add the Patient.");
            }

        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e);
        } 
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void UpdatePatient(String ID, String newName, String newSurname,String newDoctorCode,String newAddress,int Status,String room) {
        String sql= "UPDATE Patients SET Name = ?, Surname = ? ,DoctorCode = ?,Address = ?,Status = ?,RoomNumber=? WHERE ID = ?";

        try{ 
            conn = DriverManager.getConnection(DBSLocation);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, newSurname);
            pstmt.setString(3, newDoctorCode);
            pstmt.setString(4, newAddress);
            pstmt.setInt(5, Status);
            pstmt.setString(6, room);
        
            pstmt.setString(7, ID);
            
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "update completed!");

            } else {
                JOptionPane.showMessageDialog(null, "update failed.");
            }
         } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, e);
        } 
    }/////////////////////////////////////////////////////////////////////////////////////////////////////
         
         public void ViewAllNurse(){
             String NurseInfo[] = new String[3];
        String query = "SELECT * FROM Nurses";  
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            while(rs.next()){
                NurseInfo[0] = (String)rs.getString("ID");
                NurseInfo[1] = (String)rs.getString("Name");
                NurseInfo[2] = (String)rs.getString("Surname");
            
                
                for(int i = 0; i<3; i++){
                     HealthVisitorTable.setValueAt(NurseInfo[i], j, i);
                }
                j++;
                
            }
        }catch(SQLException e){
          JOptionPane.showMessageDialog(null, e + "\nUnable to view Nurses");
        }
    }   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void ViewANurse(String search){//method to enter a specific patient ID to view PATIENTS with that ID
                    String NurseInfo[] = new String[3];
        String query = "SELECT * FROM Nurses WHERE ID = '"+search+"'";    
        int j=0;
        boolean flag= false;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            if(rs.next()){
                NurseInfo[0] = (String)rs.getString("ID");
                NurseInfo[1] = (String)rs.getString("Name");
                NurseInfo[2] = (String)rs.getString("Surname");
                 
                flag=true;
                 for(int i = 0; i<3; i++){
                     HealthVisitorTable.setValueAt(NurseInfo[i], j, i);
                }
                
            }
            if(flag ==false)
            {  JOptionPane.showMessageDialog(null, "Nurse not found");}
            
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void DeleteNurse(String ID) {
        
      
        String sql = "DELETE FROM Nurses WHERE ID = ?";

        try {
            conn = DriverManager.getConnection(DBSLocation);  // Manually connect to the database
            PreparedStatement pstmt = conn.prepareStatement(sql);  // Create the PreparedStatement
            pstmt.setString(1, ID);
            int affectedRows = pstmt.executeUpdate();  // Execute the delete operation

            if (affectedRows > 0) {
                 JOptionPane.showMessageDialog(null, "Entry deleted successfully.");
            } else {
              JOptionPane.showMessageDialog(null, "No entry found with the given id.");
            }

        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e);
        } 
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void AddNurse(String ID, String Name, String Surname) {
         String sql = "INSERT INTO Nurses (ID, Name, Surname) VALUES (?, ?, ?)";
     

        try {
            // 1. Establish a connection
            conn = DriverManager.getConnection(DBSLocation);
            
            PreparedStatement  pstmt = conn.prepareStatement(sql);

            // 4. Set the values for the placeholders
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, Surname);
             

            // 5. Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Nurse added successfully!");
            } else {
            JOptionPane.showMessageDialog(null, "Failed to add the Nurse.");
            }

        } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, e);
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void UpdateNurse(String ID, String newName, String newSurname) {
        String sql= "UPDATE Nurse SET Name = ?, Surname = ?  WHERE ID = ?";
             
        try{ 
            conn = DriverManager.getConnection(DBSLocation);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, newSurname);
            pstmt.setString(3, ID);
            
           int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
             JOptionPane.showMessageDialog(null, "update completed!");
            } else {
       JOptionPane.showMessageDialog(null, "update failed.");
            } // Execute the update without checking rows affected
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }/////////////////////////////////////////////////////////////////////////////////////////////////////
        
         public void ViewAllAppointments(){
           
        String query = "SELECT * FROM Appointments";  
        int j=0;
        String appointmentInfo[] = new String[4];
        boolean attended;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            while(rs.next()){
//                String SlotNum= (String)rs.getString("SlotNum");
//                Time StartTime = (Time)rs.getTime("StartTime");
//                String PatientCode = (String)rs.getString("PatientCode");
//                String NurseCode = (String)rs.getString("NurseCode");
//                String DoctorCode = (String)rs.getString("DoctorCode");
//                boolean Completed= (boolean)rs.getBoolean("Completed");
                
//                HealthVisitorTable.setValueAt(SlotNum, j, 0);
//                HealthVisitorTable.setValueAt(StartTime, j, 1);
//                HealthVisitorTable.setValueAt(PatientCode, j, 2);
//                HealthVisitorTable.setValueAt(NurseCode, j, 3);
//                HealthVisitorTable.setValueAt(DoctorCode, j, 4);
//                HealthVisitorTable.setValueAt(Completed, j, 5); 

                appointmentInfo[0] = rs.getString("DoctorCode");
                appointmentInfo[1] = rs.getString("PatientCode");
                //appointmentInfo[4] = rs.getString("StartTime").substring(11);
                Time t = rs.getTime("StartTime");
                appointmentInfo[2] = rs.getString("NurseCode");
                attended = rs.getBoolean("Completed");
                appointmentInfo[3] = rs.getString("RoomNumber");
                for(int i = 0; i<4; i++) HealthVisitorTable.setValueAt(appointmentInfo[i], j, i);
                HealthVisitorTable.setValueAt(t, j, 4);
                HealthVisitorTable.setValueAt(attended, j, 5);
                j++;
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e + "\nUnable to view Appointments");
        }
    }   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void ViewAnAppointment(String search){//method to enter a specific patient ID to view PATIENTS with that ID
                   
        String query = "SELECT * FROM Appointments WHERE ID = '"+search+"'";    
        int j=0;
        boolean flag= false;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            if(rs.next()){
                String SlotNum= (String)rs.getString("SlotNum");
                Time StartTime = (Time)rs.getTime("StartTime");
                String PatientCode = (String)rs.getString("PatientCode");
                String NurseCode = (String)rs.getString("NurseCode");
                String DoctorCode = (String)rs.getString("DoctorCode");
                boolean Completed= (boolean)rs.getBoolean("Completed");
                 
                flag=true;
                HealthVisitorTable.setValueAt(SlotNum, j, 0);
                HealthVisitorTable.setValueAt(StartTime, j, 1);
                HealthVisitorTable.setValueAt(PatientCode, j, 2);
                HealthVisitorTable.setValueAt(NurseCode, j, 3);
                HealthVisitorTable.setValueAt(DoctorCode, j, 4);
                HealthVisitorTable.setValueAt(Completed, j, 5); 
                
            }
            if(flag ==false)
            {  JOptionPane.showMessageDialog(null, "Nurse not found");}
            
       
        }catch(SQLException e){
         JOptionPane.showMessageDialog(null, e);           
}
     }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         public void DeleteAppointment(String SlotNum) {
        
        
        String sql = "DELETE FROM Appointments WHERE id = ?";

        try {
            conn = DriverManager.getConnection(DBSLocation);  // Manually connect to the database
           PreparedStatement  pstmt = conn.prepareStatement(sql);  // Create the PreparedStatement
            pstmt.setString(1, SlotNum);
            int affectedRows = pstmt.executeUpdate();  // Execute the delete operation

            if (affectedRows > 0) {
                   JOptionPane.showMessageDialog(null, "Entry deleted successfully.");
            } else {
               JOptionPane.showMessageDialog(null, "No entry found with the given id.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        } 
    }//////////////////////////////////////////////////////////////////////////////////////////////
         public void AddAppointment(String Slotnum, Time Start, String Patientcode,String NurseCode,String DoctorCode,boolean Completed) {
         String sql = "INSERT INTO Appointments (SlotNum, StartTime,PatientCode,NurseCode,DoctorCode,Completed) VALUES (?, ?, ?,?,?,?)";
    

        try {
            // 1. Establish a connection
            conn = DriverManager.getConnection(DBSLocation);
            
              PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, Slotnum);
            pstmt.setTime(2, Start);
            pstmt.setString(3, Patientcode);
             pstmt.setString(4, NurseCode);
              pstmt.setString(5, DoctorCode);
            pstmt.setBoolean(6, Completed);
         

            // 5. Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
               JOptionPane.showMessageDialog(null, "Doctor added successfully!");

            } else {
               JOptionPane.showMessageDialog(null, "Failed to add the doctor.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }//////////////////////////////////////////////////////
         public void UpdateAppointment(String Slotnum, Time Start, String Patientcode,String NurseCode,String DoctorCode,boolean Completed) {
        String sql= "UPDATE Appointments SET  StartTime = ? ,PatientCode = ? ,NurseCode=?,DoctorCode=?,Completed=? WHERE SlotNum = ?";
           
        try{ 
                    conn = DriverManager.getConnection(DBSLocation);
            
             PreparedStatement pstmt = conn.prepareStatement(sql);


            
            pstmt.setTime(1, Start);
            pstmt.setString(2, Patientcode);
             pstmt.setString(3, NurseCode);
              pstmt.setString(4, DoctorCode);
            pstmt.setBoolean(5, Completed);
         pstmt.setString(6, Slotnum);

            // 5. Execute the SQL statement
            int rowsAffected = pstmt.executeUpdate();

            // 6. Check if the insertion was successful
            if (rowsAffected > 0) {
             JOptionPane.showMessageDialog(null, "update completed!");
            } else {
       JOptionPane.showMessageDialog(null, "update failed.");
            }
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }/////////////////////////////////////////////////////////////////////////////////////////////////////
         
    public void viewAllIllnesses(){
        String[] IllnessInfo = new String[2];
        String query = "SELECT * FROM Illnesses";  
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                IllnessInfo[0] = (String)rs.getString("IllnessCode");
                IllnessInfo[1] = (String)rs.getString("Illness");
               
                
                
                for(int i = 0; i<2; i++){
                     HealthVisitorTable.setValueAt(IllnessInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e + "\nUnable to view Illnesses");
            
        }
    }
    
    public void viewAllSpecs(){
        String[] SpecInfo = new String[3];
        String query = "SELECT * FROM DoctorSpecialisations";  
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                SpecInfo[0] = (String)rs.getString("DoctorID");
                SpecInfo[1] = (String)rs.getString("SpecCode");
                query = "SELECT * FROM Specialisations WHERE SpecCode = '" + SpecInfo[1] + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                SpecInfo[2] = rs1.getString("Specialisation");
                for(int i = 0; i<3; i++){
                     HealthVisitorTable.setValueAt(SpecInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e + "\nUnable to view Doctor Specialisations");
            
        }
    }
    
    public void viewNurse(){
        String[] NurseInfo = new String[3];
        String query = "SELECT * FROM Nurses";  
        
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            //ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                NurseInfo[0] = (String)rs.getString("ID");
                NurseInfo[1] = (String)rs.getString("Surname");
                NurseInfo[2] = (String)rs.getString("Name");
                
                
                
                for(int i = 0; i<3; i++){
                     HealthVisitorTable.setValueAt(NurseInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
}
