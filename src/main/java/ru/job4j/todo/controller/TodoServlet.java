package ru.job4j.todo.controller;

import ru.job4j.todo.dao.impl.hibernate.ImplItemDAO;
import ru.job4j.todo.model.Item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TodoServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        send(resp, ImplItemDAO.getInstance().getAllItems());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);
        Item item1 = ImplItemDAO.getInstance()
                .createItem(item.getDescription(), (int) req.getSession().getAttribute("userId"));
        send(resp, item1);
    }
}
