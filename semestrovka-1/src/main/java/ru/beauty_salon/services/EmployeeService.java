package ru.beauty_salon.services;

import java.util.List;

import ru.beauty_salon.dto.EmployeeDto;

public interface EmployeeService {
    
    List<EmployeeDto> getAllByProcedureId(Long id);

}
