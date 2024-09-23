INSERT INTO departments (id, name)
VALUES (1, 'Ophthalmology'),
       (2, 'Surgery'),
       (3, 'Immunology');

INSERT INTO rooms (id, number, number_of_places, department_id)
VALUES (1, 400, 10, 1),
       (2, 300, 20, 2),
       (3, 500, 20, 3);

INSERT INTO doctors (id, first_name, last_name)
VALUES (1, 'John', 'Doe'),
       (2, 'John', 'Smith'),
       (3, 'Robert', 'Miller'),
       (4, 'Jennifer', 'Wilson');

INSERT INTO department2doctor (department_id, doctor_id)
VALUES (1, 1),
       (1, 4),
       (2, 2),
       (3, 3);