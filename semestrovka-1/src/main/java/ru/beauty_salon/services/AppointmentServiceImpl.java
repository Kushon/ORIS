package ru.beauty_salon.services;

import lombok.AllArgsConstructor;
import ru.beauty_salon.models.Appointment;
import ru.beauty_salon.repositories.AppointmentRepository;

@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Override
    public boolean create(Appointment appointment) {
        return appointmentRepository.create(appointment);
    }

}
