insert into departments (id, name)
values (1, 'Ophthalmology'),
       (2, 'Surgery'),
       (3, 'Immunology');

insert into rooms (id, number, number_of_places, department_id)
values (1, 400, 10, 1),
       (2, 300, 20, 2),
       (3, 500, 20, 3);

insert into doctors (id, first_name, last_name, email)
values (1, 'John', 'Doe' , 'oleg.tsebak16@gmail.com'),
       (2, 'John', 'Smith', 'oleg.tsebak16@gmail.com'),
       (3, 'Robert', 'Miller', 'oleg.tsebak16@gmail.com'),
       (4, 'Jennifer', 'Wilson', 'oleg.tsebak16@gmail.com');

insert into department2doctor (department_id, doctor_id)
values (1, 1),
       (1, 4),
       (2, 2),
       (3, 3);

insert into patients (id, first_name, last_name, treatment_start_date, treatment_end_date, doctor_id, room_id, released, illness_description)
values (1, 'John', 'Patient1', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 2, 2, false, 'Problem with stomach. Surgery is needed.'),
       (2, 'Adam', 'Patient2', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 2, 2, false, 'Broken left arm.'),
       (3, 'Scott', 'Patient3', '2024-09-10T00:00:00', '2024-09-11T00:00:00', 3, 2, false, 'Broken right leg.'),
       (4, 'James', 'Patient4', '2024-01-10T00:00:00', '2024-09-11T00:00:00', 3, 3, false, 'Severe allergic reactions or chronic conditions - asthma'),
       (5, 'Jenifer', 'Patient5', '2024-02-10T00:00:00', '2024-11-15T00:00:00', 3, 3, false, 'Allergic reactions - probably asthma'),
       (6, 'Eloy', 'Patient6', '2024-03-10T00:00:00', '2024-12-15T00:00:00', 2, 3, false, 'Immunodeficiency. Need surgery.'),
       (7, 'Trinity', 'Patient7', '2024-04-10T00:00:00', '2024-11-15T00:00:00', 3, 3, false, 'Allergic reactions - probably asthma'),
       (8, 'Neo', 'Patient8', '2024-05-10T00:00:00', '2024-12-15T00:00:00', 1, 1, false, 'Problems with the eyes. Vision is low.');
