package ru.job4j.todo.dao.impl.hibernate;

import ru.job4j.todo.dao.UserDAO;
import ru.job4j.todo.model.User;

public class ImplUserDAO extends BaseImplHbn implements UserDAO {

    @Override
    public User createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return getUserById(this.transactionWrapper(
                session -> (Integer) session.save(user)));
    }

    @Override
    public User getUserById(int id) {
        return this.transactionWrapper(session -> session.get(User.class, id));
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) this.transactionWrapper(session -> session
                .createQuery("from User where email =:email")
                .setParameter("email", email)
                .uniqueResult());
    }

    public static ImplUserDAO getInstance() {
        return ImplUserDAO.SingleHbnUserDAO.INSTANCE;
    }

    private static class SingleHbnUserDAO {
        private static final ImplUserDAO INSTANCE = new ImplUserDAO();
    }
}
