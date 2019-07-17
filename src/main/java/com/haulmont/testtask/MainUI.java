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

//
//    private Grid<Customer> grid = new Grid<>(Customer.class);
//    private TextField filterText = new TextField();
//    private CustomerForm form = new CustomerForm(this);
    private PrescriptionComponent prescriptionComponent = new PrescriptionComponent();
    private PatientComponent patientComponent = new PatientComponent();
    private DoctorComponent doctorComponent = new DoctorComponent();

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout mainLayout = new VerticalLayout();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(patientComponent, "Patients");
        tabSheet.addTab(doctorComponent, "Doctors");
        tabSheet.addTab(prescriptionComponent, "Prescriptions");
        mainLayout.addComponent(tabSheet);
//        tabSheet.addComponentDetachListener(componentDetachEvent -> System.out.println("detach"));
//        tabSheet.addFocusListener(focusEvent -> System.out.println("focused"));
//        tabSheet.addBlurListener(blurEvent -> System.out.println("blur"));
        tabSheet.addSelectedTabChangeListener(selectedTabChangeEvent -> {
            if (tabSheet.getSelectedTab() instanceof DoctorComponent){
                doctorComponent.hideStatistic();
            }
        });
//        component.addContextClickListener(event -> System.out.println("clicked"));


        tabSheet.setSizeFull();
        mainLayout.setSizeFull();
        setContent(mainLayout);



    }


    public void test() {
//        Doctor doctor = new Doctor();
//        doctor.setFirstName("asdasdas");
//        doctor.setSecondName("qweqweqwe");
//        DoctorService.addDoctor(doctor);
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