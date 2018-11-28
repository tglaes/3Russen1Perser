/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

/**
 *
 * @author Tristan Glaes
 */
public class DBoxFile implements java.io.Serializable {
    
    private int ID;
    private int UserID;
    private DocumentType Type;
    private String Path;

    public DBoxFile() {}
    
    public DocumentType getType() {
        return Type;
    }
    
    public void setType(DocumentType t){
        Type = t;
    }
    
    public int getID(){
        return ID;
    }
    
    public void setID(int id){
        ID = id;
    }
    
    public int getUserID(){
        return UserID;
    }
    
    public void setUserID(int userID){
        UserID = userID;
    }
    
    public String getPath(){
        return Path;
    }
    
    public void setPath(String path){
        Path = path;
    }
}