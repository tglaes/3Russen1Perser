/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.util.List;

/**
 *
 * @author Tristan Glaes
 */
public class User implements java.io.Serializable {
    
    private int UserID;
    
    private String EMail;
    
    private String FirstName;
    
    private String LastName;
    
    public User(){}
    
    public int getUserID(){
        return UserID;
    }
    
    public void setUserID(int userID){
        UserID = userID;
    }
    
    public String getEmail(){
        return EMail;
    }
    
    public void setEmail(String email){
        EMail = email;
    }
    
    public String getFirstname(){
        return FirstName;
    }
    
    public void setFirstname(String firstname){
        FirstName = firstname;
    }
    
    public String getLastname(){
        return LastName;
    }
    
    public void setLastname(String lastname) {
        LastName = lastname;
    }
    
    public String getFullName(){
        return FirstName + ", " + LastName;
    }
}