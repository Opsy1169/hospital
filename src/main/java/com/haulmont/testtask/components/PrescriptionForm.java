package com.haulmont.testtask.components;

import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.vaadin.inputmask.InputMask;

public class PrescriptionForm extends Composite implements View {
    private Binder<Prescription> binder = new Binder<>(Prescription.class);

    private TextField description = new TextField("Description");
    private ComboBox<Patient> patientComboBox = new ComboBox<>("Patient");
    private ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
    private DateField dateField = new DateField("Begin date");
    private TextField validity = new TextField("Validity");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");
    private Button save = new Button("save");
    private Button cancel = new Button("cancel");
    PrescriptionComponent parent;

    public PrescriptionForm(PrescriptionComponent parent) {
        this.parent = parent;

        InputMask.addTo(validity, "999");
        FormLayout layout =  new FormLayout();
        patientComboBox.setItems(PatientService.getPatients());
        doctorComboBox.setItems(DoctorService.getDoctors());
        priorityComboBox.setItems(Priority.values());

        initButtonListeners();
        setUpBinder();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(description, patientComboBox, doctorComboBox, dateField, validity, priorityComboBox, buttons);
        setCompositionRoot(layout);


    }

    private void setUpBinder(){
        binder.bind(description, Prescription::getDescription, Prescription::setDescription);
        binder.bind(patientComboBox, Prescription::getPatient, Prescription::setPatient);
        binder.bind(doctorComboBox, Prescription::getDoctor, Prescription::setDoctor);
        binder.bind(dateField, Prescription::getBeginDate, Prescription::setBeginDate);
        binder.bind(priorityComboBox, Prescription::getPriority, Prescription::setPriority);
    }

    public void setPrescriptionToForm(Prescription prescription){
        binder.setBean(prescription);
    }
    public void unbindPrescription(){
        binder.removeBean();
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            Prescription prescription = binder.getBean();
            String message = "";
            if(prescription.getId() == 0){
                PrescriptionService.addPrescription(prescription);
                message = "New prescription has been added";
                parent.updateList(prescription, CrudOperations.CREATE);
            }else{
                PrescriptionService.updatePrescription(prescription);
                message = "The prescription has been updated";
                parent.updateList(prescription, CrudOperations.UPDATE);
            }
            Notification notif = new Notification("", message);
            notif.setPosition(Position.BOTTOM_RIGHT);
            notif.show(Page.getCurrent());
            Window window = event.getButton().findAncestor(Window.class);
            unbindPrescription();
            window.close();
        });
        cancel.addClickListener(event -> {
            Window window = event.getButton().findAncestor(Window.class);
            unbindPrescription();
            window.close();
        });
    }

}
