/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import static java.lang.System.exit;

/**
 *
 * @author Juri-PC
 */
public class StartView extends VerticalLayout implements View {
    
    public StartView(Navigator navigator) {
        
        Utils.CheckRootFolder();
        if (!Database.Connect()) {
            exit(-1);
        }
        
        //WrappedSession ws = vaadinRequest.getWrappedSession(false);    
        
        final TextField testField = new TextField();
        testField.setCaption("Test:");
            
        Button button = new Button("Start Test");
        button.addClickListener(e -> {
                     
            addComponent(new Label("Test output: " + Utils.GetRelativePath(testField.getValue())));
        });
        
        //setSizeFull();

        Button button1 = new Button("Go to Main View",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo("mainview");
            }
            
        });
        addComponents(testField, button, button1);
        //setComponentAlignment(button, button1, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        //Notification.show("Welcome to the Animal Farm");  
    }
}