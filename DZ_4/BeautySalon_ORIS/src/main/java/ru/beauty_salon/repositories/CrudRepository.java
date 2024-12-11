package ru.beauty_salon.repositories;

import java.util.List;
import java.util.Optional;

import ru.beauty_salon.models.Base;

public interface CrudRepository<T extends Base> {

    boolean create(T type);

    Optional<T> getById(Long id);

    List<T> getAll();

    boolean update(T type);

    boolean delete(T type);

}
