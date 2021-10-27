package ru.job4j.todo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserInfoServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession se = req.getSession();
        send(resp, new Object[]{
                se.getAttribute("roleId"),
                se.getAttribute("name")
        });
    }
}