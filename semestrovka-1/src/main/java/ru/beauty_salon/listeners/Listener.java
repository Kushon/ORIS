package ru.beauty_salon.listeners;

import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import ru.beauty_salon.ConnectionPool;
import ru.beauty_salon.repositories.RowRepository;

import ru.beauty_salon.repositories.PersonRepository;
import ru.beauty_salon.repositories.EmployeeRepository;
import ru.beauty_salon.repositories.ProcedureRepository;
import ru.beauty_salon.repositories.ClientRepository;
import ru.beauty_salon.repositories.AccountRepository;
import ru.beauty_salon.repositories.UserRepository;
import ru.beauty_salon.repositories.AppointmentRepository;

import ru.beauty_salon.repositories.impl.PersonRepositoryImpl;
import ru.beauty_salon.repositories.impl.EmployeeRepositoryImpl;
import ru.beauty_salon.repositories.impl.ProcedureRepositoryImpl;
import ru.beauty_salon.repositories.impl.ClientRepositoryImpl;
import ru.beauty_salon.repositories.impl.AccountRepositoryImpl;
import ru.beauty_salon.repositories.impl.UserRepositoryImpl;
import ru.beauty_salon.repositories.impl.AppointmentRepositoryImpl;

import ru.beauty_salon.services.EmployeeService;
import ru.beauty_salon.services.ProcedureService;
import ru.beauty_salon.services.UserService;
import ru.beauty_salon.services.AppointmentService;

import ru.beauty_salon.services.EmployeeServiceImpl;
import ru.beauty_salon.services.ProcedureServiceImpl;
import ru.beauty_salon.services.UserServiceImpl;
import ru.beauty_salon.services.AppointmentServiceImpl;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();

            ConnectionPool pool = initializeConnectionPool();

            PersonRepository personRepository = new PersonRepositoryImpl(pool);
            EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(pool);
            ClientRepository clientRepository = new ClientRepositoryImpl(pool);
            AccountRepository accountRepository = new AccountRepositoryImpl(pool);
            UserRepository userRepository = new UserRepositoryImpl(pool);
            AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl(pool);
            ProcedureRepository procedureRepository = new ProcedureRepositoryImpl(pool);

            context.setAttribute("personRepository", personRepository);
            context.setAttribute("employeeRepository", employeeRepository);
            context.setAttribute("clientRepository", clientRepository);
            context.setAttribute("accountRepository", accountRepository);
            context.setAttribute("userRepository", userRepository);
            context.setAttribute("appointmentRepository", appointmentRepository);
            context.setAttribute("procedureRepository", procedureRepository);

            RowRepository rowRepository = new RowRepository(pool);
            context.setAttribute("rowRepository", rowRepository);

            UserService userService = new UserServiceImpl(
                    personRepository,
                    accountRepository,
                    employeeRepository,
                    clientRepository,
                    userRepository);
            ProcedureService procedureService = new ProcedureServiceImpl(procedureRepository);
            EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
            AppointmentService appointmentService = new AppointmentServiceImpl(appointmentRepository);

            context.setAttribute("userService", userService);
            context.setAttribute("procedureService", procedureService);
            context.setAttribute("employeeService", employeeService);
            context.setAttribute("appointmentService", appointmentService);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Application startup failed due to database connection issues", e);
        }

    }

    private ConnectionPool initializeConnectionPool() throws SQLException {
        return new ConnectionPool();
    }

}
