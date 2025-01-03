package ru.beauty_salon.repositories;

import java.util.List;

import ru.beauty_salon.models.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment> {

    List<Appointment> getByClientId(Long id);

}
