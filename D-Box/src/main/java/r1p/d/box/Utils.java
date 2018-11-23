/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.io.File;

/**
 *
 * @author Tristan Glaes
 */
public class Utils {
    
    public static final String DATA_BASE_PATH = "C:\\Users\\Tristan Glaes\\Documents\\3Russen1Perser\\D-Box\\src\\main\\data\\";
    
    public static void CheckRootFolder(){
        try {
            String rootDirectoryPath = DATA_BASE_PATH + "Root\\";

            File rootFolder = new File(rootDirectoryPath);
            if (!rootFolder.exists()) {
                rootFolder.mkdir();
            }
        } catch (Exception ex){
            System.err.println(ex.toString());
        } 
    }
    
    public static DocumentType IntToDocumentType(int type) {
        
        DocumentType docType;
        
        switch (type) {
            case 0:  docType = DocumentType.Shared;
                     break;
            case 1:  docType = DocumentType.Private;
                     break;
            case 2:  docType = DocumentType.Public;
                     break;
            case 3:  docType = DocumentType.Normal;
                     break;
            default: docType = null;
                     break;
        }
        
        return docType;
    }
    
    public static int DocumentTypeToInt(DocumentType docType) {
        
        int type;
        
        switch (docType) {
            case Shared:  type = 0;
                     break;
            case Private:  type = 1;
                     break;
            case Public:  type = 2;
                     break;
            case Normal:  type = 3;
                     break;
            default: type = -1;
                     break;
        }
        
        return type;
    }
}
