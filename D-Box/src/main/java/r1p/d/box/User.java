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
public class User {
    
    private int UserID;
    
    private String EMail;
    
    private String FirstName;
    
    private String LastName;
    
    private List<DBoxFile> UserFolders;
    
    public User(int UserID, String Email, String FirstName, String LastName){
        this.UserID = UserID;
        this.EMail = EMail;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }
    
    public User(String EMail, String password ,String FirstName, String LastName){
        this.EMail = EMail;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }
    
    public int GetUserID(){
        return UserID;
    }
    
    public String GetEmail(){
        return EMail;
    }
    
    public String GetFullName(){
        return FirstName + ", " + LastName;
    }
    
    public DBoxFile GetSharedFolder(){
        return null;
    }
    
    public DBoxFile GetPrivateFolder() {
        return null;
    }
    
    public DBoxFile GetPublicFolder() {
        return null;
    }
}
