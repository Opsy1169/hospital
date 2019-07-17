package com.haulmont.testtask.components;

import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
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


public class DoctorForm extends Composite implements View {
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    private TextField secondName = new TextField("Second name");
    private TextField firstName = new TextField("First name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private DoctorComponent parent;

//    private PatientController patientController = PatientController.getInstance();
    private DoctorController doctorController = DoctorController.getInstance();
//    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    public DoctorForm(DoctorComponent parent) {
        this.parent = parent;
        FormLayout layout =  new FormLayout();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(secondName, firstName, patronymic, specialization, buttons);
        setUpBinder();
        initButtonListeners();
        setCompositionRoot(layout);


    }

    private void setUpBinder(){
        binder.bind(secondName, Doctor::getSecondName, Doctor::setSecondName);
        binder.bind(firstName, Doctor::getFirstName, Doctor::setFirstName);
        binder.bind(patronymic, Doctor::getThirdName, Doctor::setThirdName);
        binder.bind(specialization, Doctor::getSpecialization, Doctor::setSpecialization);

        binder.forField(secondName).withValidator(second -> ((second.length() >= 2) &&
                        (second.matches("[А-Яа-я]+") || second.matches("[a-zA-Z]+"))),
                "Second name should be longer than two symbols and contain only Russian or English letters")
                .bind(Doctor::getSecondName, Doctor::setSecondName);
        binder.forField(firstName).withValidator(first -> ((first.length() >= 2) &&
                        (first.matches("[А-Яа-я]+") || first.matches("[a-zA-Z]+"))),
                "First name should be longer than two symbols and contain only Russian or English letters")
                .bind(Doctor::getFirstName, Doctor::setFirstName);
        binder.forField(patronymic).withValidator(third -> ((third.length() >= 2) &&
                        (third.matches("[А-Яа-я]+") || third.matches("[a-zA-Z]+"))),
                "Third name should be longer than two symbols and contain only Russian or English letters")
                .bind(Doctor::getThirdName, Doctor::setThirdName);
        binder.forField(specialization).withValidator(third -> ((third.length() >= 2) &&
                        (third.matches("[А-Яа-я]+") || third.matches("[a-zA-Z]+"))),
                "Specialization should be longer than two symbols and contain only Russian or English letters")
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);


    }

    public void setDoctorToForm(Doctor doctor){
        binder.setBean(doctor);
    }

    public void unbindDoctor(){
        binder.removeBean();
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            Doctor doctor = binder.getBean();
            String message = "";
            if(doctor.getId() == 0) {
                doctorController.addDoctor(doctor);
                message = "New doctor has been added";
                parent.updateList(doctor, CrudOperations.CREATE);
            }else {
                doctorController.updateDoctor(doctor);
                message = "The doctor has been updated";
                parent.updateList(doctor, CrudOperations.UPDATE);
            }
            Notification notif = new Notification("", message);
            notif.setPosition(Position.BOTTOM_RIGHT);
            notif.show(Page.getCurrent());
            Window window = event.getButton().findAncestor(Window.class);
            unbindDoctor();
            window.close();
        });
        cancel.addClickListener(event -> {
            Window window = event.getButton().findAncestor(Window.class);
            unbindDoctor();
            window.close();
        });
    }

}