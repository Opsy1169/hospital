package com.haulmont.testtask.controllers;

import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;

import java.util.List;

public class PatientController {
    private static PatientService patientService = PatientService.getInstance();

    public static void deletePatient(Patient patient){
        patientService.delete(patient);
    }

    public static void updatePatient(Patient patient){
        patientService.update(patient);
    }

    public static List<Patient> getAllPatients() {
        return patientService.getPatients();
    }

    public static Patient addPatient(Patient patient){
        return getPatientById(patientService.add(patient));
    }

    public static Patient getPatientById(int id){
        return  patientService.getPatientById(id).get(0);
    }


}
