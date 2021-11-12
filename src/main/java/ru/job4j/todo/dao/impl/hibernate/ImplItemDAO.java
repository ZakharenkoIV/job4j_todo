package ru.job4j.todo.dao.impl.hibernate;

import ru.job4j.todo.dao.ItemDAO;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.List;

public class ImplItemDAO extends BaseImplHbn implements ItemDAO {

    private ImplItemDAO() {
    }

    @Override
    public Item createItem(String description, int userId, int[] categories) {
        Item item = new Item();
        item.setDescription(description);
        item.setUser(ImplUserDAO.getInstance().getUserById(userId));
        if (categories.length == 0) {
            item.addCategory(this.getCategoryById(4));
        } else {
            for (int key : categories) {
                item.addCategory(this.getCategoryById(key));
            }
        }
        return getItem(this.transactionWrapper(session -> (Integer) session.save(item)));
    }

    @Override
    public Item getItem(int id) {
        return this.transactionWrapper(session -> session.createQuery(
                "select distinct i "
                        + "from Item i "
                        + "left join fetch i.categories c "
                        + "where i.id in :id ", Item.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    @Override
    public List<Item> getAllItems() {
        return this.transactionWrapper(session -> session.createQuery(
                "select distinct i from Item i left join fetch i.categories")
                .list());
    }

    @Override
    public boolean updateItem(Item item) {
        return this.transactionWrapper(session -> {
            int modifiedLines = session.createQuery("update Item set done = :done where id = :id")
                    .setParameter("done", item.isDone())
                    .setParameter("id", item.getId())
                    .executeUpdate();
            return modifiedLines > 0;
        });
    }

    @Override
    public boolean deleteItem(int id) {
        return this.transactionWrapper(session -> {
            session.delete(session.get(Item.class, id));
            return getItem(id) == null;
        });
    }

    public Category getCategoryById(int id) {
        return this.transactionWrapper(session -> session.get(Category.class, id));
    }

    public List<Category> selectAllCategories() {
        return this.transactionWrapper(session -> session.createQuery(
                "select c from Category c", Category.class).list());
    }

    public static ImplItemDAO getInstance() {
        return SingleHbnItemDAO.INSTANCE;
    }

    private static class SingleHbnItemDAO {
        private static final ImplItemDAO INSTANCE = new ImplItemDAO();
    }
}
