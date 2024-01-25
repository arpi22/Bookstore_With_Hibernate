package com.Application;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;



public class HibernateConfig {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    //This method initializes and manages Hibernate configuration and services.
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                registry = new StandardServiceRegistryBuilder().configure().build();

                //Build Metadata from the configuration. Metadata represents Hibernate's
                //understanding of data models, entity mappings, and database connections.
                Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

                //Create a SessionFactory using the Metadata. The SessionFactory is a heavyweight
                //object responsible for creating individual Hibernate Session instances.
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e){
                e.printStackTrace();
                if(registry != null){
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }
}
