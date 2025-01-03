package ru.beauty_salon.servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.beauty_salon.repositories.RowRepository;

@WebServlet(name = "admin", value = "/admin/*")
public class AdminServlet extends HttpServlet {

    private RowRepository rowRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        rowRepository = (RowRepository) servletContext.getAttribute("rowRepository");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subroute = req.getPathInfo();

        subroute = (subroute != null && subroute.length() > 1) ? subroute.substring(1) : null;

        if (subroute == null) {
            resp.sendRedirect("/admin/procedures");
            return;
        }

        List<String> columns = rowRepository.getColumns(subroute);
        List<List<String>> rows = rowRepository.getRows(subroute);

        req.setAttribute("columns", columns);
        req.setAttribute("rows", rows);
        req.getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
    }
}
