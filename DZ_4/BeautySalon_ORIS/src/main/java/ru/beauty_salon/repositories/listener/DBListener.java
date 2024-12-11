package ru.beauty_salon.repositories.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.beauty_salon.repositories.impl.UserRepositoryImpl;
import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.repositories.UserRepository;

@WebListener
public class DBListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Properties props = getProperties();
            ConnectionPool pool = initializeConnectionPool();

            ServletContext context = sce.getServletContext();
            context.setAttribute("connectionPool", pool);

            UserRepository userRepository = new UserRepositoryImpl(pool);
        } catch (SQLException e) {
            throw new RuntimeException("Application startup failed due to database connection issues", e);
        }

    }

    private ConnectionPool initializeConnectionPool() throws SQLException {
        return new ConnectionPool();
    }

    private Properties getProperties() {
        Properties props = new Properties();
        try {
            props.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));

            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ConnectionPool pool = (ConnectionPool) context.getAttribute("connectionPool");
        if (pool != null) {
            try {
                pool.shutdown();
            } catch (SQLException e) {
                System.out.println("Could not destroy the connection pool!");
                e.printStackTrace();
            }
        }
    }
}
