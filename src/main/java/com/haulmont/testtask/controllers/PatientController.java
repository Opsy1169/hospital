package com.haulmont.testtask.controllers;

import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Patient;

import java.util.List;

public class PatientController {

    public static List<Patient> getAllPatients(){
        return PatientService.getPatients();
    }

}
