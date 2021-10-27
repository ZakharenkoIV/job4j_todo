package ru.job4j.todo.dao;

import ru.job4j.todo.model.User;

public interface UserDAO {
    User createUser(String name, String email, String password);

    User getUserById(int id);

    User getUserByEmail(String email);
}
