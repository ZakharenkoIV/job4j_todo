package ru.job4j.todo.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.todo.dao.impl.hibernate.ImplItemDAO;
import ru.job4j.todo.model.Item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class TodoServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        send(resp, ImplItemDAO.getInstance().getAllItems());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jo = new JSONObject(req.getReader().lines().collect(Collectors.joining()));
        String description = (String) jo.get("description");
        int[] keyCategories = new int[0];
        JSONArray jsonArray = jo.getJSONArray("categories");
        if (jsonArray != null) {
            int length = jsonArray.length();
            keyCategories = new int[length];
            for (int i = 0; i < length; i++) {
                keyCategories[i] = Integer.parseInt(jsonArray.get(i).toString());
            }
        }
        Item item = ImplItemDAO.getInstance()
                .createItem(
                        description,
                        (int) req.getSession().getAttribute("userId"),
                        keyCategories
                );
        send(resp, item);
    }
}
