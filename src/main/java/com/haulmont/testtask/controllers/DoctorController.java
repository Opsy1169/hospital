package com.haulmont.testtask.controllers;

import com.haulmont.testtask.components.DoctorComponent;
import com.haulmont.testtask.components.DoctorForm;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.entities.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorController {
    private static DoctorService service = DoctorService.getInstance();
    private static DoctorController doctorController;
    private List<Doctor> doctors = service.getDoctors();

    private PrescriptionController prescriptionController = PrescriptionController.getInstance();

    private DoctorController(){}

    public static DoctorController getInstance(){
        if(doctorController == null){
            doctorController = new DoctorController();
        }
        return doctorController;
    }


    public synchronized void deleteDoctor(Doctor doctor){
        service.delete(doctor);
        doctors.remove(doctor);
    }

    public void updateDoctor(Doctor doctor){
        service.update(doctor);
        prescriptionController.updatePrescriptionsListForDoctor(doctor);

    }

    public synchronized List<Doctor> getAllDoctors(){
        return doctors;
    }

    public synchronized Doctor addDoctor(Doctor doctor){
        Doctor doctorAdded = getDoctotByIdFromService(service.add(doctor));
        doctors.add(doctorAdded);
        return doctorAdded;
    }

    private Doctor getDoctotByIdFromService(int id){
        return (Doctor) service.getDoctorById(id).get(0);
    }

    public  Doctor getDoctorById(int id){
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst().orElse(null);//.collect(Collectors.toList()).get(0);
    }

}
