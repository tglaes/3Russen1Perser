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
        return this.userId;
    }
    
    public void setUserID(int userID){
        this.userId = userID;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public String getLastname(){
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getFullName(){
        return this.firstname + ", " + this.lastname;
    }
}