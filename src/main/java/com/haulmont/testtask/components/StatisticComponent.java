package com.haulmont.testtask.components;

import com.haulmont.testtask.controllers.DoctorController;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Prescription;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticComponent extends Composite implements View {
    private VerticalLayout fullNameLayout = new VerticalLayout();
    private VerticalLayout signedByDoctor = new VerticalLayout();
    private VerticalLayout percentageOfTotal = new VerticalLayout();
    private VerticalLayout percentageOfValid = new VerticalLayout();
    private Label fullname = new Label();
    private Label signed = new Label();
    private Label ofTotal = new Label();
    private Label valid = new Label();
    private static StatisticComponent statisticComponent = null;
    private HorizontalLayout mainLayout = new HorizontalLayout();
//    private Grid<> doctorGrid = new Grid<>();
    private TextField doctorGrid = new TextField("asdasd");
    private DoctorController doctorController = DoctorController.getInstance();
    ListDataProvider<Doctor> provider = DataProvider.ofCollection(doctorController.getAllDoctors());


    public StatisticComponent(){
//        doctorGrid.setColumnOrder("secondName", "firstName",  "thirdName", "specialization");
//        doctorGrid.removeColumn("id");

        mainLayout.addComponents(fullNameLayout, signedByDoctor, percentageOfTotal, percentageOfValid);

        fullNameLayout.addComponents(new Label("Full name"), fullname);

        signedByDoctor.addComponents(new Label("Signed by doctor"), signed);

        percentageOfTotal.addComponents(new Label("Percentage of total"), ofTotal);

        percentageOfValid.addComponents(new Label("Number of valid by today"), valid);
        HorizontalLayout subcontent = new HorizontalLayout(fullNameLayout, signedByDoctor, percentageOfTotal, percentageOfValid);
        fullNameLayout.setWidth("20%");
        signedByDoctor.setWidth("20%");
        percentageOfValid.setWidth("20%");
        percentageOfTotal.setWidth("40%");
//        subcontent.setSpacing(false);
//        subcontent.setMargin(false);
        VerticalLayout mainContent = new VerticalLayout(subcontent);
        subcontent.setSizeFull();
        mainContent.setHeight("100%");
        mainContent.setWidth("1000px");
        setCompositionRoot(mainContent);
//        fullNameLayout.setSizeFull();
//        signedByDoctor.setSizeFull();
//        percentageOfTotal.setSizeFull();
//        valid.setSizeFull();
////        mainLayout.setSizeFull();
//        setCompositionRoot(mainLayout);

    }

//    public static StatisticComponent getInstance(){
//        if(statisticComponent == null){
//            statisticComponent = new StatisticComponent();
//        }
//        return statisticComponent;
//    }

    public void setDoctorToPanel(String fullname, int signed, int oftotal, int valid){
        this.fullname.setCaption(fullname);
        this.signed.setCaption(String.valueOf(signed));
        this.ofTotal.setCaption(String.valueOf(oftotal) + "%");
        this.valid.setCaption(String.valueOf(valid));

    }
}
