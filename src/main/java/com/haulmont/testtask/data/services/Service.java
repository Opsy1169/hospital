package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class Service<T> {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    public int add(T o){
        Session session = factory.openSession();
        int id = (int)session.save(o);
        session.close();
        return id;
    }

    public  void update(T o){
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(o);
        transaction.commit();
        session.close();
    }

    public  void delete(T o){
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(o);
        transaction.commit();
        session.close();
    }
}
