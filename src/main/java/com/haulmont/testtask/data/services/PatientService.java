package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class PatientService extends Service<Patient>{

    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    private static PatientService instance;

    private PatientService(){

    }

    public static PatientService getInstance(){
        if(instance == null){
            instance = new PatientService();
        }
        return instance;
    }



    public List<Patient> getPatients(){
        Session session = factory.openSession();
        List<Patient> patients = session.createQuery("from Patient ").list();
        session.close();
        return patients;
    }

    public  List<Patient> getPatientById(int id){
        Session session = factory.openSession();
        List<Patient> patients = session.createQuery("select p from Patient p where p.id = :id").setParameter("id", id).list();
        session.close();
        return patients;
    }

    public  List<Patient> getPatientByPhone(String phone){
        Session session = factory.openSession();
        List<Patient> patients = (List<Patient>) session.createQuery("select p from Patient p where p.phoneNumber = :phone").setParameter("phone", phone).list();
        session.close();
        return patients;
    }

    public  List<Patient> getPatientByFullName(String firstName, String secondName, String thirdName){
        Session session = factory.openSession();
        List<Patient> patients = (List<Patient>) session.createQuery("select p from Patient p where  p.firstName = :first and p.secondName = :second and p.thirdName = :third")
                .setParameter("first", firstName).setParameter("second", secondName).setParameter("third", thirdName).list();
        session.close();
        return patients;
    }

}
