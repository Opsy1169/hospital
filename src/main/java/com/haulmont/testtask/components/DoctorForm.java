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
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;


public class DoctorForm extends Composite implements View {
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    private TextField secondName = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField specialization = new TextField("Специализация");

    private Button save = new Button("Oк");
    private Button cancel = new Button("Отменить");

    private DoctorComponent parent;

    private DoctorController doctorController = DoctorController.getInstance();

    public DoctorForm(DoctorComponent parent) {
        this.parent = parent;
        FormLayout layout =  new FormLayout();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(secondName, firstName, patronymic, specialization, buttons);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_LEFT);
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
                        (second.trim().matches("[А-Яа-я]+") || second.trim().matches("[a-zA-Z]+"))),
                "Фамилия должна быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Doctor::getSecondName, Doctor::setSecondName);
        binder.forField(firstName).withValidator(first -> ((first.length() >= 2) &&
                        (first.trim().matches("[А-Яа-я]+") || first.trim().matches("[a-zA-Z]+"))),
                "Имя должно быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Doctor::getFirstName, Doctor::setFirstName);
        binder.forField(patronymic).withValidator(third -> ((third.length() >= 2) &&
                        (third.trim().matches("[А-Яа-я]+") || third.trim().matches("[a-zA-Z]+"))),
                "Отчество должно быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Doctor::getThirdName, Doctor::setThirdName);
        binder.forField(specialization).withValidator(third -> ((third.length() >= 2) &&
                        (third.trim().matches("[А-Яа-я]+") || third.trim().matches("[a-zA-Z]+"))),
                "Специализация должна быть не короче двух символов и состоять только из букв русского или английского алфавитов")
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
            BinderValidationStatus status = binder.validate();
            doctor = trimDoctorFields(doctor);

            if(status.hasErrors()){
                Notification notif = new Notification("", "Некоторые данные некорректны", Notification.Type.WARNING_MESSAGE);
                notif.setPosition(Position.BOTTOM_RIGHT);
                notif.show(Page.getCurrent());
                return;
            }
            String message = "";
            if(doctor.getId() == 0) {
                doctorController.addDoctor(doctor);
                message = "Новый доктор был успешно добавлен";
                parent.updateList(doctor, CrudOperations.CREATE);
            }else {
                doctorController.updateDoctor(doctor);
                message = "Доктор был успешно изменен";
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

    private Doctor trimDoctorFields(Doctor doctor){
        doctor.setFirstName(doctor.getFirstName().trim());
        doctor.setSecondName(doctor.getSecondName().trim());
        doctor.setThirdName(doctor.getThirdName().trim());
        doctor.setSpecialization(doctor.getSpecialization().trim());
        return doctor;
    }

}