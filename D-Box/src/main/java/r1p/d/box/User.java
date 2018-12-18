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
    
    private int userId;
    
    private String email;
    
    private String firstname;
    
    private String lastname;
    
    public User(){}
    
    public int getUserID(){
        return userId;
    }
    
    public void setUserID(int userID){
        userId = userID;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public void setFirstname(String firstname){
        firstname = firstname;
    }
    
    public String getLastname(){
        return lastname;
    }
    
    public void setLastname(String lastname) {
        lastname = lastname;
    }
    
    public String getFullName(){
        return firstname + ", " + lastname;
    }
}