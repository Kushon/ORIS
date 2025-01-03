package ru.beauty_salon.services;

import java.util.List;
import java.util.Optional;

import ru.beauty_salon.dao.UserDao;
import ru.beauty_salon.dto.AppointmentDto;
import ru.beauty_salon.exceptions.InvalidCredentials;

public interface UserService {

    Optional<UserDao> getDAOById(Long id);

    Optional<UserDao> getDAOByEmail(String email);

    boolean registerUser(String firstName, String lastName, String email, String hash);

    boolean loginUser(String email, String rawPassword, String sessionId) throws InvalidCredentials;

    List<AppointmentDto> getActiveAppointments(UserDao user);
    
    List<AppointmentDto> getAppointmentHistory(UserDao user);

}
