package ru.beauty_salon.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ru.beauty_salon.models.Appointment;
import ru.beauty_salon.dao.UserDao;
import ru.beauty_salon.dto.EmployeeDto;
import ru.beauty_salon.dto.ProcedureDto;
import ru.beauty_salon.enums.AppointmentStatus;
import ru.beauty_salon.services.EmployeeService;
import ru.beauty_salon.services.ProcedureService;
import ru.beauty_salon.services.AppointmentService;

@WebServlet(value = "/appointment")
public class AppointmentServlet extends HttpServlet {

    private EmployeeService employeeService;
    private AppointmentService appointmentService;
    private ProcedureService procedureService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        employeeService = (EmployeeService) servletContext.getAttribute("employeeService");
        appointmentService = (AppointmentService) servletContext.getAttribute("appointmentService");
        procedureService = (ProcedureService) servletContext.getAttribute("procedureService");

        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long procedure = Long.parseLong(req.getParameter("procedureId"));
            Long employee = Long.parseLong(req.getParameter("employeeId"));
            Timestamp appointmentDate = Timestamp.valueOf(LocalDateTime.parse(req.getParameter("appointmentDate")));

            HttpSession session = req.getSession(false);
            UserDao userDao = (UserDao) session.getAttribute("userDao");

            appointmentService.create(Appointment.builder()
                    .client(userDao.getClient().getId())
                    .employee(employee)
                    .procedure(procedure)
                    .appointmentDate(appointmentDate)
                    .status(AppointmentStatus.CONFIRMED)
                    .build());
            resp.sendRedirect("/profile");
        } catch (NumberFormatException e) {
            resp.sendError(422, "Неправильный id работника или id услуги");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            List<EmployeeDto> employees = employeeService.getAllByProcedureId(id);

            ProcedureDto procedure = procedureService.getById(id).orElseThrow();

            req.setAttribute("procedure", procedure);
            req.setAttribute("employees", employees);

            req.getRequestDispatcher("/jsp/appointment.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/price");
        }
    }
}
