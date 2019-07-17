package com.haulmont.testtask.components;

import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.util.CrudOperations;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.vaadin.inputmask.InputMask;

public class PatientForm extends Composite implements View {

    private Binder<Patient> binder = new Binder<>(Patient.class);

    private TextField secondName = new TextField("Second name");
    private TextField firstName = new TextField("First name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber = new TextField("Phone number");
    private PatientComponent parent;

    private PatientController patientController = PatientController.getInstance();
//    private DoctorController doctorController = DoctorController.getInstance();
//    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    public PatientForm(PatientComponent parent){
        this.parent = parent;
        InputMask.addTo(phoneNumber, "+9 999 999-99-99");
        FormLayout mainContent = new FormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
        mainContent.addComponents(secondName, firstName, patronymic, phoneNumber, buttonLayout);
        initButtonListeners();
        setUpBinder();
        setCompositionRoot(mainContent);

    }

    public void setPatientToForm(Patient p){
        binder.setBean(p);
    }

    public void unbindPatient(){
        binder.removeBean();
    }

    private void setUpBinder(){
        binder.forField(secondName).withValidator(second -> ((second.length() >= 2) &&
                (second.matches("[А-Яа-я]+") || second.matches("[a-zA-Z]+"))),
                "Second name should be longer than two symbols and contain only Russian or English letters")
                .bind(Patient::getSecondName, Patient::setSecondName);
        binder.forField(firstName).withValidator(first -> ((first.length() >= 2) &&
                (first.matches("[А-Яа-я]+") || first.matches("[a-zA-Z]+"))),
                "First name should be longer than two symbols and contain only Russian or English letters")
                .bind(Patient::getFirstName, Patient::setFirstName);
        binder.forField(patronymic).withValidator(third -> ((third.length() >= 2) &&
                (third.matches("[А-Яа-я]+") || third.matches("[a-zA-Z]+"))),
                "Third name should be longer than two symbols and contain only Russian or English letters")
                .bind(Patient::getThirdName, Patient::setThirdName);
        binder.forField(phoneNumber).withValidator(phone -> (phone.length() >= 16) && (!phone.contains("_")), "Wrong phone number format" ).bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            BinderValidationStatus<Patient> status = binder.validate();
            if(status.hasErrors()) return;

            Patient patient = binder.getBean();
            boolean a = patient.getFirstName().matches("[а-яА-я]+");
            String message = "";
            if(patient.getId() == 0){
                patientController.addPatient(patient);
                message = "New patient has been added";
                parent.updateList(patient, CrudOperations.CREATE);
            }else{
                patientController.updatePatient(patient);
                message = "The patient has been updated";
                parent.updateList(patient, CrudOperations.UPDATE);
            }
            Notification notif = new Notification("", message);
            notif.setPosition(Position.BOTTOM_RIGHT);
            notif.show(Page.getCurrent());
            Window window = event.getButton().findAncestor(Window.class);
            unbindPatient();
            window.close();

        });
        cancel.addClickListener(event -> {
            Window window = event.getButton().findAncestor(Window.class);
            unbindPatient();
            window.close();

        });
    }
}
