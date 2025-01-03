package ru.beauty_salon.repositories;

import java.util.List;
import java.util.Optional;

import ru.beauty_salon.dto.AppointmentDto;
import ru.beauty_salon.models.User;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> getByAccountId(Long id);

    List<AppointmentDto> getActiveAppointments(Long id);

    List<AppointmentDto> getAppointmentHistory(Long id);

}
