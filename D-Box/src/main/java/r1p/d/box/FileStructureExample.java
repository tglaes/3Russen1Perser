/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TreeGrid;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Vadim
 */
public class FileStructureExample extends HorizontalLayout implements View {

    
    public FileStructureExample() {
        Grid<TreeFile> dir = new Grid<>();
        dir.setItems(Database.GetUserStandardFolders(1)
                .stream()
                .map(f -> new TreeFile(f)));
        
        dir.addContextClickListener(new ContextClickListener()
	{
		@Override
		public void contextClick(ContextClickEvent event)
		{
                   int id = ((TreeFile)dir.getSelectedItems().toArray()[0]).getID();
                   dir.setItems(Database.GetFolderContent(id)
                        .stream()
                        .map(f -> new TreeFile(f)));
                    
		}
	});
        
        dir.addColumn(TreeFile::getName).setCaption("Name");
        dir.addColumn(TreeFile::getLength).setCaption("Lenght");
        dir.addColumn(TreeFile::LastModDate).setCaption("Change Date");
        dir.addColumn(TreeFile::getID).setCaption("User ID");
        
        this.addComponent(dir);
        
    }
    
    class TreeFile{
        DBoxFile df;
        File file;
        
        TreeFile(DBoxFile f){
            
            df = f;
            file = new File(Utils.FILE_PATH + df.getPath());
        }
        
        String getName(){
            return file.getName();
        }
        
        long getLength(){
            return file.length();
        }
        
        int getID(){
            return df.getID();
        }
        
        Date LastModDate(){
            return new Date(file.lastModified());
        }
        
        int getUserId(){
            return df.getUserID();
        }
        
        DocumentType getType() {
        return df.getType();
        }
        
        List<TreeFile> getList(){ 
            List<TreeFile> dir = new ArrayList<>();
            if (file.isDirectory()){
                for (File f : file.listFiles()){
                }
            }
            return dir;
        }
        
    }
    
}
