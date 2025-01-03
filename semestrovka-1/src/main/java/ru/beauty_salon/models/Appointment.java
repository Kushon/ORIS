package ru.beauty_salon.models;

import java.sql.Timestamp;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.beauty_salon.enums.AppointmentStatus;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Appointment extends Base {

    private Long client;
    private Long employee;
    private Long procedure;
    private Timestamp appointmentDate;
    private AppointmentStatus status;

}
