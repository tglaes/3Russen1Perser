/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import java.sql.*;

/**
 *
 * @author Tristan Glaes
 */
public class Database {
       
    private static Connection connection;
    
    /**
     *   Establishes a connection to the local database.
     */
    public static boolean Connect(){
        
        if (connection != null) {
            return true;
        }
        
        try {
            String url = "jdbc:sqlite:C:\\Users\\Tristan Glaes\\Documents\\3Russen1Perser\\D-Box\\src\\main\\resources\\DBoxDB.db";
            connection = DriverManager.getConnection(url);      
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }   
}