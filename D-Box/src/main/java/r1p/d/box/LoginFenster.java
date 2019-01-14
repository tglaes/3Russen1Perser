/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represent a Login Window
 *
 * @author Mahan
 */
public class LoginFenster extends VerticalLayout implements View {

    //**********************************Properties***************************//
    private FormLayout layout = new FormLayout();
    private Label label;
    private TextField email;
    private PasswordField passwort;
    private Button confirmLogin;
    private Button goToReg;
    private User user;
    private static Map<Integer, User> activeUser =  new HashMap<>();

    /**
     * Constructor of the class LoginFenster
     */
    public LoginFenster() {
        // create database Connection
        Database.Connect();
        // get the current view
        Navigator navigator = UI.getCurrent().getNavigator();
        // instanz the controls       
        this.label = new Label("Please Login");
        this.email = new TextField();
        this.passwort = new PasswordField();
        this.confirmLogin = new Button("Login");
        //instanz the goToReg Button and the navigation if the button will execute 
        this.goToReg = new Button("Sign Up", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(MyUI.REGISTATION_VIEW);
            }

        });

        confirmLogin.addClickListener((event) -> {
            if (passwort == null || email == null) {                                   
                Notification.show("Please give a valid Email and Passwort");
            }
            if (passwort.getValue().trim() == "" || email.getValue().trim() == "") {
                Notification.show("Please give a valid Email and Passwort");
            }
              
                 
            user = Database.CheckLogin(email.getValue(), passwort.getValue());
            System.out.println(user.getFullName());
            activeUser.put(user.getUserID(), user);

        });

        //add some css style 
        layout.setCaption("Login Form");
        email.setCaption("Enter Your E-Mail");
        email.setIcon(FontAwesome.USER);
        email.setDescription("Please Enter your E-Mail");
        email.setPlaceholder("Email");
        passwort.setCaption("Enter Your Passwort");
        passwort.setIcon(FontAwesome.KEY);
        passwort.setDescription("Please enter Password");
        passwort.setPlaceholder("Password");
        label.addStyleNames(ValoTheme.LABEL_H2, ValoTheme.LABEL_COLORED);
        confirmLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        goToReg.addStyleName(ValoTheme.BUTTON_LINK);
        goToReg.setDescription("Click To Sign Up");
        addStyleName(ValoTheme.MENU_ROOT);

        //add Controls to view 
        addComponent(label);
        layout.addComponent(email);
        layout.addComponent(passwort);
        layout.addComponent(confirmLogin);
        addComponent(layout);
        addComponent(goToReg);

    }

    /**
     * Prints all active User out
     */
    public void showAllActiveUser() {
        for (Integer key : activeUser.keySet()) {
            User user = activeUser.get(key);
            System.out.println("Key = " + key + ", Value = " + user.toString());
        }

    }

    /**
     * A function to give all user back
     *
     * @param onlineUsersMap - the current Map of all active User
     * @return a list where all user are contained
     */
    public List<User> getAllOnlineUsers(Map<Integer, User> onlineUsersMap) {
        if (onlineUsersMap == null) {
            throw new NullPointerException("The Map was Null");
        }

        List<User> onlineUser = new ArrayList<User>();
        User user = new User();
        for (Integer key : onlineUsersMap.keySet()) {
            user = onlineUsersMap.get(key);
            onlineUser.add(user);
        }

        return onlineUser;
    }

    /**
     * A function that removes a user from the map wich means he is offline
     *
     * @param userId - the user id wich are not more loggin
     */
    public static void logout(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID was 0 or smaller then 0");
        }
        activeUser.remove(userId);
    }

    /**
     * Show a Text if your start the application
     *
     * @param ex
     */
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent ex) {
        Notification.show("Welcome to D-BOX");
    }

    //*******************GETTER and SETTER *********************************
    public FormLayout getLayout() {
        return layout;
    }

    public void setLayout(FormLayout layout) {
        this.layout = layout;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public PasswordField getPasswort() {
        return passwort;
    }

    public void setPasswort(PasswordField passwort) {
        this.passwort = passwort;
    }

    public Button getConfirmLogin() {
        return confirmLogin;
    }

    public void setConfirmLogin(Button confirmLogin) {
        this.confirmLogin = confirmLogin;
    }

    public Button getGoToReg() {
        return goToReg;
    }

    public void setGoToReg(Button goToReg) {
        this.goToReg = goToReg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, User> getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Map<Integer, User> activeUser) {
        this.activeUser = activeUser;
    }

}
