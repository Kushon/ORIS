INSERT INTO
    procedures (name, description, price, duration)
VALUES
    (
        'Классический маникюр',
        'Обработка кутикулы, форма ногтей, покрытие лаком.',
        800,
        90
    ),
    (
        'Маникюр с гель-лаком',
        'Долговечное покрытие гель-лаком, включая обработку кутикулы.',
        1200,
        90
    ),
    (
        'Дизайн ногтей',
        'Нанесение художественного дизайна на ногти (френч, рисунки и т.д.).',
        300,
        90
    ),
    (
        'SPA-маникюр',
        'Уход за руками, массаж, парафиновые процедуры.',
        1500,
        90
    ),
    (
        'Классический педикюр',
        'Обработка ногтей и стоп, удаление ороговевшей кожи.',
        1000,
        90
    ),
    (
        'Педикюр с гель-лаком',
        'Долговечное покрытие гель-лаком для ногтей на ногах.',
        1400,
        90
    ),
    (
        'SPA-педикюр',
        'Полный уход за стопами, массаж, ароматерапия.',
        2000,
        90
    ),
    (
        'Коррекция формы ногтей',
        'Устранение вросших ногтей и коррекция формы.',
        600,
        90
    ),
    (
        'Коррекция бровей',
        'Формирование идеальной формы бровей (восковая или ниточная).',
        500,
        90
    ),
    (
        'Окрашивание бровей',
        'Подбор цвета и окрашивание бровей.',
        700,
        90
    ),
    (
        'Ламинирование бровей',
        'Процедура для создания ухоженного вида и фиксации формы.',
        1500,
        90
    ),
    (
        'Долговременная укладка бровей',
        'Укладка и фиксация бровей на длительный срок.',
        1200,
        90
    );

-- Insert sample persons
INSERT INTO
    persons (first_name, last_name)
VALUES
    ('Anna', 'Petrova'),
    ('Elena', 'Ivanova'),
    ('Olga', 'Sidorova'),
    ('Maria', 'Kuznetsova'),
    ('Natalia', 'Volkova');

INSERT INTO
    employees (position, experience, is_active, hire_date)
VALUES
    ('EYEBROWS', 3, true, '2022-03-15'),
    ('EYELASHES', 2, true, '2023-01-10'),
    ('NOGOTOCKI', 5, true, '2020-07-01'),
    ('EYEBROWS', 1, true, '2024-06-01'),
    ('EYELASHES', 4, true, '2021-11-20');

INSERT INTO
    clients (fav_procedure)
VALUES
    (1),
    (2),
    (3),
    (1),
    (2);

INSERT INTO
    accounts (email, hash)
VALUES
    (
        'anna.petrova@example.com',
        'hashed_password_1'
    ),
    (
        'elena.ivanova@example.com',
        'hashed_password_2'
    ),
    (
        'olga.sidorova@example.com',
        'hashed_password_3'
    ),
    (
        'maria.kuznetsova@example.com',
        'hashed_password_4'
    ),
    (
        'natalia.volkova@example.com',
        'hashed_password_5'
    );

INSERT INTO
    users (person, employee, client, account)
VALUES
    (1, 1, 1, 1),
    (2, 2, 2, 2),
    (3, 3, 3, 3),
    (4, 4, 4, 4),
    (5, 5, 5, 5);

INSERT INTO
    employee_procedures (employee_id, procedure_id)
VALUES
    -- Employee 1
    (1, 9), -- Коррекция бровей
    (1, 10), -- Окрашивание бровей
    (1, 11), -- Ламинирование бровей
    (1, 12), -- Долговременная укладка бровей
    -- Employee 3
    (3, 1), -- Классический маникюр
    (3, 2), -- Маникюр с гель-лаком
    (3, 3), -- Дизайн ногтей
    (3, 4), -- SPA-маникюр
    (3, 5), -- Классический педикюр
    (3, 6), -- Педикюр с гель-лаком
    (3, 7), -- SPA-педикюр
    (3, 8), -- Коррекция формы ногтей
    -- Employee 4
    (4, 9), -- Коррекция бровей
    (4, 10), -- Окрашивание бровей
    (4, 11), -- Ламинирование бровей
    (4, 12);

-- Долговременная укладка бровей