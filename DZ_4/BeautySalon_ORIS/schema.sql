-- создание таблиц
-- пользователи - храним там и клиентов, и мастеров
CREATE TABLE
    users (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        number VARCHAR(20) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE
    employees (
        id INT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
        position VARCHAR(100) NOT NULL,
        experience DOUBLE PRECISION NOT NULL,
        is_active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE
    clients (
        id INT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE
    );

CREATE TABLE
    procedures (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        duration INTERVAL NOT NULL
    );

CREATE TABLE
    appointments (
        id SERIAL PRIMARY KEY,
        client INT NOT NULL REFERENCES clients (id) ON DELETE CASCADE,
        employee INT NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
        procedure INT NOT NULL REFERENCES procedures (id) ON DELETE CASCADE,
        appointment_date TIMESTAMP NOT NULL,
        status VARCHAR(50) NOT NULL
    );

CREATE TABLE
    payments (
        id SERIAL PRIMARY KEY,
        procedure INT NOT NULL REFERENCES procedures (id) ON DELETE CASCADE,
        price DOUBLE PRECISION NOT NULL,
        payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    schedule (
        id SERIAL PRIMARY KEY,
        employee INT NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
        work_day DATE NOT NULL,
        start_time TIME NOT NULL,
        end_time TIME NOT NULL
    );