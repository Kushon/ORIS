package ru.beauty_salon.services;

import java.util.List;

import lombok.AllArgsConstructor;
import ru.beauty_salon.dto.EmployeeDto;
import ru.beauty_salon.repositories.EmployeeRepository;

@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> getAllByProcedureId(Long id) {
        return employeeRepository.getAllByProcedureId(id);
    }
    
}
