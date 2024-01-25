package com.Application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
    try {
        Book book = new Book("title", "Author", "genre 1", BigDecimal.ONE, 123);
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        if (sessionFactory != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                session.persist(book);
                session.flush();
                transaction.commit();

            }
        } else System.err.println("SessionFactory is null. Check HibernateConfig configuration.");
    }
        catch (Exception e) {
                e.printStackTrace();
            }

    }
}