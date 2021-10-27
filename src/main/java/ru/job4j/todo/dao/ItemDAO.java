package ru.job4j.todo.dao;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface ItemDAO {
    Item createItem(String description, int userId);

    Item getItem(int id);

    List<Item> getAllItems();

    boolean updateItem(Item item);

    boolean deleteItem(int id);

}
