package com.haulmont.testtask;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


public class DefaultView extends Composite implements View {
    public DefaultView(){

//
        TabSheet tabSheet = new TabSheet();
//        tabSheet.setSizeFull();
        tabSheet.setHeight("100%");

        VerticalLayout firstTab = new VerticalLayout();
        firstTab.setHeight("100%");
        firstTab.setSizeFull();

        TextArea area = new TextArea("zxczxc");
        area.setWidth("100%");
        Button button = new Button("asdasd");
        button.setWidth("100%");
        firstTab.addComponents(area, button);

        VerticalLayout secondTab = new VerticalLayout();
        secondTab.setHeight("100%");
        secondTab.setSizeFull();

        TextField field = new TextField("qweqwe");
        field.setWidth("100%");
        PasswordField passwordField = new PasswordField();
        passwordField.setWidth("100%");
        secondTab.addComponents(field, passwordField);

        tabSheet.addTab(firstTab, "first");
        tabSheet.addTab(secondTab, "second");


        setCompositionRoot(tabSheet);
//        TabSheet sample = new TabSheet();
//        sample.setHeight(100.0f, Unit.PERCENTAGE);
//
//        for (int i = 1; i < 8; i++) {
//            final Label label = new Label("qweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqwe");
//            label.setWidth(100.0f, Unit.PERCENTAGE);
//
//            final VerticalLayout layout = new VerticalLayout(label);
//            layout.setMargin(true);
//
//            sample.addTab(layout, "Tab " + i);
//        }
//        setCompositionRoot(sample);
    }

}
