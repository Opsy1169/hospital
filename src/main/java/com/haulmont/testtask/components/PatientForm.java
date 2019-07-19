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

    private TextField secondName = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField phoneNumber = new TextField("Номер телефона");
    private PatientComponent parent;

    private PatientController patientController = PatientController.getInstance();


    private Button save = new Button("Ок");
    private Button cancel = new Button("Отменить");
    public PatientForm(PatientComponent parent){
        this.parent = parent;
        InputMask.addTo(phoneNumber, "+9 999 999-99-99");
        FormLayout mainContent = new FormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
        mainContent.addComponents(secondName, firstName, patronymic, phoneNumber, buttonLayout);
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
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
                (second.trim().matches("[А-Яа-я]+") || second.trim().matches("[a-zA-Z]+"))),
                "Фамилия должна быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Patient::getSecondName, Patient::setSecondName);
        binder.forField(firstName).withValidator(first -> ((first.length() >= 2) &&
                (first.trim().matches("[А-Яа-я]+") || first.trim().matches("[a-zA-Z]+"))),
                "Имя должно быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Patient::getFirstName, Patient::setFirstName);
        binder.forField(patronymic).withValidator(third -> ((third.length() >= 2) &&
                (third.trim().matches("[А-Яа-я]+") || third.trim().matches("[a-zA-Z]+"))),
                "Отчество должно быть не короче двух символов и состоять только из букв русского или английского алфавитов")
                .bind(Patient::getThirdName, Patient::setThirdName);
        binder.forField(phoneNumber).withValidator(phone -> (phone.length() >= 16) && (!phone.contains("_")), "Неверный формат номера" ).bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            BinderValidationStatus<Patient> status = binder.validate();
            if(status.hasErrors()){
                Notification notif = new Notification("", "Некоторые данные некорректны", Notification.Type.WARNING_MESSAGE);
                notif.setPosition(Position.BOTTOM_RIGHT);
                notif.show(Page.getCurrent());
                return;
            }

            Patient patient = binder.getBean();
            patient = trimPatientFields(patient);
            boolean a = patient.getFirstName().matches("[а-яА-я]+");
            String message = "";
            if(patient.getId() == 0){
                patientController.addPatient(patient);
                message = "Новый пациент был успешно добавлен";
                parent.updateList(patient, CrudOperations.CREATE);
            }else{
                patientController.updatePatient(patient);
                message = "Пациент был успешно обновлен";
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

    private Patient trimPatientFields(Patient patient){
        patient.setFirstName(patient.getFirstName().trim());
        patient.setSecondName(patient.getSecondName().trim());
        patient.setThirdName(patient.getThirdName().trim());
        return patient;
    }
}
