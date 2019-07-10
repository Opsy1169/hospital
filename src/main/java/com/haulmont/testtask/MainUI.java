package com.haulmont.testtask;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.sql.*;
import java.util.List;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(new Label("Main UI"));

        setContent(layout);
        create();
    }


    Connection connection = null;

    public void create() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        hibernate();

    }

    private boolean loadDriver() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    private void hibernate(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Doctor> rows = (List<Doctor>)session.createQuery("from Doctor ").list();
//        Patient patient = new Patient();
//        patient.setFirstName("aaa");
//        patient.setSecondName("aaa");
//        session.save(patient);

        List<Patient> patients = (List<Patient>)session.createQuery("from Patient where id = 9 ").list();
        List<Prescription> prescr = (List<Prescription>)session.createQuery("from Prescription").list();
//        Patient patient = patients.get(patients.size()-1);
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
//        session.delete(patients.get(0));
//        transaction.commit();
        System.out.println(prescr);
        int a = 2;

    }


    private void printTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT id, FIRSTNAME, secondname from public.patient";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " "
                        + resultSet.getString(2)+ " " + resultSet.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}