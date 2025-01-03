package ru.beauty_salon.servlets;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ru.beauty_salon.CredentalsValidator;
import ru.beauty_salon.dao.UserDao;
import ru.beauty_salon.exceptions.InvalidCredentials;
import ru.beauty_salon.exceptions.UnprocessableEntry;
import ru.beauty_salon.services.UserService;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        userService = (UserService) servletContext.getAttribute("userService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            CredentalsValidator.checkEmail(email);
            CredentalsValidator.checkPassword(password);
        } catch (UnprocessableEntry e) {
            resp.setStatus(e.getStatus());
            resp.setContentType("application/json");
            resp.getWriter().write(e.getMessage());
            
            return;
        }

        HttpSession session = req.getSession(true);

        try {
            boolean isLoggedIn = userService.loginUser(email, password, session.getId());
            if (!isLoggedIn) {
                resp.sendError(500, "Не смог залогинить пользователя");
                return;
            }

            UserDao userDao = userService.getDAOByEmail(email).orElseThrow();
            session.setAttribute("userDao", userDao);

            resp.sendRedirect("/home");
            return;
        } catch (InvalidCredentials e) {
            resp.setStatus(e.getStatus());
            resp.setContentType("application/json");
            resp.getWriter().write(e.getMessage());
            return;
        }
    }
}