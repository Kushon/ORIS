package ru.beauty_salon.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beauty_salon.dao.UserDao;
import ru.beauty_salon.dto.AppointmentDto;
import ru.beauty_salon.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "profile", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        userService = (UserService) servletContext.getAttribute("userService");

        super.init(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        UserDao userDao = (UserDao) session.getAttribute("userDao");

        List<AppointmentDto> activeAppointments = userService.getActiveAppointments(userDao);
        List<AppointmentDto> appointmentHistory = userService.getAppointmentHistory(userDao);

        req.setAttribute("name", userDao.getPerson().getFirstName() + ' ' + userDao.getPerson().getLastName());
        req.setAttribute("email", userDao.getAccount().getEmail());
        req.setAttribute("activeAppointments", activeAppointments);
        req.setAttribute("appointmentHistory", appointmentHistory);

        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);

    }
}
