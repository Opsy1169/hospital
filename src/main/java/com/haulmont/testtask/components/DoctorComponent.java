package com.haulmont.testtask.components;


import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.util.CrudOperations;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import javax.print.Doc;

public class DoctorComponent extends Composite implements View {
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button delete = new Button("", VaadinIcons.TRASH);
    private Button edit = new Button("", VaadinIcons.EDIT);
    private Window subWindow = new Window("addEditWindow");

    ListDataProvider<Doctor> provider = DataProvider.ofCollection(DoctorService.getDoctors());

    DoctorForm doctorForm = new DoctorForm(this);

    public DoctorComponent(){

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);
        VerticalLayout mainContent = new VerticalLayout(buttonLayout, doctorGrid);

        VerticalLayout subcontent = new VerticalLayout();
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        subcontent.addComponent(doctorForm);
        subWindow.setContent(subcontent);
        subcontent.setWidth("350px");

        subWindow.addCloseListener(event -> {
            doctorForm.unbindDoctor();
        });

        initButtonListenters();
        mainContent.setSizeFull();
        doctorGrid.setSizeFull();

        doctorGrid.setDataProvider(provider);
        setCompositionRoot(mainContent);

    }

    private void initButtonListenters(){
        add.addClickListener(clickEvent -> {
            doctorForm.setDoctorToForm(new Doctor());
            subWindow.center();
            this.getUI().addWindow(subWindow);
        });

        edit.addClickListener(clickEvent -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor != null) {
                doctorForm.setDoctorToForm(doctor);
                subWindow.center();
                this.getUI().addWindow(subWindow);
            }
        });

        delete.addClickListener(event -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor != null){
                try {
                    DoctorService.deleteDoctor(doctor);
                    updateList(doctor, CrudOperations.DELETE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateList(Doctor doctor, CrudOperations operation){
        switch (operation){
            case CREATE:
                provider.getItems().add(doctor);
                provider.refreshAll();
                break;
            case UPDATE:
                provider.refreshItem(doctor);
                break;
            case DELETE:
                provider.getItems().remove(doctor);
                provider.refreshAll();
                break;
        }
    }


}