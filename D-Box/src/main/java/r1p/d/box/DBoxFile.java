/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.io.File;
import java.net.URI;

/**
 *
 * @author Tristan Glaes
 */
public class DBoxFile extends File{
    
    private int ID;
    private int UserID;
    private DocumentType Type;

    public DBoxFile(int ID, int UserID, DocumentType Type, String pathname) {
        super(pathname);
        this.ID = ID;
        this.UserID = UserID;
        this.Type = Type;
    }
    
    public DocumentType getType() {
        return Type;
    }
    
    public int GetID(){
        return ID;
    }
    
    public int GetUserID(){
        return UserID;
    }
}
