package ru.job4j.todo.model.dao;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface ItemDAO {
    Item createItem(String description);

    Item getItem(int id);

    List<Item> getAllItems();

    boolean updateItem(Item item);

    boolean deleteItem(int id);

}
