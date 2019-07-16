package com.haulmont.testtask.controllers;

import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    private static PrescriptionService service = PrescriptionService.getInstance();

    public static void deletePrescription(Prescription prescription){
        service.delete(prescription);
    }

    public static void updatePrescription(Prescription prescription){
        service.update(prescription);
    }

    public static List<Prescription> getAllPrescriptions(){
        return service.getPrescriptions();
    }

    public static Prescription addPrescription(Prescription prescription){

        return getPrescriptionById(service.add(prescription));
    }

    public static Prescription getPrescriptionById(int id){
        return service.getPrescriptionById(id).get(0);
    }
}
