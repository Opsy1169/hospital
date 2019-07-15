package com.haulmont.testtask.components;


import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
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
    private Window subWindow = new Window("addEditWindow");
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button delete = new Button("", VaadinIcons.TRASH);
    private Button edit = new Button("", VaadinIcons.EDIT);
    private ComboBox<Patient> patientFilter = new ComboBox<>("Filter by patient");
    private ComboBox<Priority> priorityFilter = new ComboBox<>("Filter by priority");
    private TextField descriptionFilter = new TextField("Filter by description");
    private TextField a = new TextField();
    ListDataProvider<Prescription> provider = DataProvider.ofCollection(PrescriptionService.getPrescriptions());

    PrescriptionForm prescriptionForm = new PrescriptionForm(this);

    public PrescriptionComponent(){

        prescriptionGrid.setColumnOrder("description", "patient", "doctor", "beginDate", "validityInDays", "priority");
        prescriptionGrid.removeColumn("id");

        HorizontalLayout buttonLayout = new HorizontalLayout( add, delete, edit);
        HorizontalLayout filterLayout = new HorizontalLayout(patientFilter, priorityFilter, descriptionFilter);
        HorizontalLayout headerLayout = new HorizontalLayout(filterLayout, buttonLayout);
        headerLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
        headerLayout.setWidth("100%");

        patientFilter.setItems(PatientService.getPatients());
        priorityFilter.setItems(Priority.values());


        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(prescriptionForm);
        subContent.setWidth("350px");
        subWindow.center();

        subWindow.addCloseListener(event -> prescriptionForm.unbindPrescription());

        initButtonListeners();
        initFilterListeners();


        VerticalLayout mainContent = new VerticalLayout(headerLayout, prescriptionGrid);
//        mainContent.setComponentAlignment(headerLayout, Alignment.MIDDLE_RIGHT);
        prescriptionGrid.setDataProvider(provider);
        mainContent.setSizeFull();
        mainContent.setExpandRatio(prescriptionGrid, 0.8f);
        prescriptionGrid.setSizeFull();
        setCompositionRoot(mainContent);


    }
    private void filterGrid(){
        Patient patient = patientFilter.getValue();
        Priority priority = priorityFilter.getValue();
        String descriptionString = descriptionFilter.getValue();
        provider.setFilter(prescription -> {
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
        patientFilter.addValueChangeListener(event -> {
            filterGrid();
        });

        priorityFilter.addValueChangeListener( event -> {
            filterGrid();
        });

        descriptionFilter.addValueChangeListener(event -> {
            filterGrid();
        });
    }

    private void initButtonListeners(){
        add.addClickListener(buttonClickEvent -> {
            Prescription prescription = new Prescription();
            prescriptionForm.setPrescriptionToForm(prescription);
            subWindow.center();
            this.getUI().getUI().addWindow(subWindow);
        });
        edit.addClickListener(buttonClickEvent -> {
            Prescription prescription = prescriptionGrid.asSingleSelect().getValue();
            if( prescription != null) {
                prescriptionForm.setPrescriptionToForm(prescription);
                subWindow.center();
                this.getUI().getUI().addWindow(subWindow);
            }
        });

        delete.addClickListener(event -> {
            Prescription prescription = prescriptionGrid.asSingleSelect().getValue();
            String message = "";
            Notification notif = null;
            if(prescription != null){
                try {
                    PrescriptionService.deletePrescription(prescription);
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

    public void updateList(Prescription prescription, CrudOperations operation){
        switch (operation){
            case CREATE:
                provider.getItems().add(prescription);
                provider.refreshAll();
                break;
            case UPDATE:
                provider.refreshItem(prescription);
                break;
            case DELETE:
                provider.getItems().remove(prescription);
                provider.refreshAll();
                break;
        }
    }

}