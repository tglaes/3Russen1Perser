package r1p.d.box;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import static java.lang.System.exit;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        Utils.CheckRootFolder();
        if (!Database.Connect()) {
            exit(-1);
        }
        
        //WrappedSession ws = vaadinRequest.getWrappedSession(false);    
        
        final TextField testField = new TextField();
        testField.setCaption("Test:");
            
        Button button = new Button("Start Test");
        button.addClickListener(e -> {
                    
            
            layout.addComponent(new Label(Database.CheckLogin("tristan.glaes@gmx.de", "xxx").getFullName()));
        });
        
        layout.addComponents(testField, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
