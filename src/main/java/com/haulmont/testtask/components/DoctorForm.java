package com.haulmont.testtask.components;

import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;


public class DoctorForm extends Composite implements View {
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    private TextField secondName = new TextField("Second name");
    private TextField firstName = new TextField("First name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");


    public DoctorForm() {
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
            if(doctor.getId() == 0) {
                DoctorService.addDoctor(doctor);
            }else {
                DoctorService.updateDoctor(doctor);
            }
        });
        cancel.addClickListener(event -> {
            Window window = event.getButton().findAncestor(Window.class);
            window.close();
        });
    }

}