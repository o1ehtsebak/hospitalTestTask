INSERT INTO departments (id, name)
VALUES (1, 'Ophthalmology'),
       (2, 'Surgery'),
       (3, 'Immunology');

INSERT INTO rooms (id, number, number_of_places, department_id)
VALUES (1, 400, 10, 1),
       (2, 300, 20, 2),
       (3, 500, 20, 3);

INSERT INTO doctors (id, first_name, last_name, email)
VALUES (1, 'John', 'Doe' , 'oleg.tsebak16@gmail.com'),
       (2, 'John', 'Smith', 'oleg.tsebak16@gmail.com'),
       (3, 'Robert', 'Miller', 'oleg.tsebak16@gmail.com'),
       (4, 'Jennifer', 'Wilson', 'oleg.tsebak16@gmail.com');

INSERT INTO department2doctor (department_id, doctor_id)
VALUES (1, 1),
       (1, 4),
       (2, 2),
       (3, 3);

INSERT INTO patients (id, first_name, last_name, treatment_start_date, treatment_end_date, doctor_id, room_id, released)
VALUES (1, 'John', 'Patient1', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 2, 2, false),
       (2, 'Adam', 'Patient2', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 2, 2, false),
       (3, 'Scott', 'Patient3', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 2, 2, false),
       (4, 'James', 'Patient4', '2024-01-10T00:00:00', '2024-09-11T00:00:00', 3, 3, false),
       (5, 'Jenifer', 'Patient5', '2024-02-10T00:00:00', '2024-11-15T00:00:00', 3, 3, false),
       (6, 'Eloy', 'Patient6', '2024-03-10T00:00:00', '2024-12-15T00:00:00', 3, 3, false),
       (7, 'Trinity', 'Patient7', '2024-04-10T00:00:00', '2024-11-15T00:00:00', 3, 3, false),
       (8, 'Neo', 'Patient8', '2024-05-10T00:00:00', '2024-12-15T00:00:00', 1, 1, false);
