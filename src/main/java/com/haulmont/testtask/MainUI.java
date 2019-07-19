package com.haulmont.testtask;

import com.haulmont.testtask.components.DoctorComponent;
import com.haulmont.testtask.components.PatientComponent;
import com.haulmont.testtask.components.PrescriptionComponent;
import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.navigator.Navigator;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.vaadin.inputmask.InputMask;
//import org.vaadin.inputmask.client.Alias;
//import org.vaadin.inputmask.client.Casing;
//import org.vaadin.inputmask.client.Definition;
//import org.vaadin.inputmask.client.PreValidator;


import java.sql.*;
import java.util.List;

import static com.sun.javafx.fxml.expression.Expression.add;

@PushStateNavigation
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private PrescriptionComponent prescriptionComponent = new PrescriptionComponent();
    private PatientComponent patientComponent = new PatientComponent();
    private DoctorComponent doctorComponent = new DoctorComponent();

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout mainLayout = new VerticalLayout();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(patientComponent, "Пациенты");
        tabSheet.addTab(doctorComponent, "Доктора");
        tabSheet.addTab(prescriptionComponent, "Рецепты");
        mainLayout.addComponent(tabSheet);

        tabSheet.addSelectedTabChangeListener(selectedTabChangeEvent -> {
            if (tabSheet.getSelectedTab() instanceof PrescriptionComponent ||  tabSheet.getSelectedTab() instanceof PatientComponent){
                doctorComponent.hideStatistic();
            }
        });



        tabSheet.setSizeFull();
        mainLayout.setSizeFull();
        setContent(mainLayout);



    }



}