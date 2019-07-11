package com.haulmont.testtask;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class View2 extends Composite implements View {
    public View2(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Label label  = new Label("view2");
        verticalLayout.addComponent(label);
        verticalLayout.setSizeFull();
        setCompositionRoot(verticalLayout);
    }
}
