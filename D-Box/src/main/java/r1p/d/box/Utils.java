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
    
    public static void CheckRootFolder(){
        try {
            String property = "java.io.tmpdir";
            String rootDirectoryPath = System.getProperty(property) + "DBox/Root/";

            File rootFolder = new File(rootDirectoryPath);
            if (!rootFolder.exists()) {
                rootFolder.mkdir();
            }
        } catch (Exception ex){
            System.err.println(ex.toString());
        } 
    }   
}
