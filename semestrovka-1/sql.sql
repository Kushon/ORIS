CREATE TYPE appointment_status AS ENUM (
    'CONFIRMED', -- Подтверждена
    'CANCELED', -- Отменена
    'PENDING', -- Ожидает подтверждения
    'COMPLETED', -- Завершена
    'NO_SHOW' -- Неявка
);

CREATE TYPE position_enum AS ENUM ('EYEBROWS', 'EYELASHES', 'NOGOTOCKI', 'JOBLESS');

-- Создание таблицы procedures
CREATE TABLE
    procedures (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT,
        price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
        duration INTEGER NOT NULL CHECK (duration > 0)
    );

-- Создание таблицы persons
CREATE TABLE
    persons (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR(50),
        last_name VARCHAR(50)
    );

-- Создание таблицы employees
CREATE TABLE
    employees (
        id SERIAL PRIMARY KEY,
        position position_enum NOT NULL DEFAULT 'JOBLESS',
        experience INTEGER NOT NULL DEFAULT 0,
        is_active BOOLEAN NOT NULL DEFAULT FALSE,
        hire_date DATE NOT NULL,
        termination_date DATE
    );

-- Создание таблицы clients
CREATE TABLE
    clients (
        id SERIAL PRIMARY KEY,
        fav_procedure INT REFERENCES procedures (id) ON DELETE SET NULL
    );

-- Создание таблицы accounts
CREATE TABLE
    accounts (
        id SERIAL PRIMARY KEY,
        email VARCHAR(254),
        hash CHAR(60),
        is_admin BOOLEAN DEFAULT false
    );

-- Создание таблицы users
CREATE TABLE
    users (
        id SERIAL PRIMARY KEY,
        person INT REFERENCES persons (id) ON DELETE SET NULL,
        employee INT REFERENCES employees (id) ON DELETE SET NULL,
        client INT REFERENCES clients (id) ON DELETE SET NULL,
        account INT REFERENCES accounts (id) ON DELETE SET NULL
    );

-- Создание таблицы appointments
CREATE TABLE
    appointments (
        id SERIAL PRIMARY KEY,
        client INT REFERENCES clients (id) ON DELETE CASCADE,
        employee INT REFERENCES employees (id) ON DELETE SET NULL,
        procedure INT REFERENCES procedures (id) ON DELETE SET NULL,
        appointment_date TIMESTAMP NOT NULL,
        status appointment_status NOT NULL
    );

CREATE TABLE
    employee_procedures (
        id SERIAL PRIMARY KEY,
        employee_id INT REFERENCES employees (id) ON DELETE CASCADE,
        procedure_id INT REFERENCES procedures (id) ON DELETE CASCADE,
        UNIQUE (employee_id, procedure_id)
    );