/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.util.Date;
import java.util.List;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author Tristan Glaes
 */
public class Folder implements Document {
    
    private int ID;
    private int UserID;
    private String Name;
    private DocumentType Type;
    private Date ChangeDate;
    private String Path;
    private List<Document> Children;
    
    public Folder(int id, int userID, String name, DocumentType type, Date changeDate, String path, List<Document> children){
        this.ID = id;
        this.UserID = userID;
        this.Name = name;
        this.Type = type;
        this.ChangeDate = changeDate;
        this.Path = path;
        this.Children = children;
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
        int size = 0;
        
        for(Document d : Children){
            
            size += d.GetSize();
        }
        return size;
    }

    @Override
    public Date GetChangeDate() {
        return ChangeDate;
    }
    
}
