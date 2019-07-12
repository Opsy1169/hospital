package com.haulmont.testtask.components;

import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class PatientForm extends Composite implements View {

    private Binder<Patient> binder = new Binder<>(Patient.class);

    private TextField secondName = new TextField("Second name");
    private TextField firstName = new TextField("First name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber = new TextField("Phone number");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    public PatientForm(){
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
        binder.bind(secondName, Patient::getSecondName, Patient::setSecondName);
        binder.bind(firstName, Patient::getFirstName, Patient::setFirstName);
        binder.bind(patronymic, Patient::getThirdName, Patient::setThirdName);
        binder.bind(phoneNumber, Patient::getPhoneNumber, Patient::setPhoneNumber);
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            Patient patient = binder.getBean();
            if(patient.getId() == 0){
                PatientService.addPatient(patient);
            }else{
                PatientService.updatePatient(patient);
            }
        });
        cancel.addClickListener(event -> {
            Window window = event.getButton().findAncestor(Window.class);
            window.close();
        });
    }
}
