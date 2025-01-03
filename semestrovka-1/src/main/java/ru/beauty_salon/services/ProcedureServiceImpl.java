package ru.beauty_salon.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import ru.beauty_salon.dto.ProcedureDto;
import ru.beauty_salon.models.Procedure;
import ru.beauty_salon.repositories.ProcedureRepository;

@AllArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private ProcedureRepository procedureRepository;

    public List<ProcedureDto> getAll() {
        List<Procedure> rawProcedure = procedureRepository.getAll();

        return rawProcedure.stream()
                .map(rp -> ProcedureDto.builder()
                        .id(rp.getId())
                        .name(rp.getName())
                        .description(rp.getDescription())
                        .price(rp.getPrice())
                        .build())
                .toList();
    }

    @Override
    public Optional<ProcedureDto> getById(Long id) {
        Procedure procedure = procedureRepository.getById(id).orElseThrow();

        return Optional.of(ProcedureDto.builder()
                .id(procedure.getId())
                .name(procedure.getName())
                .description(procedure.getDescription())
                .price(procedure.getPrice())
                .build());
    }
}
