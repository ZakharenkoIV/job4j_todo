package ru.job4j.todo.dao.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

public class BaseImplHbn implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SingleSessionFactory.INSTANCE.sf;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private static class SingleSessionFactory {
        private static final BaseImplHbn INSTANCE = new BaseImplHbn();
    }

    <T> T transactionWrapper(final Function<Session, T> command) {
        final Session session = BaseImplHbn.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();
        try (session) {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}