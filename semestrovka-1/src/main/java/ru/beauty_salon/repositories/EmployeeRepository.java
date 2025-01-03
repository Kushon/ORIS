package ru.beauty_salon.repositories;

import java.util.List;

import ru.beauty_salon.dto.EmployeeDto;
import ru.beauty_salon.models.Employee;

public interface EmployeeRepository extends CrudRepository<Employee> {

    List<EmployeeDto> getAllByProcedureId(Long id);

}
