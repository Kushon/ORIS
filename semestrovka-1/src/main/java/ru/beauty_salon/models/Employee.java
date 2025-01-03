package ru.beauty_salon.models;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

import ru.beauty_salon.enums.EmployeePosition;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends Base {

    private EmployeePosition position;
    private int experience;
    private boolean isActive;
    private Date hireDate;
    private Date terminationDate;

}
