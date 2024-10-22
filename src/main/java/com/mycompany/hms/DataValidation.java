/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.hms;

/**
 *
 * @author Muhammad Buckus
 */
public class DataValidation {
    String validateString;
    
    
    public DataValidation(String s){
        this.validateString = s;
    }
    
    public boolean validateLettersOnly(){
       return validateString.matches("[a-zA-Z]+"); 
       }
    
    public void setString(String s){
        this.validateString = s;
    }
}
