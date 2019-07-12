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


import java.sql.*;
import java.util.List;

import static com.sun.javafx.fxml.expression.Expression.add;

@PushStateNavigation
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

//
//    private Grid<Customer> grid = new Grid<>(Customer.class);
//    private TextField filterText = new TextField();
//    private CustomerForm form = new CustomerForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new PatientComponent(), "patient");
        tabSheet.addTab(new DoctorComponent(), "doctor");
        tabSheet.addTab(new PrescriptionComponent(), "prescription");
        mainLayout.addComponent(tabSheet);
        test();
//        Label title = new Label("Menu");
//        title.addStyleName(ValoTheme.MENU_TITLE);
//
//        Button view1 = new Button("View 1", e -> getNavigator().navigateTo("view1"));
//        view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
//        Button view2 = new Button("View 2", e -> getNavigator().navigateTo("view2"));
//        view2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
//
//        CssLayout menu = new CssLayout(title, view1, view2);
//        menu.addStyleName(ValoTheme.MENU_ROOT);
//        menu.setWidth(null);
//
//
//        VerticalLayout viewContainer = new VerticalLayout();
//
//        HorizontalLayout mainLayout = new HorizontalLayout(menu, viewContainer);
////        VerticalLayout mainLayout = new VerticalLayout(viewContainer);
//        mainLayout.setExpandRatio(viewContainer, 1.0f);
////        viewContainer.setHeight("100%");
//        viewContainer.setWidth("100%");
        mainLayout.setSizeFull();
        setContent(mainLayout);
//
//        Navigator navigator = new Navigator(this, viewContainer);
//        navigator.addView("", DefaultView.class);
//        navigator.addView("view1", View1.class);
//        navigator.addView("view2", View2.class);
    }


    public void test() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("asdasdas");
        doctor.setSecondName("qweqweqwe");
        DoctorService.addDoctor(doctor);
//        System.out.println(PatientService.getPatients());
//        Patient patient =  PatientService.getPatientById(2).get(0);
//        System.out.println(patient);
//        patient.setPhoneNumber(patient.getPhoneNumber() + "228");
//        PatientService.updatePatient(patient);
//        patient = PatientService.getPatientById(2).get(0);
//        System.out.println(patient);
//        System.out.println(PatientService.getPatientByFullName("IVANOV","IVAN","IVANOVICH"));
//        Prescription prescription = PrescriptionService.getPrescriptionById(0).get(0);
//        System.out.println(prescription);
//        Patient first = PatientService.getPatientById(0).get(0);
//        PrescriptionService.deletePrescription(prescription);
//        List<Prescription> prescriptionList = PrescriptionService.getPrescriptions();
//        System.out.println(prescriptionList.size());
//        PatientService.deletePatient(first);
//        prescription = PrescriptionService.getPrescriptionById(0).get(0);
//        System.out.println(prescription);
//        System.out.println(PrescriptionService.getPrescriptionById(0));

    }



    private void hibernate(){

    }


    private void printTable() {

    }

}