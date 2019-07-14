package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor ;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorService {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    private static Session session = factory.openSession();

    public static Doctor addDoctor (Doctor doctor){
//        Session session = factory.getCurrentSession();
        int id = (int)session.save(doctor);
        return getDoctorById(id).get(0);

    }
    public static void updateDoctor (Doctor Doctor ){

        Transaction transaction = session.beginTransaction();
        session.update(Doctor );
        transaction.commit();
    }

    public static void deleteDoctor (Doctor Doctor ){
        Transaction transaction = session.beginTransaction();
        session.delete(Doctor );
        transaction.commit();
    }

    public static List<Doctor> getDoctors(){
        return session.createQuery("from Doctor ").list();
    }

    public static List<Doctor> getDoctorById(int id){
        return session.createQuery("select d from Doctor d where d.id = :id").setParameter("id", id).list();
    }

    public static List<Doctor> getDoctorBySpecialization(String specialization){
        return (List<Doctor>) session.createQuery("select d from Doctor d where d.specialization = :specialization")
                .setParameter("specialization", specialization).list();
    }

    public static List<Doctor> getDoctorByFullName(String firstName, String secondName, String thirdName){
        return (List<Doctor>) session.createQuery("select d from Doctor d where d.firstName = :first and d.secondName = :second and d.thirdName = :third")
                .setParameter("first", firstName).setParameter("second", secondName).setParameter("third", thirdName).list();
    }
}
