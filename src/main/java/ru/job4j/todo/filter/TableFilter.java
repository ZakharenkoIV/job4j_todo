package ru.job4j.todo.filter;

import ru.job4j.todo.dao.impl.hibernate.ImplItemDAO;
import ru.job4j.todo.model.Category;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TableFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        List<Category> categories = ImplItemDAO.getInstance().selectAllCategories();
        req.setAttribute("allCategories", categories);
        chain.doFilter(req, resp);
    }
}
