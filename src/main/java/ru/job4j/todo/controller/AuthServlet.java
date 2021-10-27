package ru.job4j.todo.controller;

import ru.job4j.todo.dao.impl.hibernate.ImplUserDAO;
import ru.job4j.todo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("auth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User foundUser = ImplUserDAO.getInstance().getUserByEmail(email);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            HttpSession se = req.getSession();
            if (se.getAttribute("roleId") == null) {
                se.setAttribute("roleId", foundUser.getRole().getId());
                se.setAttribute("userId", foundUser.getId());
                se.setAttribute("name", foundUser.getName());
            }
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            resp.sendError(resp.SC_UNAUTHORIZED, "Не верный email или пароль");
        }
    }
}