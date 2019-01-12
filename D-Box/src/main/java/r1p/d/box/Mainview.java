/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
/**
 *
 * @author Vadim
 */
public class Mainview extends VerticalLayout implements View {
    int lastFolderId = 1;
    
    public Mainview() {
       Grid<TreeFile> dir = new Grid<>();
       
       //Springt immer in die default ausgabe der Ordner
        Button navigationButton = new Button("DBOX", e ->{
             dir.setItems(Database.GetUserStandardFolders(1)
                        .stream()
                        .map(f -> new TreeFile(f)));
             System.out.println(1);
        });
        navigationButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        
        //Gibt uns die ausgabe der StandardFolder
        dir.setItems(Database.GetUserStandardFolders(1)
                .stream()
                .map(f -> new TreeFile(f)));
        
   
        dir.addContextClickListener(new ContextClickListener()
	{
		@Override
		public void contextClick(ContextClickEvent event) {
                    
                   int id = ((TreeFile)dir.getSelectedItems().toArray()[0]).getID();
                   lastFolderId = id;
                   dir.setItems(Database.GetFolderContent(id)
                        .stream()
                        .map(f -> new TreeFile(f)));
		}
	});
        
        
        dir.addColumn(f -> {
            String iconHtml = null;
            if (f.getType().equals(DocumentType.Normal)) {
                iconHtml = VaadinIcons.FILE_O.getHtml();
            } else {
                iconHtml = VaadinIcons.FOLDER_O.getHtml();
            }
            return iconHtml + " "
                    + Jsoup.clean(f.getName(), Whitelist.basicWithImages());
        }, new HtmlRenderer()).setCaption("Name").setId("file-name");
        
        dir.addColumn(TreeFile::getLength).setCaption("Lenght");
        dir.addColumn(TreeFile::LastModDate).setCaption("Change Date");
        dir.addColumn(TreeFile::getID).setCaption("User ID");
        
        
        this.addComponents(navigationButton,dir);
        
    }
    
    class TreeFile{
        DBoxFile df;
        File file;
        
        TreeFile(DBoxFile f){
            
            df = f;
            file = new File(Utils.FILE_PATH + df.getPath());
        }
        
        boolean isFile(){
            if(file.isFile()){
                return true;
            }
            
            else{
                return false;
            }
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
