package ru.job4j.todo.controller;

import ru.job4j.todo.dao.impl.HbnItemDAO;
import ru.job4j.todo.model.Item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TodoUpServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item frontItem = GSON.fromJson(req.getReader(), Item.class);
        HbnItemDAO.getInstance().updateItem(frontItem);
        send(resp, frontItem);
    }
}
