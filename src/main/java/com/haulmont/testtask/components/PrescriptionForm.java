package com.haulmont.testtask.components;

import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
//import org.vaadin.inputmask.InputMask;

public class PrescriptionForm extends Composite implements View {
    private Binder<Prescription> binder = new Binder<>(Prescription.class);

    private TextArea description = new TextArea("Description");
    private ComboBox<Patient> patientComboBox = new ComboBox<>("Patient");
    private ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
    private DateField dateField = new DateField("Begin date");
    private TextField validity = new TextField("Validity");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");
    private Button save = new Button("save");
    private Button cancel = new Button("cancel");
    private PrescriptionComponent parent;
    private PatientController patientController = PatientController.getInstance();
    private DoctorController doctorController = DoctorController.getInstance();
    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    public PrescriptionForm(PrescriptionComponent parent) {
        this.parent = parent;
//        InputMask mask = new InputMask("\\d{0, 3}");
//        mask.setRegexMask(true);
//        mask.extend(validity);
        FormLayout layout =  new FormLayout();
        patientComboBox.setItems(patientController.getAllPatients());
        doctorComboBox.setItems(doctorController.getAllDoctors());
        priorityComboBox.setItems(Priority.values());

        initButtonListeners();
        setUpBinder();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(description, patientComboBox, doctorComboBox, dateField, validity, priorityComboBox, buttons);
        setCompositionRoot(layout);


    }

    private void setUpBinder(){
//        binder.forField(description).withValidator(second -> ((second.length() >= 2) &&
//                        (second.matches("[А-Яа-я]+") || second.matches("[a-zA-Z]+"))),
//                "Second name should be longer than two symbols and contain only Russian or English letters")
//                .bind(Prescription::getDescription, Prescription::setDescription);
        binder.bind(description, Prescription::getDescription, Prescription::setDescription);
        binder.bind(patientComboBox, Prescription::getPatient, Prescription::setPatient);
        binder.bind(doctorComboBox, Prescription::getDoctor, Prescription::setDoctor);
        binder.bind(dateField, Prescription::getBeginDate, Prescription::setBeginDate);
        binder.bind(priorityComboBox, Prescription::getPriority, Prescription::setPriority);
        binder.forField(validity)
                .withConverter(new StringToIntegerConverter("Must be integer value"))
                .withValidator(validity -> validity.toString().length() <= 3 && validity > 0, "Prescription should be positive and is valid for no longer than 999 days")
                .bind(Prescription::getValidityInDays, Prescription::setValidityInDays);


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
                prescriptionController.addPrescription(prescription);
                message = "New prescription has been added";
                parent.updateList(prescription, CrudOperations.CREATE);
            }else{
                prescriptionController.updatePrescription(prescription);
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
