package com.haulmont.testtask.components;


import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

public class PatientComponent extends Composite implements View {

    private Grid<Patient> patientGrid = new Grid<>();
    private Window window = new Window("");
    Button add = new Button("Добавить");
    Button delete = new Button("Изменить");
    Button edit = new Button("Удалить");
    PatientForm patientForm = new PatientForm(this);

    private PatientController patientController = PatientController.getInstance();
    ListDataProvider<Patient> provider = DataProvider.ofCollection(patientController.getAllPatients());

    public PatientComponent(){

        patientGrid.addColumn(Patient::getSecondName).setCaption("Фамилия");
        patientGrid.addColumn(Patient::getFirstName).setCaption("Имя");
        patientGrid.addColumn(Patient::getThirdName).setCaption("Отчество");
        patientGrid.addColumn(Patient::getPhoneNumber).setCaption("Номер телефона");

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);

        VerticalLayout mainContent = new VerticalLayout(buttonLayout, patientGrid);
        patientGrid.setSizeFull();

        mainContent.setHeight("100%");
        mainContent.setExpandRatio(patientGrid, 0.8f);

        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        VerticalLayout subContent = new VerticalLayout(patientForm);
        subContent.setWidth("350px");
        window.setContent(subContent);
        window.setModal(true);
        window.setResizable(false);

        window.addCloseListener(closeEvent -> patientForm.unbindPatient());

        initButtonListeners();



        patientGrid.setDataProvider(provider);
        setCompositionRoot(mainContent);

    }

    private void initButtonListeners(){
        add.addClickListener(event -> {
            patientForm.setPatientToForm(new Patient());
            window.center();
            window.setCaption("Добавить нового пациент");
            this.getUI().getUI().addWindow(window);
        });

        edit.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            if(patient != null){
                patientForm.setPatientToForm(patient);
                window.center();
                window.setCaption("Изменить данные о пациенте");
                this.getUI().getUI().addWindow(window);
            }
        });

        delete.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            String message = "";
            Notification notification = null;
            if(patient != null){
                try {
                    patientController.deletePatient(patient);
                    updateList(patient, CrudOperations.DELETE);
                    message = "Пациент был успешно удален";
                    notification = new Notification("", message);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                } catch (Exception e){
                    message = "Пациент не может быть удален из-за рецептов, выписанных для него";
                    notification = new Notification("", message, Notification.Type.WARNING_MESSAGE);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                }
            }
        });
    }

    public void updateList(Patient patient, CrudOperations operation){
        switch (operation){
            case CREATE:
                provider.refreshAll();
                break;
            case UPDATE:
                provider.refreshAll();
                break;
            case DELETE:
                provider.refreshAll();
                break;
        }
    }

}
