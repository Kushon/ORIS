package ru.beauty_salon.servlets;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.beauty_salon.CredentalsValidator;
import ru.beauty_salon.exceptions.UnprocessableEntry;
import ru.beauty_salon.services.UserService;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        userService = (UserService) servletContext.getAttribute("userService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/register.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        try {
            CredentalsValidator.checkFirstName(firstName);
            CredentalsValidator.checkLastName(lastName);

            CredentalsValidator.checkEmail(email);
            CredentalsValidator.checkPassword(password);

            CredentalsValidator.checkConfirmPassword(confirmPassword);
            CredentalsValidator.matchPasswords(password, confirmPassword);
        } catch (UnprocessableEntry e) {
            resp.setStatus(e.getStatus());
            resp.setContentType("application/json");
            resp.getWriter().write(e.getMessage());
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hash = bCryptPasswordEncoder.encode(password);

        boolean isRegistered = userService.registerUser(firstName, lastName, email, hash);
        if (!isRegistered) {
            resp.sendError(500, "Не смог зарегистрировать пользователя");
            return;
        }
        resp.sendRedirect("/login");
        return;
    }

}
