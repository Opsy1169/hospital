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
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.Objects;


public class PrescriptionForm extends Composite implements View {
    private Binder<Prescription> binder = new Binder<>(Prescription.class);

    private TextArea description = new TextArea("Описание");
    private ComboBox<Patient> patientComboBox = new ComboBox<>("Пациент");
    private ComboBox<Doctor> doctorComboBox = new ComboBox<>("Доктор");
    private DateField dateField = new DateField("Дата создания ");
    private TextField validity = new TextField("Срок действия");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Приоритет");
    private Button save = new Button("Oк");
    private Button cancel = new Button("Отменить");
    private PrescriptionComponent parent;
    private PatientController patientController = PatientController.getInstance();
    private DoctorController doctorController = DoctorController.getInstance();
    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    public PrescriptionForm(PrescriptionComponent parent) {
        this.parent = parent;

        dateField.setDefaultValue(LocalDate.now());
        FormLayout layout =  new FormLayout();
        patientComboBox.setItems(patientController.getAllPatients());
        doctorComboBox.setItems(doctorController.getAllDoctors());
        priorityComboBox.setItems(Priority.values());
        setUpBinder();

        initButtonListeners();

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(description, patientComboBox, doctorComboBox, dateField, validity, priorityComboBox, buttons);

        setCompositionRoot(layout);


    }

    private void setUpBinder(){

        binder.bind(dateField, Prescription::getBeginDate, Prescription::setBeginDate);
        binder.forField(description).withValidator(description -> description.length() > 0, "Поле должно быть заполнено").bind(Prescription::getDescription, Prescription::setDescription);
        binder.forField(doctorComboBox).withValidator(Objects::nonNull, "Значение должно быть выбрано").bind(Prescription::getDoctor, Prescription::setDoctor);
        binder.forField(patientComboBox).withValidator(Objects::nonNull, "Значение должно быть выбрано").bind(Prescription::getPatient, Prescription::setPatient);
        binder.forField(priorityComboBox).withValidator(Objects::nonNull, "Значение должно быть выбрано").bind(Prescription::getPriority, Prescription::setPriority);
        binder.forField(validity)
                .withConverter(new StringToIntegerConverter("Должно быть целочисленным значением"))
                .withValidator(validity -> validity.toString().length() <= 3 && validity > 0, "Срое действия должен быть положительным и длиться меньше 999 дней")
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
            BinderValidationStatus status = binder.validate();

            if(status.hasErrors()){
                Notification notif = new Notification("", "Некоторые данные некорректны", Notification.Type.WARNING_MESSAGE);
                notif.setPosition(Position.BOTTOM_RIGHT);
                notif.show(Page.getCurrent());
                return;
            }
            if(prescription.getId() == 0){
                prescriptionController.addPrescription(prescription);
                message = "Рецепт был успешно добавлен";
                parent.updateList(prescription, CrudOperations.CREATE);
            }else{
                prescriptionController.updatePrescription(prescription);
                message = "Рецепт был успешно обновлен";
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
