package ru.beauty_salon.services;

import java.util.List;
import java.util.Optional;

import ru.beauty_salon.dto.ProcedureDto;

public interface ProcedureService {
    
    List<ProcedureDto> getAll();
    Optional<ProcedureDto> getById(Long id);
    
}
