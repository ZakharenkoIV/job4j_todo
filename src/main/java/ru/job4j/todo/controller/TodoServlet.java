package ru.job4j.todo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.dao.impl.HbnItemDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TodoServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    private final HbnItemDAO itemStorage = new ru.job4j.todo.model.dao.impl.HbnItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(itemStorage.getAllItems());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        Item it = item;
        if (item.getDescription() != null) {
            it = itemStorage.createItem(item.getDescription());
        }
        if (item.getId() != 0) {
            it = itemStorage.getItem(item.getId());
            it.setDone(item.isDone());
            itemStorage.updateItem(it);
        }
        String json = GSON.toJson(it);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
