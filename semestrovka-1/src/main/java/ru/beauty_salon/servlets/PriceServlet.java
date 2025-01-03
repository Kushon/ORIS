package ru.beauty_salon.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import ru.beauty_salon.dto.ProcedureDto;
import ru.beauty_salon.services.ProcedureService;

@WebServlet(name = "price", value = "/price")
public class PriceServlet extends HttpServlet {

    private ProcedureService procedureService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        procedureService = (ProcedureService) servletContext.getAttribute("procedureService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProcedureDto> procedures = procedureService.getAll();

        req.setAttribute("procedures", procedures);
        req.getRequestDispatcher("/jsp/price.jsp").forward(req, resp);
    }
}