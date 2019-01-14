/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A Class that represtent the registration
 *
 * @author Mahan
 */
public class RegistrationFenster extends VerticalLayout implements View {

    //***************************Properties**********************************
    private FormLayout layout = new FormLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Label label;
    private TextField email;
    private PasswordField passwort;
    private TextField firstname;
    private TextField lastname;
    private Button confirmReg;
    private Button cancelReg;
    private User user;

    /**
     * Constructor of the class RegistrationFenster
     */
    public RegistrationFenster() {
        //Start database Connection 
        Database.Connect();
        //get the Current Navigator
        Navigator navigator = UI.getCurrent().getNavigator();
        // instanz the properties  
        this.label = new Label("Welcome to Registration Window");
        this.email = new TextField("E-Mail");
        this.passwort = new PasswordField("Passwort");
        this.firstname = new TextField("Firstname");
        this.lastname = new TextField("Lastname");
        this.confirmReg = new Button("Confirm");
        //instaz the property and define the navigation for the button reg
        this.cancelReg = new Button("Cancel", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(MyUI.LOGIN_VIEW);
            }

        });
        //create a new user to database and check that the values are valid 
        confirmReg.addClickListener((event) -> {
            if (email.getValue() == null || passwort.getValue() == null || firstname.getValue() == null || lastname.getValue() == null) {
                Notification.show("Check that you fills the fields correctly");
            }
            if (email.getValue().trim() == "" || passwort.getValue().trim() == "" || firstname.getValue().trim() == "" || lastname.getValue().trim() == "") {
                Notification.show("Check that you fills the fields correctly");
            }

            
            if (Database.GetUserByEmail(email.getValue()) != -1 ) {
                Notification.show("Email already exists");
            }
            
            user = Database.CreateNewUser(email.getValue(), passwort.getValue(), firstname.getValue(), lastname.getValue());

        });

        //add some ccs styles   
        layout.setCaption("Registration Form");
        email.setDescription("Please enter your Email");
        email.setIcon(FontAwesome.USER);
        email.setPlaceholder("MaxMusterMann@gmail.com");
        passwort.setDescription("Please enter a Passwort for your Account");
        passwort.setIcon(FontAwesome.KEY);
        passwort.setPlaceholder("Password");
        firstname.setDescription("Please give your firstname ");
        firstname.setIcon(FontAwesome.MAIL_FORWARD);
        firstname.setPlaceholder("Max");
        lastname.setDescription("Please give your lastname");
        lastname.setIcon(FontAwesome.MAIL_FORWARD);
        lastname.setPlaceholder("Mustermann");
        label.addStyleName(ValoTheme.LABEL_H2);
        confirmReg.addStyleName(ValoTheme.BUTTON_PRIMARY);
        cancelReg.addStyleName(ValoTheme.BUTTON_DANGER);
        addStyleName(ValoTheme.MENU_ROOT);
        label.addStyleNames(ValoTheme.LABEL_H2, ValoTheme.LABEL_COLORED);
        addStyleName(ValoTheme.MENU_ROOT);

        // add controls to the view 
        addComponent(label);
        layout.addComponent(email);
        layout.addComponent(passwort);
        layout.addComponent(firstname);
        layout.addComponent(lastname);
        buttonLayout.addComponent(confirmReg);
        buttonLayout.addComponent(cancelReg);
        layout.addComponent(buttonLayout);
        addComponent(layout);
    }

    /**
     * A function that displays the text "Registation" if we wont to register us
     *
     * @param event
     */
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Registation");
    }
//************************************GETTER AND SETTER *********************************

    public FormLayout getLayout() {
        return layout;
    }

    public void setLayout(FormLayout layout) {
        this.layout = layout;
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
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

    public TextField getFirstname() {
        return firstname;
    }

    public void setFirstname(TextField firstname) {
        this.firstname = firstname;
    }

    public TextField getLastname() {
        return lastname;
    }

    public void setLastname(TextField lastname) {
        this.lastname = lastname;
    }

    public Button getConfirmReg() {
        return confirmReg;
    }

    public void setConfirmReg(Button confirmReg) {
        this.confirmReg = confirmReg;
    }

    public Button getCancelReg() {
        return cancelReg;
    }

    public void setCancelReg(Button cancelReg) {
        this.cancelReg = cancelReg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
