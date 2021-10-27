package ru.job4j.todo.controller;

import ru.job4j.todo.dao.UserDAO;
import ru.job4j.todo.dao.impl.hibernate.ImplUserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDAO userData = ImplUserDAO.getInstance();
        if (userData.getUserByEmail(email) == null) {
            userData.createUser(name, email, password);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            resp.sendError(resp.SC_CONFLICT, "Пользователь с таким email уже существует");
        }
    }
}
