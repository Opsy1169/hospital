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
    private Label ofValid = new Label();


    public StatisticComponent(){
        addComponents(fullNameLayout, signedByDoctor, percentageOfTotal, percentageOfValid);

        fullNameLayout.addComponents(new Label("Full name"), fullname);

        signedByDoctor.addComponents(new Label("Signed by doctor"), signed);

        percentageOfTotal.addComponents(new Label("Percentage of total number of prescriptions"), ofTotal);

        percentageOfValid.addComponents(new Label("Number of valid by today"), ofValid);
        fullNameLayout.setSizeFull();
        signedByDoctor.setSizeFull();
        percentageOfTotal.setSizeFull();
        percentageOfValid.setSizeFull();

    }

    public void setDoctor(Doctor doctor){
        List<Prescription> prescriptions = PrescriptionService.getPrescriptions();
        Predicate<Prescription> prescriptionPredicate = prescription -> prescription.getDoctor().equals(doctor);
        Predicate<Prescription> validPredicate = prescription -> prescription.getBeginDate().
                plusDays(prescription.getValidityInDays()).isAfter(LocalDate.now());

        List<Predicate<Prescription>> predicateList = new ArrayList<Predicate<Prescription>>(Arrays.asList(prescriptionPredicate, validPredicate));
        Predicate<Prescription> combinedPredicate = predicateList.stream().reduce(Predicate::and).get();

        Stream<Prescription> stream = prescriptions.stream().filter(prescriptionPredicate);

        List<Prescription> a = prescriptions.stream().filter(prescriptionPredicate).collect(Collectors.toList());
        double byDoctor = stream.count();
        long  valid = prescriptions.stream().filter(combinedPredicate).count();
        fullname.setCaption(doctor.toString());
        signed.setCaption(String.valueOf(byDoctor));
        int total = (int) Math.ceil((byDoctor/prescriptions.size())*100);
        ofTotal.setCaption(String.valueOf(total));
        ofValid.setCaption(String.valueOf(valid));
    }
}
