package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class PrescriptionService {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    private static Session session = factory.openSession();

    public static void addPrescription (Prescription prescription){
        Session session = factory.getCurrentSession();
        session.save(prescription);
    }
    public static void updatePrescription (Prescription prescription ){
        session.update(prescription );
    }

    public static void deletePrescription (Prescription prescription ){
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(prescription);
            transaction.commit();
        }catch (ConstraintViolationException e){
            e.printStackTrace();
        }
    }

    public static List<Prescription> getPrescriptions(){
        return session.createQuery("from Prescription ").list();
    }

    public static List<Prescription> getPrescriptionById(int id){
        return session.createQuery("select p from Prescription p where p.id = :id").setParameter("id", id).list();
    }

    public static List getPrescriptionByDoctor(Doctor doctor){
        return session.createQuery("select p from Prescription p where p.doctor = :doctor").setParameter("doctor", doctor).list();
    }

    public static List getPrescriptionByPatient(Patient patient){
        return session.createQuery("select p from Prescription p where p.patient = :patient").setParameter("patient", patient).list();
    }

    public static List getPrescriptionByPriority(Priority priority){
        return session.createQuery("select p from Prescription p where p.priority = :priority").setParameter("priority", priority).list();
    }

    public static void getPrescriptionByDate(){}

    public static void getPrescriptionGivenAfterDate(){}

    public static void getPrescriptionGivenBeforeDate(){}

    public static void getPrescriptionByDescription(){}

    public static void getExpiredPrescription(){ }

    public static void getValidPrescription(){}
}
