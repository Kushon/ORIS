package ru.beauty_salon.repositories;

import java.util.List;

import ru.beauty_salon.models.Procedure;

public interface ProcedureRepository extends CrudRepository<Procedure> {

    List<Procedure> getAll();
    
}
