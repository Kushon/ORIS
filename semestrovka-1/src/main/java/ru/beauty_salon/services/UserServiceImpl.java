package ru.beauty_salon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

import ru.beauty_salon.dao.UserDao;
import ru.beauty_salon.dao.UserDao.UserDaoBuilder;
import ru.beauty_salon.dto.AppointmentDto;
import ru.beauty_salon.exceptions.InvalidCredentials;
import ru.beauty_salon.models.Account;
import ru.beauty_salon.models.Client;
import ru.beauty_salon.models.Employee;
import ru.beauty_salon.models.Person;
import ru.beauty_salon.models.User;
import ru.beauty_salon.repositories.AccountRepository;
import ru.beauty_salon.repositories.EmployeeRepository;
import ru.beauty_salon.repositories.ClientRepository;
import ru.beauty_salon.repositories.PersonRepository;
import ru.beauty_salon.repositories.UserRepository;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private PersonRepository personRepository;
    private AccountRepository accountRepository;
    private EmployeeRepository employeeRepository;
    private ClientRepository clientRepository;
    private UserRepository userRepository;

    @Override
    public boolean registerUser(String firstName, String lastName, String email, String hash) {
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        personRepository.create(person);

        Account account = Account.builder()
                .email(email)
                .hash(hash)
                .build();
        accountRepository.create(account);

        Client client = Client.builder()
                .build();
        clientRepository.create(client);

        User user = User.builder()
                .person(person.getId())
                .account(account.getId())
                .client(client.getId())
                .build();

        return userRepository.create(user);
    }

    @Override
    public boolean loginUser(String email, String rawPassword, String sessionId) throws InvalidCredentials {
        Account account = accountRepository.getByEmail(email).orElseThrow(() -> new InvalidCredentials());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(rawPassword, account.getHash()))
            throw new InvalidCredentials();
            
        return true;
    }

    public Optional<UserDao> getDAOById(Long id) {
        Optional<User> optionalUser = userRepository.getById(id);

        if (optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.orElseThrow();

        Optional<Person> optionalPerson = personRepository.getById(user.getPerson());
        Optional<Account> optionalAccount = accountRepository.getById(user.getAccount());
        Optional<Employee> optionalEmployee = employeeRepository.getById(user.getEmployee());
        Optional<Client> optionalClient = clientRepository.getById(user.getClient());

        UserDaoBuilder userDaoBuilder = UserDao.builder();

        userDaoBuilder.id(user.getId());
        optionalPerson.ifPresent(p -> userDaoBuilder.person(p));
        optionalAccount.ifPresent(a -> userDaoBuilder.account(a));
        optionalEmployee.ifPresent(e -> userDaoBuilder.employee(e));
        optionalClient.ifPresent(c -> userDaoBuilder.client(c));

        return Optional.of(userDaoBuilder.build());
    }

    @Override
    public Optional<UserDao> getDAOByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.getByEmail(email);

        if (optionalAccount.isEmpty())
            return Optional.empty();

        Account account = optionalAccount.orElseThrow();

        User user = userRepository.getByAccountId(account.getId()).orElseThrow();

        Optional<Person> optionalPerson = personRepository.getById(user.getPerson());
        Optional<Employee> optionalEmployee = employeeRepository.getById(user.getEmployee());
        Optional<Client> optionalClient = clientRepository.getById(user.getClient());

        UserDaoBuilder userDaoBuilder = UserDao.builder();

        userDaoBuilder.id(user.getId());
        userDaoBuilder.account(account);
        optionalPerson.ifPresent(a -> userDaoBuilder.person(a));
        optionalEmployee.ifPresent(e -> userDaoBuilder.employee(e));
        optionalClient.ifPresent(c -> userDaoBuilder.client(c));

        return Optional.of(userDaoBuilder.build());
    }

    @Override
    public List<AppointmentDto> getActiveAppointments(UserDao user) {
        return userRepository.getActiveAppointments(user.getId());
    }

    @Override
    public List<AppointmentDto> getAppointmentHistory(UserDao user) {
        return userRepository.getAppointmentHistory(user.getId());
    }
}
