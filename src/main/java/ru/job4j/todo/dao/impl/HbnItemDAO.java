package ru.job4j.todo.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.dao.ItemDAO;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

public class HbnItemDAO implements ItemDAO, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbnItemDAO() {
    }

    @Override
    public Item createItem(String description) {
        return getItem(this.tx(session1 -> (Integer) session1.save(new Item(description))));
    }

    @Override
    public Item getItem(int id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    @Override
    public List<Item> getAllItems() {
        return this.tx(session -> session.createQuery("from Item").list());
    }

    @Override
    public boolean updateItem(Item item) {
        return this.tx(session -> {
            int modifiedLines = session.createQuery(
                    "update Item set description = :description, done = :done where id = :id")
                    .setParameter("description", item.getDescription())
                    .setParameter("done", item.isDone())
                    .setParameter("id", item.getId())
                    .executeUpdate();
            return modifiedLines > 0;
        });
    }

    @Override
    public boolean deleteItem(int id) {
        return this.tx(session -> {
            session.delete(session.get(Item.class, id));
            return getItem(id) == null;
        });
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static HbnItemDAO getInstance() {
        return SingleHbnItemDAO.INSTANCE;
    }

    private static class SingleHbnItemDAO {
        public static final HbnItemDAO INSTANCE = new HbnItemDAO();

    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
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
