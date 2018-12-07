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
    
    // Tristan
    public static final String PROJECT_PATH = "C:\\Users\\Tristan Glaes\\Documents\\3Russen1Perser\\";
    // Mahan
    // Vadim
    // Iurie
    public static final String DATA_BASE_PATH = PROJECT_PATH + "D-Box\\src\\main\\data\\Database\\DBoxDB.db";
    public static final String FILE_PATH = PROJECT_PATH + "D-Box\\src\\main\\data\\Root\\";
    
    public static void CheckRootFolder(){
        try {

            File rootFolder = new File(FILE_PATH);
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
    
    /**
     * Create a file archieve for a new user.
     * @param UserID The Id of the user.
     * @return True, if the archieve was successfully created, false otherwise.
     */
    public static boolean CreateUserArchieve(int UserID){
        
        try {
            File userRootFolder = new File(FILE_PATH + UserID);
            if (userRootFolder.mkdir()) {
                return true;              
            } else {
                System.err.println("Could not create root folder for user: " + UserID);
                return false;
            }
                    
        } catch(Exception ex) {
            System.err.println(ex.toString());
            return false;
        }
    }
    
    public static File GetFile(String path){
        return null;
    }
    
    public static String GetRelativePath(String path){
        return null;
    }
}
