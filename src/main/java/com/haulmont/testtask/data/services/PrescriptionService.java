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

public class PrescriptionService extends Service<Prescription>{
    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    private static Session session = factory.openSession();
    private static PrescriptionService instance;

    private PrescriptionService(){}

    public static PrescriptionService getInstance(){
        if(instance == null){
            instance = new PrescriptionService();
        }
        return instance;
    }



    public  List<Prescription> getPrescriptions(){
        Session session = factory.openSession();
        List<Prescription> prescriptions = session.createQuery("from Prescription ").list();
        session.close();
        return prescriptions;
    }

    public  List<Prescription> getPrescriptionById(int id){
        Session session = factory.openSession();
        List<Prescription> prescriptions = session.createQuery("select p from Prescription p where p.id = :id").setParameter("id", id).list();
        session.close();
        return prescriptions;
    }

    public  List getPrescriptionByDoctor(Doctor doctor){
        Session session = factory.openSession();
        List<Prescription> prescriptions = session.createQuery("select p from Prescription p where p.doctor = :doctor").setParameter("doctor", doctor).list();
        session.close();
        return prescriptions;
    }

    public  List getPrescriptionByPatient(Patient patient){
        Session session = factory.openSession();
        List<Prescription> prescriptions = session.createQuery("select p from Prescription p where p.patient = :patient").setParameter("patient", patient).list();
        session.close();
        return prescriptions;
    }

    public  List getPrescriptionByPriority(Priority priority){
        Session session = factory.openSession();
        List<Prescription> prescriptions = session.createQuery("select p from Prescription p where p.priority = :priority").setParameter("priority", priority).list();
        session.close();
        return prescriptions;
    }


}
