package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class PatientService {

    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    private static Session session = factory.openSession();

    public static Patient addPatient(Patient patient){

        int id = (int) session.save(patient);
        return getPatientById(id).get(0);
    }
    public static void updatePatient(Patient patient){

        Transaction transaction = session.beginTransaction();
        session.update(patient);
        transaction.commit();
    }

    public static void deletePatient(Patient patient){
        Transaction transaction = session.beginTransaction();
        session.delete(patient);
        transaction.commit();
    }

    public static List<Patient> getPatients(){
        return session.createQuery("from Patient ").list();
    }

    public static List<Patient> getPatientById(int id){
        return session.createQuery("select p from Patient p where p.id = :id").setParameter("id", id).list();
    }

    public static List<Patient> getPatientByPhone(String phone){
        return (List<Patient>) session.createQuery("select p from Patient p where p.phoneNumber = :phone").setParameter("phone", phone).list();
    }

    public static List<Patient> getPatientByFullName(String firstName, String secondName, String thirdName){
        return (List<Patient>) session.createQuery("select p from Patient p where  p.firstName = :first and p.secondName = :second and p.thirdName = :third")
                .setParameter("first", firstName).setParameter("second", secondName).setParameter("third", thirdName).list();
    }

}