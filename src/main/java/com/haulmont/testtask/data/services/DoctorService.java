package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor ;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorService extends Service<Doctor>{
    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    private static DoctorService instance;

    private DoctorService(){}

    public static DoctorService getInstance(){
        if(instance == null){
            instance = new DoctorService();
        }
        return instance;
    }






    public  List<Doctor> getDoctors(){
        Session session = factory.openSession();
        List<Doctor> doctors = session.createQuery("from Doctor ").list();
        session.close();
        return doctors;
    }

    public  List<Doctor> getDoctorById(int id){
        Session session = factory.openSession();
        List<Doctor> doctors = session.createQuery("select d from Doctor d where d.id = :id").setParameter("id", id).list();
        session.close();
        return doctors;
    }

    public  List<Doctor> getDoctorBySpecialization(String specialization){
        Session session = factory.openSession();
        List<Doctor> doctors = (List<Doctor>) session.createQuery("select d from Doctor d where d.specialization = :specialization")
                .setParameter("specialization", specialization).list();
        session.close();
        return doctors;
    }

    public  List<Doctor> getDoctorByFullName(String firstName, String secondName, String thirdName){
        Session session = factory.openSession();
        List<Doctor> doctors = (List<Doctor>) session.createQuery("select d from Doctor d where d.firstName = :first and d.secondName = :second and d.thirdName = :third")
                .setParameter("first", firstName).setParameter("second", secondName).setParameter("third", thirdName).list();
        session.close();
        return doctors;
    }
}
