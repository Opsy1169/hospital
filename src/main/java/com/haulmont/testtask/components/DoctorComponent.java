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
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
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

        doctorGrid.setColumnOrder("firstName", "secondName", "thirdName", "specialization");
        doctorGrid.removeColumn("id");

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);
        VerticalLayout mainContent = new VerticalLayout(buttonLayout, doctorGrid);
//        doctorGrid.setHeight("1200px");
//        doctorGrid.setSizeFull();
        mainContent.setHeight("1000px");
        mainContent.setExpandRatio(doctorGrid, 0.9f);

        VerticalLayout subcontent = new VerticalLayout();
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        subcontent.addComponent(doctorForm);
        subWindow.setContent(subcontent);
        subcontent.setWidth("350px");

        subWindow.addCloseListener(event -> {
            doctorForm.unbindDoctor();
        });

        initButtonListenters();
//        mainContent.setSizeFull();
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
            String message = "";
            Notification notification = null;
            if(doctor != null){
                try {
                    DoctorService.deleteDoctor(doctor);
                    updateList(doctor, CrudOperations.DELETE);
                    message = "Doctor has been deleted";
                    notification = new Notification("", message);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                } catch (Exception e){
                    message = "Doctor can't be deleted due to prescriptions assigned by him";
                    notification = new Notification("", message, Notification.Type.WARNING_MESSAGE);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
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