package com.haulmont.testtask.data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

//    public static SessionFactory getSessionFactory()
//    {
//        try
//        {
//            if (sessionFactory == null)
//            {
//                Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
//                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
//                serviceRegistryBuilder.applySettings(configuration.getProperties());
//                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
//                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            }
//            return sessionFactory;
//        } catch (Throwable ex)
//        {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }


    public static void shutdown()
    {
        getSessionFactory().close();
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure().build();
                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();
                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }

        return sessionFactory;

    }


}