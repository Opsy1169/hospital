package com.haulmont.testtask.controllers;

import com.haulmont.testtask.components.DoctorComponent;
import com.haulmont.testtask.components.DoctorForm;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.entities.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorController {
    private static DoctorService service = DoctorService.getInstance();


    public static void deleteDoctor(Doctor doctor){
        service.delete(doctor);
    }

    public static void updateDoctor(Doctor doctor){
        service.update(doctor);
    }

    public static List<Doctor> getAllDoctors(){
        return service.getDoctors();
    }

    public static Doctor addDoctor(Doctor doctor){
        return getDoctorById(service.add(doctor));
    }

    public static Doctor getDoctorById(int id){
        return  service.getDoctorById(id).get(0);
    }
}
