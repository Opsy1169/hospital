package com.haulmont.testtask;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


public class DefaultView extends Composite implements View {
    public DefaultView(){


        TabSheet tabSheet = new TabSheet();

        VerticalLayout firstTab = new VerticalLayout();

        TextArea area = new TextArea("zxczxc");
        Button button = new Button("asdasd");
        firstTab.addComponents(area, button);

        VerticalLayout secondTab = new VerticalLayout();

        TextField field = new TextField("qweqwe");
        PasswordField passwordField = new PasswordField();
        secondTab.addComponents(field, passwordField);

        tabSheet.addTab(firstTab, "first");
        tabSheet.addTab(secondTab, "second");


        setCompositionRoot(tabSheet);
    }

}
