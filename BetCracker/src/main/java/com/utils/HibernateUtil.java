package com.utils;

import com.utils.exceptions.IncorectInputDataException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Locale;

/**
 * Created by Катя on 28.01.2015.
 */
public class HibernateUtil {
    private static final SessionFactory factory;
    private Session session;
    private Transaction transaction;

    static {
        Locale.setDefault(Locale.ENGLISH);
        Configuration configuration = new Configuration();
        configuration.configure();
        factory = configuration.buildSessionFactory(new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry());
    }

    public void startSession() {
        session = getFactory().openSession();
    }

    public Session getSession() {
        return session;
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public void startTransaction() {
        if(transaction != null) {
            if (!transaction.isActive()) {
                transaction = session.beginTransaction();
            }
        } else {
            transaction = session.beginTransaction();
        }
    }

    public void commitTransaction() throws IncorectInputDataException {
        if(transaction != null) {
            if (transaction.isActive()) {
                transaction.commit();
                session=null;
                transaction=null;
            } else  {
                throw new IncorectInputDataException("can't  commit transaction");
            }
        } else {
        }
    }
    public void rollbackTransaction() {
        transaction.rollback();
    }

}
