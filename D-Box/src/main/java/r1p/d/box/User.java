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
    
    private List<Folder> UserFolders;
    
    public int GetUserID(){
        return UserID;
    }
    
    public String GetEmail(){
        return EMail;
    }
    
    public String GetFullName(){
        return FirstName + ", " + LastName;
    }
    
    public Folder GetSharedFolder(){
        return null;
    }
    
    public Folder GetPrivateFolder() {
        return null;
    }
    
    public Folder GetPublicFolder() {
        return null;
    }
}
