/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represent a Login Window 
 * @author Mahan
 */
public class LoginFenster extends VerticalLayout {
    //**********************************Properties***************************//
    private Label label ;
    private TextField email ; 
    private PasswordField passwort ; 
    private Button confirmLogin ;
    private Link linkToReg ; 
    private User user ;
    private Map<Integer,User> activeUser;
    
    
    public LoginFenster () {
        Database.Connect();
        this.activeUser = new HashMap<>();
        this.label = new Label("Please Login");
        this.email = new TextField(); 
        this.passwort = new PasswordField();
        this.confirmLogin = new Button("Login");
        this.linkToReg = new Link("Google search", new ExternalResource(
                "http://www.google.com"));
        
        //set properties for the control email
        email.setCaption("Enter Your E-Mail");
        email.setIcon(FontAwesome.USER);
        
        
        passwort.setCaption("Enter Your Passwort");
        passwort.setIcon(FontAwesome.KEY);
        passwort.setDescription("Confirm your Login");
        linkToReg.setDescription("Click to Sign up");
        
        confirmLogin.addClickListener((event) -> {
           List<User> test = new ArrayList<User>();
           
            user = Database.CheckLogin(email.getValue(), passwort.getValue());
            
            activeUser.put(user.getUserID(), user);
            
          showAllActiveUser();
            logout(user.getUserID());
            showAllActiveUser();
        });
        
        
        
       
        addComponent(label);
        addComponent(email);
        addComponent(passwort);
        addComponent(confirmLogin);
        addComponent(linkToReg);
        setStyleName("blue");
       
    }
    
    /**
     * Prints all active User out
     */
    public void showAllActiveUser () {
        for (Integer key : activeUser.keySet()) {
    User user = activeUser.get(key);
    System.out.println("Key = " + key + ", Value = " + user.toString());
   }
        
    }
    
    /**
     * A function to give all user back 
     * @param onlineUsersMap - the current Map of all active User
     * @return a list where all user are contained 
     */
    public List<User> getAllOnlineUsers(Map<Integer,User> onlineUsersMap) { 
        List <User> onlineUser = new ArrayList<User>(); 
        User user = new User();
        for(Integer key :onlineUsersMap.keySet()) { 
            user = onlineUsersMap.get(key); 
            onlineUser.add(user);
        }
        
        
        return onlineUser ;
    }
    
    /** 
     * A function that removes a user from the map wich means he is offline 
     * @param userId - the user id wich are not more loggin  
     */
    public void logout (int userId) {
     this.activeUser.remove(userId);
    }
    
    
}
