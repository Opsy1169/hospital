package com.haulmont.testtask.controllers;

import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.util.CrudOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionController {
    private static PrescriptionService service = PrescriptionService.getInstance();
    private static List<Prescription> prescriptions = service.getPrescriptions();
    private static PrescriptionController prescriptionController;

    private PrescriptionController(){}

    public static PrescriptionController getInstance(){
        if(prescriptionController == null){
            prescriptionController = new PrescriptionController();
        }
        return prescriptionController;
    }

    public synchronized void deletePrescription(Prescription prescription){
        service.delete(prescription);
        prescriptions.remove(prescription);
    }

    public synchronized void updatePrescription(Prescription prescription){
        service.update(prescription);
    }

    public List<Prescription> getAllPrescriptions(){
        return prescriptions;
    }

    public synchronized Prescription addPrescription(Prescription prescription){
        Prescription prescriptionAdded = getPrescriptionByIdFromService(service.add(prescription));
        prescriptions.add(prescriptionAdded);
        return prescriptionAdded;
    }

    private Prescription getPrescriptionByIdFromService(int id){
        return (Prescription) service.getPrescriptionById(id).get(0);
    }

    public  Prescription getPrescriptionById(int id){
        return prescriptions.stream().filter(prescription -> prescription.getId() == id).findFirst().orElse(null);//.collect(Collectors.toList()).get(0);
    }

    public void updatePrescriptionsListForDoctor(Doctor doctor){
        for (int i = 0; i < prescriptions.size(); i++) {
            Doctor prescriptionDoctor = prescriptions.get(i).getDoctor();
            if(prescriptionDoctor.getId() == doctor.getId()){
                Prescription prescription = prescriptionController.getPrescriptionByIdFromService(prescriptions.get(i).getId());
                prescriptions.set(i, prescription);
            }
        }
    }

    public void  updatePrescriptionsListForPatient(Patient patient){
        for (int i = 0; i < prescriptions.size(); i++) {
            if(prescriptions.get(i).getPatient().getId() == patient.getId()){
                Prescription prescription = prescriptionController.getPrescriptionByIdFromService(prescriptions.get(i).getId());
                prescriptions.set(i, prescription);
            }
        }
    }

//    public static void refreshDataProvider(Object o, CrudOperations operations){}
}
