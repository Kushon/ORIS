package ru.beauty_salon.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import ru.beauty_salon.models.Account;
import ru.beauty_salon.models.Client;
import ru.beauty_salon.models.Employee;
import ru.beauty_salon.models.Person;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDao {

    private Long id;

    private Person person;
    private Account account;
    private Client client;
    private Employee employee;

}