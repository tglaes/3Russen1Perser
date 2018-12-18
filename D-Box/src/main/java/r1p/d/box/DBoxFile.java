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
    
    private int id;
    private int userId;
    private DocumentType type;
    private String path;

    public DBoxFile() {}
    
    public DocumentType getType() {
        return type;
    }
    
    public void setType(DocumentType t){
        type = t;
    }
    
    public int getID(){
        return id;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getUserID(){
        return userId;
    }
    
    public void setUserID(int userID){
        userId = userID;
    }
    
    public String getPath(){
        return path;
    }
    
    public void setPath(String path){
        this.path = path;
    }
}