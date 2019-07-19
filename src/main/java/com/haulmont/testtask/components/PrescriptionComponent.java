package com.haulmont.testtask.components;


import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

public class PrescriptionComponent extends Composite implements View {

    private Grid<Prescription> prescriptionGrid = new Grid<>(Prescription.class);
    private Window subWindow = new Window("");
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button delete = new Button("", VaadinIcons.TRASH);
    private Button edit = new Button("", VaadinIcons.EDIT);
    private ComboBox<Patient> patientFilter = new ComboBox<>("Filter by patient");
    private ComboBox<Priority> priorityFilter = new ComboBox<>("Filter by priority");
    private TextField descriptionFilter = new TextField("Filter by description");

    private Button applyFilterButton = new Button("Apply");
//    private Button clearFilters = new Button("", VaadinIcons.CROSS_CUTLERY);
//    private VerticalLayout applyLayout = new VerticalLayout(new Label(""), applyFilterButton);
    private PatientController patientController = PatientController.getInstance();
    private DoctorController doctorController = DoctorController.getInstance();
    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    ListDataProvider<Prescription> prescriptionProvider = DataProvider.ofCollection(prescriptionController.getAllPrescriptions());
    ListDataProvider<Patient> patientProvider = DataProvider.ofCollection(patientController.getAllPatients());
    ListDataProvider<Doctor> doctorProvider = DataProvider.ofCollection(doctorController.getAllDoctors());

    PrescriptionForm prescriptionForm = new PrescriptionForm(this);

    public PrescriptionComponent(){

        prescriptionGrid.setColumnOrder("description", "patient", "doctor", "beginDate", "validityInDays", "priority");
        prescriptionGrid.removeColumn("id");

        HorizontalLayout buttonLayout = new HorizontalLayout( add, delete, edit);
        HorizontalLayout filterLayout = new HorizontalLayout(patientFilter, priorityFilter, descriptionFilter, applyFilterButton);
        filterLayout.setComponentAlignment(applyFilterButton, Alignment.BOTTOM_CENTER);
//        filterLayout.setComponentAlignment(clearFilters, Alignment.BOTTOM_CENTER);
        HorizontalLayout headerLayout = new HorizontalLayout(filterLayout, buttonLayout);
        headerLayout.setExpandRatio(filterLayout, 0.7f);
        headerLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
        headerLayout.setWidth("100%");

        patientFilter.setItems(patientController.getAllPatients());
        priorityFilter.setItems(Priority.values());


        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(prescriptionForm);
        subContent.setWidth("350px");
        subWindow.center();
        subWindow.setModal(true);


        subWindow.addCloseListener(event -> prescriptionForm.unbindPrescription());

        initButtonListeners();
        initFilterListeners();


        VerticalLayout mainContent = new VerticalLayout(headerLayout, prescriptionGrid);
//        mainContent.setComponentAlignment(headerLayout, Alignment.MIDDLE_RIGHT);
        prescriptionGrid.setDataProvider(prescriptionProvider);
        mainContent.setSizeFull();
        mainContent.setExpandRatio(prescriptionGrid, 0.8f);
        prescriptionGrid.setSizeFull();
        setCompositionRoot(mainContent);


    }
    private void filterGrid(){
        Patient patient = patientFilter.getValue();
        Priority priority = priorityFilter.getValue();
        String descriptionString = descriptionFilter.getValue();
        prescriptionProvider.setFilter(prescription -> {
            boolean patientCondition = true;
            boolean priorityCondition = true;
            boolean descriptionCondition = true;
            if(patient != null) {
                patientCondition = prescription.getPatient().equals(patient);
            }
            if(priority != null){
                priorityCondition = prescription.getPriority() == priority;
            }
            if(descriptionString != "" || descriptionString != null){
                descriptionCondition = prescription.getDescription().toLowerCase().contains(descriptionString.trim().toLowerCase());
            }
            return patientCondition && priorityCondition && descriptionCondition;
        });
    }

    private void initFilterListeners(){
        applyFilterButton.addClickListener(clickEvent -> filterGrid());
//        patientFilter.addValueChangeListener(event -> {
//            filterGrid();
//        });
//
//        priorityFilter.addValueChangeListener( event -> {
//            filterGrid();
//        });
//
//        descriptionFilter.addValueChangeListener(event -> {
//            filterGrid();
//        });
    }

    private void initButtonListeners(){
        add.addClickListener(buttonClickEvent -> {
            Prescription prescription = new Prescription();
            prescriptionForm.setPrescriptionToForm(prescription);
            subWindow.center();
            subWindow.setCaption("Add new prescription");
            this.getUI().getUI().addWindow(subWindow);
        });
        edit.addClickListener(buttonClickEvent -> {
            Prescription prescription = prescriptionGrid.asSingleSelect().getValue();
            if( prescription != null) {
                prescriptionForm.setPrescriptionToForm(prescription);
                subWindow.center();
                subWindow.setCaption("Edit prescription");
                this.getUI().getUI().addWindow(subWindow);
            }
        });

        delete.addClickListener(event -> {
            Prescription prescription = prescriptionGrid.asSingleSelect().getValue();
            String message = "";
            Notification notif = null;
            if(prescription != null){
                try {
                    prescriptionController.deletePrescription(prescription);
                    updateList(prescription, CrudOperations.DELETE);
                    message = "Prescription has been deleted";
                    notif = new Notification("", message);
                    notif.setPosition(Position.BOTTOM_RIGHT);
                    notif.show(Page.getCurrent());
                } catch (Exception e){
                    message = "Something went wrong";
                    notif = new Notification("", message, Notification.Type.WARNING_MESSAGE);
                    notif.setPosition(Position.BOTTOM_RIGHT);
                    notif.show(Page.getCurrent());
                }
            }
        });
    }

//    public void updateList(Prescription prescription, CrudOperations operation){
//        switch (operation){
//            case CREATE:
//                provider.getItems().add(prescription);
//                provider.refreshAll();
//                break;
//            case UPDATE:
////                provider.refreshItem(prescription);
//                provider.refreshAll();
//                break;
//            case DELETE:
//                provider.getItems().remove(prescription);
//                provider.refreshAll();
//                break;
//        }
//    }

    public void updateList(Object o, CrudOperations operation ){
        ListDataProvider provider = null;
        if(o instanceof Patient){
            provider = patientProvider;
        } else if(o instanceof Doctor){
            provider = doctorProvider;
        } else if(o instanceof  Prescription){
            provider = prescriptionProvider;
        } else{
            return;
        }
        switch (operation){
            case CREATE:
//                provider.getItems().add(o);
                provider.refreshAll();
                break;
            case UPDATE:
//                provider.refreshItem(prescription);
                provider.refreshAll();
                break;
            case DELETE:
//                provider.getItems().remove(o);
                provider.refreshAll();
                break;
        }
    }



}