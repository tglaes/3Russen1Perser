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
 *
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
     */
    public static boolean Connect() {

        if (connection != null) {
            return true;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + Utils.DATA_BASE_PATH + "Database\\DBoxDB.db";
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
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
        return new User(UserID, email, firstname, lastname);
    }

    /**
     * @return The created User object, or null if the user could not be
     * created.
     */
    public static User CreateNewUser(String email, String password, String firstname, String lastname) {
        return null;
    }

    /**
     * @return The User object if the credentials are correct, otherwise null.
     */
    public static User CheckLogin(String email, String password) {
        return null;
    }

    /**
     * Deletes all references and the physical file.
     *
     * @return True if the file was deleted, false otherwise.
     */
    public static boolean DeleteFile(int fileID) {
        return false;
    }

    /**
     * Deletes one specific reference.
     *
     * @return True if the reference was deleted, false otherwise.
     */
    public static boolean DeleteFileReference(int fileID, int parentFileID) {
        return false;
    }

    /**
     * Creates a new File in the a specific folder.
     *
     * @return True if the file was created, false otherwise.
     */
    public static boolean CreateNewFile(File f, int parentFileID) {
        return false;
    }

    /**
     * Moves the file from one folder to another.
     *
     * @return True if the file was moved, false otherwise.
     */
    public static boolean MoveFile(int fileID, int oldParentID, int newParentID) {
        return false;
    }

    /**
     * Shares a file with a user.
     *
     * @return True if the file was shared, false otherwise.
     */
    public static boolean ShareFile(int fileID, String userEmail) {
        return false;
    }

    /**
     * @return A list with the 3 topmost folders of the user, null otherwise.
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
                    
                    DBoxFile df = new DBoxFile(ID, UserID, Utils.IntToDocumentType(Type), Path);
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

    public static List<DBoxFile> GetFolderContent(int folderID) {
        return null;
    }

    /**
     *
     * @return
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
     * @return
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
}
