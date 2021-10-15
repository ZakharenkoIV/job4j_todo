package ru.job4j.todo.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.dao.ItemDAO;
import ru.job4j.todo.model.Item;

import java.util.List;

public class HbnItemDAO implements ItemDAO, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbnItemDAO() {
    }

    @Override
    public Item createItem(String description) {
        Session session = sf.openSession();
        session.beginTransaction();
        int id = (Integer) session.save(new Item(description));
        session.getTransaction().commit();
        session.close();
        return getItem(id);
    }

    @Override
    public Item getItem(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public boolean updateItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        int modifiedLines = session.createQuery(
                "update Item set description = :description, done = :done where id = :id")
                .setParameter("description", item.getDescription())
                .setParameter("done", item.isDone())
                .setParameter("id", item.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return modifiedLines > 0;
    }

    @Override
    public boolean deleteItem(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(session.get(Item.class, id));
        session.getTransaction().commit();
        session.close();
        return getItem(id) == null;
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
}
