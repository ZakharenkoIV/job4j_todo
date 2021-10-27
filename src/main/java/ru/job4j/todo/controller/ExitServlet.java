package ru.job4j.todo.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession se = req.getSession();
        se.removeAttribute("roleId");
        se.removeAttribute("name");
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
