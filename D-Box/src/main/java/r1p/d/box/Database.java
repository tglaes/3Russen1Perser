/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 0.1
 * @author Tristan Glaes
 */
public class Database {

    public static final String USER_EMAIL = "Email";
    public static final String USER_FIRSTNAME = "Firstname";
    public static final String USER_LASTNAME = "Lastname";
    public static final String USER_ID = "ID";
    public static final String USER_PASSWORD = "Password";
    
    public static final String FOLDER_ID = "ID";
    public static final String FOLDER_PATH = "Path";
    public static final String FOLDER_TYPE = "Type";
    public static final String FOLDER_USERID = "UserID";
    
    public static final String FILE_ID = "ID";
    public static final String FILE_PATH = "Path";
    public static final String FILE_USERID = "UserID";
            

    private static Connection connection;

    /**
     * Establishes a connection to the local database.
     * @return True if successfull, false otherwise.
     */
    public static boolean Connect() {

        if (connection != null) {
            return true;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + Utils.DATA_BASE_PATH;
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @param UserID The ID of the User.
     * @return The user object.
     */
    public static User GetUser(int UserID) {

        String email;
        String firstname;
        String lastname;
        
        String sql = "SELECT * FROM Users WHERE ID=" + UserID;
        ResultSet rs = ExecuteSqlWithReturn(sql);
        try {
            if (rs != null) {
                if (rs.next()) {
                    email = rs.getString(USER_EMAIL);
                    firstname = rs.getString(USER_FIRSTNAME);
                    lastname = rs.getString(USER_LASTNAME);
                    User u = new User();
                    u.setEmail(email);
                    u.setUserID(UserID);
                    u.setFirstname(firstname);
                    u.setLastname(lastname);
                    return u;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    
    public static DBoxFile GetFile(int fileID){
        
        DBoxFile file = new DBoxFile();
        
        try
        {
            String sql = "SELECT * FROM Files WHERE ID=" + fileID;
            ResultSet rs = ExecuteSqlWithReturn(sql);
            if (rs != null) {
                if (rs.next()) {
                    
                    file.setID(fileID);
                    file.setPath(rs.getString(FILE_PATH));
                    file.setType(DocumentType.File);
                    file.setUserID(rs.getInt(FILE_USERID));
                }
            }         
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return file;
    }
    
    public static DBoxFile GetFolder(int folderID){
        
        DBoxFile file = new DBoxFile();
        
        try
        {
            String sql = "SELECT * FROM Folders WHERE ID=" + folderID;
            ResultSet rs = ExecuteSqlWithReturn(sql);
            if (rs != null) {
                if (rs.next()) {                  
                    file.setID(folderID);
                    file.setPath(rs.getString(FOLDER_PATH));
                    file.setType(Utils.IntToDocumentType(rs.getInt(FOLDER_TYPE)));
                    file.setUserID(rs.getInt(FOLDER_USERID));
                }
            }         
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return file;
    }
    
    /**
     * 
     * @param email The e-mail of the user.
     * @param password The password of the user
     * @param firstname The firstname of the user.
     * @param lastname The lastname of the user.
     * @return The created User object, or null if the user could not be created.
     */
    public static User CreateNewUser(String email, String password, String firstname, String lastname) {
        
        String sql = "INSERT INTO Users (" + USER_EMAIL + ", " + USER_PASSWORD + ", " + USER_FIRSTNAME + ", " + USER_LASTNAME + ") VALUES('" + email + "', '" + password + "', '" + firstname + "','" + lastname + "')";
        int UserID = 0;
        
        try {
            // Create the user in the database.
            if (ExecuteSql(sql)) {
                
                if ((UserID = GetLastGeneratedID()) > 0) {
                    // Create the users file archieve.
                    if (Utils.CreateUserArchieve(UserID)) {              
                        // Create the database entries for the basic file structure.
                        if (CreateBasicFileStructure(UserID)) {
                            // Return the new user object.
                            User u = new User();
                            u.setUserID(UserID);
                            u.setEmail(email);
                            u.setFirstname(firstname);
                            u.setLastname(lastname);
                            return u;
                        } else {
                            return null;
                        }                     
                    } else {
                        return null;
                    }                   
                } else {
                    return null;
                }
            } else {
                return null;
            }              
        } catch( Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }      
    }

    /**
     * 
     * @param email The entered Email.
     * @param password The entered Password.
     * @return The User object if the credentials are correct, otherwise null.
     */
    public static User CheckLogin(String email, String password) {
        String sql = "SELECT * FROM Users WHERE Email='" + email + "' AND Password='" + password + "'";
        
        try {
            ResultSet rs = ExecuteSqlWithReturn(sql);
            
            if (rs != null) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserID(rs.getInt(USER_ID));
                    u.setEmail(rs.getString(USER_EMAIL));
                    u.setFirstname(rs.getString(USER_FIRSTNAME));
                    u.setLastname(rs.getString(USER_LASTNAME));              
                    return u;
                } else {
                    System.out.println("No user found with email: " + email + " and password: " + password);
                    return null;
                }           
            } else {
                return null;
            }        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }     
    }

    /**
     * Deletes all references and the physical file.
     * @param fileID The file ID.
     * @return True if the file was deleted, false otherwise.
     */
    public static boolean DeleteFile(DBoxFile f) {
        // Decide if file or folder
        if (f.getType() == DocumentType.File) {
            
            // Save the filepath
            String filePath = f.getPath();
            
            // Delete all references
            String sql = "DELETE FROM FilesFolders WHERE FileID=" + f.getID();
            if (ExecuteSql(sql)) {
                
                // Delete the file database entry
                sql = "DELETE FROM Files WHERE ID=" + f.getID();
                if (ExecuteSql(sql)) {
                    // delete the physical file
                    File file = new File(filePath);
                    return file.delete();
                } else {
                    return false;
                }           
            } else {
                return false;
            }
                     
        } else if (f.getType() == DocumentType.Normal) {
            
            // Delete content
            
            String folderPath = f.getPath();
            String sql = "DELETE FROM FoldersFolders WHERE ChildID=" + f.getID(); 
            // Delete references
            if (ExecuteSql(sql)) {
                
                sql = "DELETE FROM Folders WHERE ID=" + f.getID();
                if (ExecuteSql(sql)) {
                    
                     // Delete the folder itself         
                    File folder = new File(folderPath);
                    return folder.delete();
                    
                } else {
                    return false;
                }              
            } else {
                return false;
            }       
        } else {
            return false;
        }
    }

    /**
     * Deletes one specific reference of a file.
     * @param fileID The file which reference should be deleted.
     * @param parentFileID The folder with the reference to the file.
     * @return @return True if the reference was deleted, false otherwise.
     */
    public static boolean DeleteFileReference(DBoxFile f, int parentFileID) {
        
        // Decide if file or folder
        if (f.getType() == DocumentType.File) {
            String sql = "DELETE FROM FilesFolders WHERE FileID=" + f.getID();
            return ExecuteSql(sql);
            
        } else if (f.getType() == DocumentType.Normal){
            String sql = "DELETE FROM FoldersFolders WHERE ChildID=" + f.getID();
            return ExecuteSql(sql);
            
        } else {
            return false;
        }
    }

    /**
     * Creates a new File in the a specific folder. The file should already be in the folder of the user.
     * @param f The file object.
     * @param parentFileID The folder in which the new file should be placed.
     * @return The id created file or -1.
     */
    public static int CreateNewFile(File f, int parentFileID) {
        
        DBoxFile folder = GetFolder(parentFileID);
        if (folder != null) {
            
            if (f.isDirectory()) {
                
                // Create new folder
                String sql = "INSERT INTO Folders (" + FOLDER_PATH + ", " + FOLDER_USERID + ", " + FOLDER_TYPE + ") VALUES('" + Utils.GetRelativePath(f.getAbsolutePath()) + "', " + folder.getUserID() + ", "  + 3 + ")";
                if (ExecuteSql(sql)) {
                    // Create new entry in folderfolders
                    sql = "INSERT INTO FoldersFolders (ParentID, ChildID) VALUES(" + parentFileID + "," + GetLastGeneratedID() + ")";
                    if (ExecuteSql(sql)) {
                        return GetLastGeneratedID();
                    }
                } else {
                    return -1;
                }             
            } else if (f.isFile()) {
                
                // Create new file
                String sql = "INSERT INTO Files (" + FOLDER_PATH + ", " + FOLDER_USERID + ") VALUES('" + Utils.GetRelativePath(f.getAbsolutePath()) + "', " + folder.getUserID() +")";
                if (ExecuteSql(sql)) {
                    // Create new entry in filesfolder
                    sql = "INSERT INTO FilesFolders (FolderID, FileID) VALUES(" + parentFileID + "," + GetLastGeneratedID() + ")";
                    if (ExecuteSql(sql)) {
                        return GetLastGeneratedID();
                    }
                } else {
                    return -1;
                }          
            }           
        } else {
            return -1;
        }     
        return -1;
    }

    /**
     * Moves the file from one folder (source) to another (destination).
     * @param f The file that should be moved.
     * @param oldParentID The source folder.
     * @param newParentID The destinaton folder.
     * @return True if the file was moved, false otherwise.
     */
    public static boolean MoveFile(DBoxFile f, int oldParentID, int newParentID) {
        
        try {
            // Decide if file or folder
            if (f.getType() == DocumentType.File) {
            String sql = "DELETE FROM FilesFolders WHERE FileID=" + f.getID() + "AND FolderID=" + oldParentID;
                if (ExecuteSql(sql)) {
                    
                    sql = "INSERT INTO FilesFolders (FileID,FolderID) VALUES(" + f.getID() + ", " + newParentID + ")";
                    return ExecuteSql(sql);
                    
                } else {
                    return false;
                }          
        } else if (f.getType() == DocumentType.Normal){
            String sql = "DELETE FROM FoldersFolders WHERE ChildID=" + f.getID() + "AND ParentID=" + oldParentID;
            if (ExecuteSql(sql)) {
                    
                sql = "INSERT INTO FoldersFolders (ChildID, ParentID) VALUES(" + f.getID() + ", " + newParentID + ")";
                return ExecuteSql(sql);
                } else {
                    return false;
                }         
        } else {
            return false;
        }          
        } catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }
   
    /**
     * Shares a file with another user.
     * @param fileID The file to share.
     * @param userEmail The email of the person the file is shared with.
     * @return True if the file was shared, false otherwise.
     */
    public static boolean ShareFile(int fileID, String userEmail) {      
       
        try {  
            // Get user shared folder
            String sql = "SELECT ID FROM Folders WHERE UserID=" + GetUserByEmail(userEmail) + " AND Type=0";
            ResultSet rs = ExecuteSqlWithReturn(sql);
            if (rs != null) {              
                if (rs.next()) {
                    
                    int folderID = rs.getInt(FOLDER_ID);
                    sql = "INSERT INTO FoldersFolders (ParentID,ChildID) VALUES(" + folderID + ", " + fileID + " )";
                    return ExecuteSql(sql);
                    
                } else {
                    return false;
                }             
            } else {
                return false;
            }               
        
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static int GetUserByEmail(String email){
        
        try {
        
            String sql = "SELECT ID FROM Users WHERE Email='" + email + "'";
            ResultSet rs = ExecuteSqlWithReturn(sql);
            if (rs != null) {
                
                if (rs.next()) {
                    
                    return rs.getInt(USER_ID);
                    
                } else {
                    return -1;
                }             
            } else {
                return -1;
            }    
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return -1;
        }       
    }

    /**
     * 
     * @param UserID The ID of the user.
     * @return A list with the 3 topmost folders of the user(Shared, Private, Public), null otherwise.
     */
    public static List<DBoxFile> GetUserStandardFolders(int UserID) {

        List<DBoxFile> folders = new ArrayList<>();
        String sql = "SELECT * FROM Folders WHERE Folders.UserID=" + UserID + " AND Folders.ID Not IN (SELECT ChildID FROM FoldersFolders)";
        ResultSet rs = ExecuteSqlWithReturn(sql);
        try {        
            if (rs != null) {
                while (rs.next()) {
                    
                    int ID = rs.getInt(FOLDER_ID);
                    String Path = rs.getString(FOLDER_PATH);
                    int Type = rs.getInt(FOLDER_TYPE);
                    DBoxFile df = new DBoxFile();
                    df.setID(ID);
                    df.setPath(Path);
                    df.setType(Utils.IntToDocumentType(Type));
                    df.setUserID(UserID);
                    folders.add(df);
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return folders;
    }

    /**
     * 
     * @param folderID
     * @return A list of DBoxFiles that the folder contains.
     */
    public static List<DBoxFile> GetFolderContent(int folderID) {
        // Get all folders
        String sql1 = "SELECT * FROM Folders AS f JOIN FoldersFolders AS ff ON f.ID=ff.ChildID AND ff.ParentID=" + folderID;
        // Get all files
        String sql2 = "SELECT * FROM Files AS f JOIN FilesFolders AS ff ON f.ID=ff.FileID AND ff.FolderID=" + FOLDER_ID;
        List<DBoxFile> files = new ArrayList<DBoxFile>();
        
        try {
            // FOLDERS
            ResultSet rs = ExecuteSqlWithReturn(sql1);
            if (rs != null) {
                
                while(rs.next()){
                    DBoxFile f = new DBoxFile();
                    f.setID(rs.getInt(FOLDER_ID));
                    f.setPath(rs.getString(FOLDER_PATH));
                    f.setType(Utils.IntToDocumentType(rs.getInt(FOLDER_TYPE)));
                    f.setUserID(rs.getInt(FOLDER_USERID));                  
                    files.add(f);
                }
                
            // FILES
                ResultSet rs2 = ExecuteSqlWithReturn(sql2);
            if (rs2 != null) {
                
                while(rs2.next()){
                    DBoxFile f = new DBoxFile();
                    f.setID(rs2.getInt(FILE_ID));
                    f.setPath(rs2.getString(FILE_PATH));
                    f.setType(DocumentType.File);
                    f.setUserID(rs2.getInt(FILE_USERID));                  
                    files.add(f);
                }
                
            } else {
                return null;
            }
            } else {
                return null;
            }
                     
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }   
        return files;
    }

    public static boolean CheckIfUserExists(String email) {
        String sql = "SELECT * FROM Users WHERE email='" + email + "'";
        
        try
        {
            ResultSet rs = ExecuteSqlWithReturn(sql);
            if (rs != null) {
                
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * @param sql The SQL-String to be executed.
     * @return True if the statement was successfully executed, false otherwise.
     */
    private static boolean ExecuteSql(String sql) {
        try {
            Statement sm = connection.createStatement();
            return sm.execute(sql);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * 
     * @param sql The SQL-String to be executed.
     * @return The ResultSet of the query if successfull, null otherwise
     */
    private static ResultSet ExecuteSqlWithReturn(String sql) {
        try {
            Statement sm = connection.createStatement();
            return sm.executeQuery(sql);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @return The last generated ID (Primary Key) by the database.
     */
    private static int GetLastGeneratedID() {
        try {
            String sql = "SELECT last_insert_rowid()";
            ResultSet rs = ExecuteSqlWithReturn(sql);

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    /**
     * 
     * @param UserID The id of the user.
     * @return True if the structure was created, false otherwise.
     */
    private static boolean CreateBasicFileStructure(int UserID) {
        
        String sql = "INSERT INTO Folders (Path, Type, UserID) VALUES(('" + UserID + "\\Shared',0," + UserID + "),('" + UserID + "\\Private',1," + UserID + "),('" + UserID + "\\Public',2, " + UserID + " ))";       
        try {
            return ExecuteSql(sql);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
