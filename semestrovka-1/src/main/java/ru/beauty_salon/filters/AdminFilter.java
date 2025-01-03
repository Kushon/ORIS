package ru.beauty_salon.filters;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ru.beauty_salon.dao.UserDao;

@WebFilter(urlPatterns = { "/admin/*", "/admin" })
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userDao") == null
                || !((UserDao) session.getAttribute("userDao")).getAccount().isAdmin()) {
            resp.sendRedirect("/login");
        } else {
            chain.doFilter(req, resp);
        }
    }
}
