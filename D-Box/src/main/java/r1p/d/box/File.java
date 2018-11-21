/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.util.Date;

/**
 *
 * @author Tristan Glaes
 */
public class File implements Document{
    
    private String Name;
    private String Path;
    private int ID;  
    private int UserID;
    private int Size;
    private Date ChangeDate;
    private DocumentType Type;
    
    public File(String name, String path, int id, int userID, int size, Date changeDate, DocumentType type){
        this.Name = name;
        this.Path = path;
        this.ID = id;
        this.UserID = userID;
        this.Size = size;
        this.ChangeDate = changeDate;
        this.Type = type;
    }

    @Override
    public int GetOwner() {
        return UserID;
    }

    @Override
    public int GetID() {
        return ID;
    }

    @Override
    public DocumentType GetType() {
        return Type;
    }

    @Override
    public String GetPath() {
        return Path;
    }

    @Override
    public String GetName() {
        return Name;
    }

    @Override
    public int GetSize() {
        return Size;
    }

    @Override
    public Date GetChangeDate() {
        return ChangeDate;
    }
    
}
