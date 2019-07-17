package com.haulmont.testtask.components;


import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

public class PatientComponent extends Composite implements View {

    private Grid<Patient> patientGrid = new Grid<>(Patient.class);
    private Window window = new Window("");
    Button add = new Button("", VaadinIcons.PLUS);
    Button delete = new Button("", VaadinIcons.TRASH);
    Button edit = new Button("", VaadinIcons.EDIT);
    PatientForm patientForm = new PatientForm(this);

    private PatientController patientController = PatientController.getInstance();
//    private DoctorController doctorController = DoctorController.getInstance();
//    private PrescriptionController prescriptionController = PrescriptionController.getInstance();
    ListDataProvider<Patient> provider = DataProvider.ofCollection(patientController.getAllPatients());

    public PatientComponent(){

        patientGrid.setColumnOrder("secondName", "firstName",  "thirdName", "phoneNumber");
        patientGrid.removeColumn("id");

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);

        VerticalLayout mainContent = new VerticalLayout(buttonLayout, patientGrid);
        patientGrid.setSizeFull();

        mainContent.setHeight("100%");
        mainContent.setExpandRatio(patientGrid, 0.8f);

        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        VerticalLayout subContent = new VerticalLayout(patientForm);
        subContent.setWidth("350px");
        window.setContent(subContent);

        window.addCloseListener(closeEvent -> patientForm.unbindPatient());

        initButtonListeners();



        patientGrid.setDataProvider(provider);
//        patientGrid.setItems(PatientService.getPatients());
        setCompositionRoot(mainContent);

    }

    private void initButtonListeners(){
        add.addClickListener(event -> {
            patientForm.setPatientToForm(new Patient());
            window.center();
            window.setCaption("Add new patient");
            this.getUI().getUI().addWindow(window);
        });

        edit.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            if(patient != null){
                patientForm.setPatientToForm(patient);
                window.center();
                window.setCaption("Edit patient");
                this.getUI().getUI().addWindow(window);
            }
        });

        delete.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            String message = "";
            Notification notification = null;
            if(patient != null){
                try {
                    patientController.deletePatient(patient);
                    updateList(patient, CrudOperations.DELETE);
                    message = "Patient has been deleted";
                    notification = new Notification("", message);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                } catch (Exception e){
                    message = "Patient can't be deleted due to prescriptions assigned to him";
                    notification = new Notification("", message, Notification.Type.WARNING_MESSAGE);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                }
            }
        });
    }

    public void updateList(Patient patient, CrudOperations operation){
        switch (operation){
            case CREATE:
//                provider.getItems().add(patient);
                provider.refreshAll();
                break;
            case UPDATE:
//                provider.refreshItem(patient);
                provider.refreshAll();
                break;
            case DELETE:
//                provider.getItems().remove(patient);
                provider.refreshAll();
                break;
        }
    }

}
