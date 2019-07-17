package com.haulmont.testtask.controllers;

import com.haulmont.testtask.components.StatisticComponent;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticController {

//    private PatientController patientController = PatientController.getInstance();
//    private DoctorController doctorController = DoctorController.getInstance();
    private static StatisticController controller;
    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    private StatisticController(){}

    public static StatisticController getInstance(){
        if(controller == null){
            controller = new StatisticController();
        }
        return controller;
    }


    public synchronized void setDoctor(Doctor doctor, StatisticComponent component){
        List<Prescription> prescriptions = prescriptionController.getAllPrescriptions();
        Predicate<Prescription> doctorPredicate = prescription -> prescription.getDoctor().equals(doctor);
        Predicate<Prescription> validPredicate = prescription -> prescription.getBeginDate().
                plusDays(prescription.getValidityInDays()).isAfter(LocalDate.now());
        List<Predicate<Prescription>> predicateList = new ArrayList<Predicate<Prescription>>(Arrays.asList(doctorPredicate, validPredicate));
        Predicate<Prescription> combinedPredicate = predicateList.stream().reduce(Predicate::and).get();
        Stream<Prescription> signedByDoctor = prescriptions.stream().filter(doctorPredicate);

        int byDoctor = (int) signedByDoctor.count();
        double quotient = (prescriptions.size()) != 0 ? (double)byDoctor/prescriptions.size() : 0;
        int  valid = (int) prescriptions.stream().filter(combinedPredicate).count();
        int total = (int) Math.ceil(quotient*100);
        component.setDoctorToPanel(doctor.toString(), byDoctor, total, valid);
    }


}
