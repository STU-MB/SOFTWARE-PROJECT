/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.hms;

import java.nio.file.Paths;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class HealthVisitor {
     private String DBSLocation;
    private Connection conn;
    private Statement s;
    private ResultSet rs;
      private JTable HealthVisitorTable;
    
    public HealthVisitor(String DBSLocation){
        this.DBSLocation = "jdbc:ucanaccess://" + Paths.get(DBSLocation).toAbsolutePath().toString();
      
    }/////////////////////////////////////////////////////////////////////////////////
     public void setDoctorTable(JTable  HealthVisitorTable){
        this.HealthVisitorTable =  HealthVisitorTable;
    }
     public void ViewAllDoctors(){//////////////////////////////////////////////
          String doctorInfo[] = new String[3];
        String query = "SELECT * FROM Doctors";   
            int j = 0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            while(rs.next()){
                    doctorInfo[0] = (String)rs.getString("ID");
                doctorInfo[1] = (String)rs.getString("Name");
                doctorInfo[2] = (String)rs.getString("Surname");
                
                    for(int i = 0; i<3; i++){
                   HealthVisitorTable.setValueAt(doctorInfo[i], j, i);
                }
                j++;
                
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }//////////////////////////////////////////////////////////////////////////////////
     
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
            {  JOptionPane.showMessageDialog(null, "Patient not found");}
            
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
         public void ViewAllPatients(){/////////////////////////////////////
             String PatientInfo[] = new String[6];
        String query = "SELECT * FROM Patients";  
        int j=0;
        try{
            conn = DriverManager.getConnection(DBSLocation);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            while(rs.next()){
                PatientInfo[0] = (String)rs.getString("ID");
                PatientInfo[1] = (String)rs.getString("Name");
                PatientInfo[2] = (String)rs.getString("Surname");
                 PatientInfo[3] = (String)rs.getString("DoctorCode");
                PatientInfo[4] = (String)rs.getString("Address");
                PatientInfo[5] = (String)rs.getString("Status");
                
                for(int i = 0; i<5; i++){
                     HealthVisitorTable.setValueAt(PatientInfo[i], j, i);
                }
                j++;
                
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }/////////////////////////////////////////////////////////////////////////
         
         
                public void ViewAPatient(String search){//method to enter a specific patient ID to view PATIENTS with that ID
                    String PatientInfo[] = new String[6];
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
                PatientInfo[5] = (String)rs.getString("Status");
                flag=true;
                 for(int i = 0; i<5; i++){
                     HealthVisitorTable.setValueAt(PatientInfo[i], j, i);
                }
                
            }
            if(flag ==false)
            {  JOptionPane.showMessageDialog(null, "Patient not found");}
            
       
        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
