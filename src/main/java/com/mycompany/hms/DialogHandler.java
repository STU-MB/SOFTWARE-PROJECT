/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.hms;
import java.nio.file.Paths;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author muham
 */
public class DialogHandler {
//    private TableModelEvent evt;
    private String url; 
    private Connection conn;
    private Statement s;
    private ResultSet rs;
    private String query;
    private String tableName;
    private JTable table;
    private JDialog dialog;
    
    //setter methods
    public void setTable(JTable table){
        this.table = table;
    }
    
    public void setAddDialog(JDialog dialog){
        this.dialog = dialog;
    }
    
    public DialogHandler( String DBSLocation){
//        this.evt = evt;
        
        this.url = "jdbc:ucanaccess://" + Paths.get(DBSLocation).toAbsolutePath().toString();
    }
    
//    public void editPatientOld(){
//        
//        String columns[] = {"ID", "Surname", "Name", "RoomNumber", "Condition"};
//        //for(int i = 0; i<count; i++)columns[i] = ((TableModel) evt.getSource()).getColumnName(i);
//        
//        //find change row and column
//        int row = evt.getFirstRow();
//        int column  = evt.getColumn();
//        
//        //Generate query
////        if(columns[column].equals("Condition"))query = "UPDATE PatientIllnesses SET WHERE PatientID = P00" + (row+1);
////            else query = "UPDATE Patients SET " + columns[column] +  " = " + ((TableModel) evt.getSource()).getValueAt(row,column) + " WHERE ID = P00" + (row+1);
//        if(column != 4)query = "UPDATE Patients SET " + columns[column] +  " = " + ((TableModel) evt.getSource()).getValueAt(row,column) + " WHERE ID = 'P00" + (row+1) +"'";
//        else query = "UPDATE PatientIllnesses SET IllnessID = " + ((TableModel) evt.getSource()).getValueAt(row,column) + " WHERE PatientID = 'P00" + (row+1) + "'";
//        
//        try{
//            conn = DriverManager.getConnection(url); 
//            s = conn.createStatement(); 
//            rs = s.executeQuery(query);
//        }catch(SQLException e){
//            System.out.println(e);
//            JOptionPane.showMessageDialog(null, "Please enter valid Information");
//        }
//    }
//    
    //generates a new ID Number
    public String generateID(char IDLetter, String tableName){
        query = "SELECT * FROM " + tableName;
        int IDNumber = 0;
        try{
            conn = DriverManager.getConnection(url); 
            s = conn.createStatement(); 
            rs = s.executeQuery(query);
            while(rs.next())IDNumber++;
        }catch(SQLException e){
            System.out.println(e);
        }
        IDNumber++;
        return IDLetter + "00" + IDNumber;
        
        
        
    }
    
    public void savePatient(){
        String columns[] = {"ID", "Surname", "Name", "RoomNumber"};//condition
        String values[] = new String[4];
                try{
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<4; i++){
                query = "UPDATE Patients SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE ID = '" + table.getValueAt(j, 0) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
        }
        
        for(int i=0; i<table.getRowCount(); i++){
            query = "UPDATE PatientIllnesses SET IllnessID = '" + table.getValueAt(i, 4) + "' WHERE PatientID = '" + table.getValueAt(i, 0) + "'";
             
            try{
                
                PreparedStatement p = conn.prepareStatement(query); 
                p.executeUpdate();
            }catch(SQLException e){
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Please enter valid Information");
            }
        }
    }
    
