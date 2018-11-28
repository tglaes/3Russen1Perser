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
    public static boolean DeleteFile(int fileID) {
        return false;
    }

    /**
     * Deletes one specific reference of a file.
     * @param fileID The file which reference should be deleted.
     * @param parentFileID The folder with the reference to the file.
     * @return @return True if the reference was deleted, false otherwise.
     */
    public static boolean DeleteFileReference(int fileID, int parentFileID) {
        return false;
    }

    /**
     * Creates a new File in the a specific folder.
     * @param f The file object.
     * @param parentFileID The folder in which the new file should be placed.
     * @return True if the file was created, false otherwise.
     */
    public static boolean CreateNewFile(File f, int parentFileID) {
        return false;
    }

    /**
     * Moves the file from one folder (source) to another (destination).
     * @param fileID The file that should be moved.
     * @param oldParentID The source folder.
     * @param newParentID The destinaton folder.
     * @return True if the file was moved, false otherwise.
     */
    public static boolean MoveFile(int fileID, int oldParentID, int newParentID) {
        return false;
    }
   
    /**
     * Shares a file with another user.
     * @param fileID The file to share.
     * @param userEmail The email of the person the file is shared with.
     * @return True if the file was shared, false otherwise.
     */
    public static boolean ShareFile(int fileID, String userEmail) {
        return false;
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
        return null;
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
