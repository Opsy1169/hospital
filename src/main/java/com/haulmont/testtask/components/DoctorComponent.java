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
    private Grid<Doctor> doctorGrid = new Grid<>();
    private Button add = new Button("Добавить");
    private Button delete = new Button("Изменить");
    private Button edit = new Button("Удалить");
    private Button showStatisticButton = new Button("Статистика");
    private Window subWindow = new Window("");
    private Window statisticSubWindow = new Window("Статистика");
    private StatisticComponent statisticLayout = new StatisticComponent();

    private DoctorController doctorController = DoctorController.getInstance();
    private StatisticController statisticController = StatisticController.getInstance();


    ListDataProvider<Doctor> provider = DataProvider.ofCollection(doctorController.getAllDoctors());

    DoctorForm doctorForm = new DoctorForm(this);

    public DoctorComponent(){

        doctorGrid.addColumn(Doctor::getSecondName).setCaption("Фамилия");
        doctorGrid.addColumn(Doctor::getFirstName).setCaption("Имя");
        doctorGrid.addColumn(Doctor::getThirdName).setCaption("Отчество");
        doctorGrid.addColumn(Doctor::getSpecialization).setCaption("Специализация");

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit, showStatisticButton);
        VerticalLayout mainContent = new VerticalLayout(buttonLayout, doctorGrid);

        mainContent.setHeight("100%");
        mainContent.setExpandRatio(doctorGrid, 0.8f);

        VerticalLayout subcontent = new VerticalLayout();
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        subcontent.addComponent(doctorForm);
        subWindow.setContent(subcontent);
        subWindow.setModal(true);
        subcontent.setWidth("350px");
        subWindow.setResizable(false);

        VerticalLayout statisticContent = new VerticalLayout();
        statisticContent.addComponent(statisticLayout);
        statisticSubWindow.setContent(statisticContent);
        statisticSubWindow.setResizable(false);
        statisticSubWindow.center();


        subWindow.addCloseListener(event -> {
            doctorForm.unbindDoctor();
        });

        initButtonListenters();

        doctorGrid.setSizeFull();

        doctorGrid.setDataProvider(provider);
        setCompositionRoot(mainContent);

        showStatisticButton.addClickListener(clickEvent -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor == null){
                Notification notification = new Notification("", "Пожалуйста, выберите доктора", Notification.Type.WARNING_MESSAGE);
                notification.setPosition(Position.BOTTOM_RIGHT);
                notification.show(Page.getCurrent());
                return;
            }
            statisticController.setDoctor(doctor, statisticLayout);
            if(statisticSubWindow.isAttached()) return;
            this.getUI().addWindow(statisticSubWindow);
        });



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
            subWindow.setCaption("Добавить нового доктора");
            this.getUI().addWindow(subWindow);
        });

        edit.addClickListener(clickEvent -> {
            Doctor doctor = doctorGrid.asSingleSelect().getValue();
            if(doctor != null) {
                doctorForm.setDoctorToForm(doctor);
                subWindow.center();
                subWindow.setCaption("Изменить данные о докторе");
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
                    message = "Доктор был успешно удален";
                    notification = new Notification("", message);
                    notification.setPosition(Position.BOTTOM_RIGHT);
                    notification.show(Page.getCurrent());
                } catch (Exception e){
                    message = "Доктор не может быть удален из-за рецептов, подписанных им";
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