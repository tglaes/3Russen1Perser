/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1p.d.box;

/**
 *
 * @author Juri-PC
 */
import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class MainOverview extends Composite implements View {

    public MainOverview() {
        setCompositionRoot(new Label("This is Overview"));
    }
}
