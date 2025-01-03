package ru.beauty_salon.filters;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns =  {"/profile", "/appointment", "/logout"})
public class ProfileFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        
        if (session == null || session.getAttribute("userDao") == null) {
            resp.sendRedirect("/login");
        } else {
            chain.doFilter(req, resp);
        }
    }
}
