package com.haulmont.testtask.components;


import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.controllers.PatientController;
import com.haulmont.testtask.controllers.PrescriptionController;
import com.haulmont.testtask.controllers.StatisticController;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorComponent extends Composite implements View {
    private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button delete = new Button("", VaadinIcons.TRASH);
    private Button edit = new Button("", VaadinIcons.EDIT);
    private Button showStatisticButton = new Button("Show statistic");
    private Window subWindow = new Window("");
    private Window statisticSubWindow = new Window("Statistic");
//    private VerticalLayout statisticLayout = new VerticalLayout(new ComboBox<Doctor>(), new Label("aasdasd"), new Label("qweqwe"));
    private StatisticComponent statisticLayout = new StatisticComponent();

//    private PatientController patientController = PatientController.getInstance();
    private DoctorController doctorController = DoctorController.getInstance();
    private StatisticController statisticController = StatisticController.getInstance();
//    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    ListDataProvider<Doctor> provider = DataProvider.ofCollection(doctorController.getAllDoctors());

    DoctorForm doctorForm = new DoctorForm(this);

    public DoctorComponent(){

        doctorGrid.setColumnOrder("secondName", "firstName",  "thirdName", "specialization");
        doctorGrid.removeColumn("id");

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit, showStatisticButton);
        VerticalLayout mainContent = new VerticalLayout(buttonLayout, doctorGrid);
//        doctorGrid.setHeight("1200px");
//        doctorGrid.setSizeFull();
        mainContent.setHeight("100%");
        mainContent.setExpandRatio(doctorGrid, 0.8f);

        VerticalLayout subcontent = new VerticalLayout();
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        subcontent.addComponent(doctorForm);
        subWindow.setContent(subcontent);
        subWindow.setModal(true);
        subcontent.setWidth("350px");

        VerticalLayout statisticContent = new VerticalLayout();
        statisticContent.addComponent(statisticLayout);
        statisticSubWindow.setContent(statisticContent);
        statisticSubWindow.setResizable(false);
        statisticSubWindow.center();
//        statisticContent.setWidth("500px");
//        statisticContent.setSizeFull();

        subWindow.addCloseListener(event -> {
            doctorForm.unbindDoctor();
        });

        initButtonListenters();
//        mainContent.setSizeFull();
        doctorGrid.setSizeFull();


        doctorGrid.setDataProvider(provider);
        setCompositionRoot(mainContent);
//        mainContent.addComponent(statisticLayout);
//        statisticLayout.setVisible(false);
        showStatisticButton.addClickListener(clickEvent -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor == null){
                Notification notification = new Notification("", "Please, select a doctor first", Notification.Type.WARNING_MESSAGE);
                notification.setPosition(Position.BOTTOM_RIGHT);
                notification.show(Page.getCurrent());
                return;
            }
            statisticController.setDoctor(doctor, statisticLayout);
            if(statisticSubWindow.isAttached()) return;
            this.getUI().addWindow(statisticSubWindow);
        });
//        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
//            Doctor doctor = doctorGrid.asSingleSelect().getValue();
//            if(doctor != null) {
//                statisticController.setDoctor(doctor, statisticLayout);
////                statisticLayout.setDoctor(doctor);
//                this.getUI().addWindow(statisticSubWindow);
////                statisticLayout.setVisible(true);
//
//            }else{
////                statisticLayout.setVisible(false);
//            }
//
//        });


    }

    public void hideStatistic(){
        if(statisticSubWindow.isAttached()) {
            statisticSubWindow.close();
        }
    }

    private void initButtonListenters(){
        add.addClickListener(clickEvent -> {
            doctorForm.setDoctorToForm(new Doctor());
            subWindow.center();
            subWindow.setCaption("Add new doctor");
            this.getUI().addWindow(subWindow);
        });

        edit.addClickListener(clickEvent -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor != null) {
                doctorForm.setDoctorToForm(doctor);
                subWindow.center();
                subWindow.setCaption("Edit doctor");
                this.getUI().addWindow(subWindow);
            }
        });

        delete.addClickListener(event -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            String message = "";
            Notification notification = null;
            if(doctor != null){
                try {
                    doctorController.deleteDoctor(doctor);
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
//                provider.getItems().add(doctor);
                provider.refreshAll();
                break;
            case UPDATE:
                provider.refreshAll();
                break;
            case DELETE:
//                provider.getItems().remove(doctor);
                provider.refreshAll();
                break;
        }
    }


}