package com.haulmont.testtask.components;

import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.util.CrudOperations;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
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
        binder.bind(secondName, Patient::getSecondName, Patient::setSecondName);
        binder.bind(firstName, Patient::getFirstName, Patient::setFirstName);
        binder.bind(patronymic, Patient::getThirdName, Patient::setThirdName);
        binder.bind(phoneNumber, Patient::getPhoneNumber, Patient::setPhoneNumber);
    }

    private void initButtonListeners(){
        save.addClickListener(event ->{
            Patient patient = binder.getBean();
            String message = "";
            if(patient.getId() == 0){
                PatientService.addPatient(patient);
                message = "New patient has been added";
                parent.updateList(patient, CrudOperations.CREATE);
            }else{
                PatientService.updatePatient(patient);
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
