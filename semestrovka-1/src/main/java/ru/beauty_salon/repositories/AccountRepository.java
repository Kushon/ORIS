package ru.beauty_salon.repositories;

import java.util.Optional;

import ru.beauty_salon.models.Account;

public interface AccountRepository extends CrudRepository<Account> {

    Optional<Account> getByEmail(String email);
    
}
