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
public interface Document {
    
    int GetOwner();
    
    int GetID();
    
    DocumentType GetType();
    
    String GetPath();
    
    String GetName();
    
    int GetSize();
    
    Date GetChangeDate();
    
}
