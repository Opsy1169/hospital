package com.haulmont.testtask.controllers;

import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;

import java.util.List;
import java.util.stream.Collectors;

public class PatientController {
    private  PatientService patientService = PatientService.getInstance();
    private static PatientController patientController;
    private List<Patient> patients = patientService.getPatients();

    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    private PatientController(){}

    public static PatientController getInstance(){
        if(patientController == null){
            patientController = new PatientController();
        }
        return patientController;
    }

    public  synchronized void deletePatient(Patient patient){

        patientService.delete(patient);
        patients.remove(patient);
    }

    public void updatePatient(Patient patient){
        patientService.update(patient);
        prescriptionController.updatePrescriptionsListForPatient(patient);
    }

    public  List<Patient> getAllPatients() {
        return patients;
    }

    public synchronized Patient addPatient(Patient patient){
        Patient patientAdded = getPatientByIdFromService(patientService.add(patient));
        patients.add(patientAdded);
        return patientAdded;
    }

    private Patient getPatientByIdFromService(int id){
        return (Patient) patientService.getPatientById(id).get(0);
    }

    public synchronized Patient getPatientById(int id){
        return  patients.stream().filter(patient -> patient.getId() == id).findFirst().orElse(null);
    }


}