    public void displayPatientEditForm(){
        String PatientInfo[] = new String[4];
        String query = "SELECT * FROM Patients";  
        
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                PatientInfo[0] = (String)rs.getString("ID");
                PatientInfo[2] = (String)rs.getString("Name");
                PatientInfo[1] = (String)rs.getString("Surname");
                PatientInfo[3] = (String)rs.getString("RoomNumber");
                //PatientInfo[5] = (String)rs.getString("Status");
//                query = "SELECT * FROM PatientIllnesses WHERE PatientID = '" + (String)rs.getString("ID") + "'";
//                //query = "SELECT * FROM PatientIllnesses WHERE PatientID = 'P001'";
//                rs1 = s.executeQuery(query);
//                rs1.next();
//                query = "SELECT * FROM Illnesses WHERE IllnessCode = '" + (String)rs1.getString("IllnessID") + "'";
//                rs1 = s.executeQuery(query);
//                rs1.next();
//                AppointmentInfo[3] = (String)rs1.getString("Illness");//should be condition
                
                for(int i = 0; i<4; i++){
                     table.setValueAt(PatientInfo[i], j, i);
                     
                }
                query = "SELECT * FROM PatientIllnesses WHERE PatientID = '" + rs.getString("ID") + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                table.setValueAt(rs1.getString("IllnessID"), j, 4);
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void displayDoctorEditForm(){
        String[] DoctorInfo = new String[3];
        String query = "SELECT * FROM Doctors";  
        
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                DoctorInfo[0] = (String)rs.getString("ID");
                DoctorInfo[2] = (String)rs.getString("Name");
                DoctorInfo[1] = (String)rs.getString("Surname");
                
                
                
                for(int i = 0; i<3; i++){
                     table.setValueAt(DoctorInfo[i], j, i);
                     
                }
                query = "SELECT * FROM DoctorSpecialisations WHERE DoctorID = '" + rs.getString("ID") + "'";
                rs1 = s.executeQuery(query);
                rs1.next();
                table.setValueAt(rs1.getString("SpecCode"), j, 3);
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void saveDoctor(){
        String columns[] = {"ID", "Surname", "Name"};//spec
        
                try{
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<3; i++){
                query = "UPDATE Doctors SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE ID = '" + table.getValueAt(j, 0) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
        }
        
        for(int i=0; i<table.getRowCount(); i++){
            query = "UPDATE DoctorSpecialisations SET SpecCode = '" + table.getValueAt(i, 3) + "' WHERE DoctorID = '" + table.getValueAt(i, 0) + "'";
             
            try{
                
                PreparedStatement p = conn.prepareStatement(query); 
                p.executeUpdate();
            }catch(SQLException e){
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Please enter valid Information");
            }
        }
    }
    
    public void displayAppointmentEditForm(){
        String[] AppointmentInfo = new String[4];
        String query = "SELECT * FROM Appointments";  
        boolean attended;
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                AppointmentInfo[0] = (String)rs.getString("DoctorCode");
                AppointmentInfo[1] = (String)rs.getString("PatientCode");
                AppointmentInfo[2] = (String)rs.getString("NurseCode");
                AppointmentInfo[3] = (String)rs.getString("RoomNumber");
                Time t = rs.getTime("StartTime");
                attended = rs.getBoolean("Completed");
                
                
                for(int i = 0; i<4; i++){
                     table.setValueAt(AppointmentInfo[i], j, i);
                     
                }
                
                table.setValueAt(t, j, 4);
                table.setValueAt(attended, j, 5);
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void saveAppointments(){
        String columns[] = {"DoctorCode", "PatientCode", "NurseCode", "RoomNumber", "StartTime", "Completed"};//spec
        
                try{
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<4; i++){
                query = "UPDATE Appointments SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE SlotNum = 'S" + (j+1) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
            query = "UPDATE Appointments SET " + columns[4] + " = #" + table.getValueAt(j, 4) + "# WHERE SlotNum = 'S" + (j+1) + "'" ;
            try{
                    
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
           
           query = "UPDATE Appointments SET " + columns[5] + " = '" + table.getValueAt(j, 5) + "' WHERE SlotNum = 'S" + (j+1) + "'" ;
           try{
                    
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
           
        }
        
//        for(int i=0; i<table.getRowCount(); i++){
//            query = "UPDATE DoctorSpecialisations SET SpecCode = '" + table.getValueAt(i, 3) + "' WHERE DoctorID = '" + table.getValueAt(i, 0) + "'";
//             
//            try{
//                
//                PreparedStatement p = conn.prepareStatement(query); 
//                p.executeUpdate();
//            }catch(SQLException e){
//                System.out.println(e);
//                JOptionPane.showMessageDialog(null, "Please enter valid Information");
//            }
//        }
    }
    
    public void displayIllnessEditForm(){
        String[] IllnessInfo = new String[2];
        String query = "SELECT * FROM Illnesses";  
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                IllnessInfo[0] = (String)rs.getString("IllnessCode");
                IllnessInfo[1] = (String)rs.getString("Illness");
               
                
                
                for(int i = 0; i<2; i++){
                     table.setValueAt(IllnessInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e + "\nUnable to view Illnesses");
            
        }
    }
    
    public void saveIllnesses(){
        String columns[] = {"IllnessCode", "Illness",};//spec
        
                try{
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<2; i++){
                query = "UPDATE Illnesses SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE IllnessCode = 'I1" + (j+1) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
        }
        
        
    }
    
    public void displaySpecsEditForm(){
        String[] DoctorInfo = new String[2];
        String query = "SELECT * FROM Specialisations";  
        
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            ResultSet rs1;
            //PatientInfo[2] = (String)rs.getString("ID");//should be condition
            
            
            while(rs.next()){
                DoctorInfo[0] = (String)rs.getString("SpecCode");
                DoctorInfo[1] = (String)rs.getString("Specialisation");
                
                
                
                
                for(int i = 0; i<2; i++){
                     table.setValueAt(DoctorInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void saveSpecs(){
        String columns[] = {"SpecCode", "Specialisation",};//spec
        
                try{    
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<2; i++){
                query = "UPDATE Specialisations SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE SpecCode = 'S1" + (j+1) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
        }
    }
    
    public void displayNurseEditForm(){
        String[] nurseInfo = new String[3];
        String query = "SELECT * FROM Nurses";  
        
        int j=0;
        try{
            conn = DriverManager.getConnection(url);
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            
            
            
            while(rs.next()){
                nurseInfo[0] = (String)rs.getString("ID");
                nurseInfo[1] = (String)rs.getString("Surname");
                nurseInfo[2] = (String)rs.getString("Name");
                
                
                
                for(int i = 0; i<3; i++){
                     table.setValueAt(nurseInfo[i], j, i);
                     
                }
                
                j++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
    
    public void saveNurse(){
        String columns[] = {"ID", "Surname", "Name"};//spec
        
                try{    
                    conn = DriverManager.getConnection(url);  
                }catch(SQLException e){
                    System.out.println(e);
                    
                }
        
        
        for(int j = 0; j<table.getRowCount(); j++){
            for(int i=0; i<3; i++){
                query = "UPDATE Nurses SET " + columns[i] + " = '" + table.getValueAt(j, i) + "' WHERE ID = 'N00" + (j+1) + "'" ;
            
                try{
                    //conn = DriverManager.getConnection(url); 
                    PreparedStatement p = conn.prepareStatement(query); 
                    p.executeUpdate();
                }catch(SQLException e){
                    System.out.println(e);
                    //JOptionPane.showMessageDialog(null, "Please enter valid Information");
                }
            }
        }
    }
    
    //add Forms
    public void doctorAddButton(){
        SwingUtilities.updateComponentTreeUI(dialog);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    public void cppointmentAddButton(){
        
    }
    
    public void nurseAddButton(){
        
    }
    
    public void patientAddButton(){
        
    }
    
    public void specAddButton(){
        
    }
    
    public void illnessAddButton(){
        
    }
    
    
//delete button code
    
    public void deleteDoctor(){
        
    }
    
    public void deleteAppointment(){
        
    }
    
    public void deleteNurse(){
        
    }
    
    public void deletePatient(){
        
    }
    
    public void deleteSpec(){
        
    }
    
    public void deleteIllness(){
        
    }
    
}
