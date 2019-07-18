package com.haulmont.testtask.components;

import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Prescription;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticComponent extends HorizontalLayout {
    private VerticalLayout fullNameLayout = new VerticalLayout();
    private VerticalLayout signedByDoctor = new VerticalLayout();
    private VerticalLayout percentageOfTotal = new VerticalLayout();
    private VerticalLayout percentageOfValid = new VerticalLayout();
    private Label fullname = new Label();
    private Label signed = new Label();
    private Label ofTotal = new Label();
    private Label valid = new Label();
    private static StatisticComponent statisticComponent = null;


    public StatisticComponent(){
        addComponents(fullNameLayout, signedByDoctor, percentageOfTotal, percentageOfValid);

        fullNameLayout.addComponents(new Label("Full name"), fullname);

        signedByDoctor.addComponents(new Label("Signed by doctor"), signed);

        percentageOfTotal.addComponents(new Label("Percentage of total number of prescriptions"), ofTotal);

        percentageOfValid.addComponents(new Label("Number of valid by today"), valid);
        fullNameLayout.setSizeFull();
        signedByDoctor.setSizeFull();
        percentageOfTotal.setSizeFull();
        valid.setSizeFull();
        this.addComponentAttachListener(event -> System.out.println("component attached"));
        this.addComponentDetachListener(event -> System.out.println("component detached"));

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
