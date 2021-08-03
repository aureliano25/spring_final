drop schema if exists epam2;

create table user
(
    id         int unsigned auto_increment primary key,
    login      varchar(255)     not null,
    password   varchar(255)     not null,
    first_name varchar(255)     not null,
    last_name  varchar(255)     not null,
    is_blocked bit default 0 not null,
    constraint login
        unique (login)
);

INSERT INTO user
(id, login, password, first_name, last_name, is_blocked)
VALUES
(1, 'admin', '$2a$10$umWXw1AKalE9TOXspP/mluVN32CG3WRZsvBj3D2e2tG7pxbHtiYYO', 'admin', 'admin', false),
(2, 'student', '$2y$12$JCFy.xO2hVjTYAJsyQdRCui6R.DBkao.ghq9pb58SGo/oP2l4szvy', 'Yaroslav', 'Pavliuk', false);



create table role
(
    id   int unsigned auto_increment
        primary key,
    role varchar(255) not null,
    constraint role_role_uindex
        unique (role)
);

insert into role (id, role)
values  (1, 'ADMIN'),
        (2, 'STUDENT');


create table user_role
(
    user_id int unsigned not null,
    role_id int unsigned not null,
    constraint user_role_user_id_role_id_uindex
        unique (user_id, role_id),
    constraint user_role_role_id_fk
        foreign key (role_id) references role (id),
    constraint user_role_user_id_fk
        foreign key (user_id) references user (id)
);

insert into user_role (user_id, role_id)
values  (1, 1),
        (2, 2);

create table subject
(
    id   int unsigned auto_increment
        primary key,
    name varchar(255) not null,
    constraint subject_name_uindex
        unique (name)
);
insert into subject (id, name)
values  (5, '.Net'),
        (4, 'Java'),
        (6, 'Javascript');

create table test
(
    id         int unsigned auto_increment primary key,
    subject_id int unsigned                                                not null,
    difficulty enum ('EASY', 'NORMAL', 'HARD', 'EXTREME') default 'NORMAL' null,
    timer      int unsigned                                                not null,
    title      varchar(255)                                                not null,
    constraint test_ibfk_1
        foreign key (subject_id) references subject (id)
            on delete cascade
);
create index subject_id
    on test (subject_id);
insert into test (id, subject_id, difficulty, timer, title)
values  (1, 5, 'NORMAL', 7, '.NET Core 3.0 Demo'),
        (2, 4, 'NORMAL', 17, 'JAVA DEMO PROGRAMMING (mvn)'),
        (3, 6, 'HARD', 10, 'JavaScript Demo'),
        (4, 4, 'EXTREME', 28, 'Java Basics TaskSet'),
        (5, 5, 'EASY', 34, 'Dev .Net Kyiv '),
        (6, 6, 'EASY', 50, 'JS intro'),
        (7, 6, 'EASY', 150, 'JavaScript PreScreening'),
        (8, 6, 'EASY', 150, 'TypeScript PreScreening'),
        (9, 5, 'EASY', 150, 'Net Winter Program 2021 FrontEnd'),
        (10, 4, 'EASY', 150, 'Pre selection '),
        (11, 4, 'EASY', 150, 'Pilot FE group');


create table question
(
    id        int unsigned auto_increment
        primary key,
    test_id   int unsigned     not null,
    text      text             null,
    is_active bit default 0 null,
    constraint question_ibfk_1
        foreign key (test_id) references test (id)
);
insert into epam2.question (id, test_id, text, is_active)
values  (6, 4, 'What is a correct syntax to output "Hello World" in Java?', true),
        (7, 4, 'Java is short for "JavaScript".', true),
        (8, 4, 'How do you insert COMMENTS in Java code?', true),
        (9, 4, 'Which data type is used to create a variable that should store text?', true),
        (19, 1, 'What is a correct syntax to output "Hello World" in Java?', true),
        (20, 1, 'Java is short for "JavaScript".', true),
        (21, 1, 'How do you insert COMMENTS in Java code?', true),
        (22, 1, 'Which data type is used to create a variable that should store text?', true),
        (26, 2, 'What is a correct syntax to output "Hello World" in Java?', true),
        (27, 2, 'Java is short for "JavaScript".', true),
        (28, 2, 'How do you insert COMMENTS in Java code?', true),
        (29, 2, 'Which data type is used to create a variable that should store text?', true),
        (33, 3, 'What is a correct syntax to output "Hello World" in Java?', true),
        (34, 3, 'Java is short for "JavaScript".', true),
        (35, 3, 'How do you insert COMMENTS in Java code?', true),
        (36, 3, 'Which data type is used to create a variable that should store text?', true),
        (40, 5, 'What is a correct syntax to output "Hello World" in Java?', true),
        (41, 5, 'Java is short for "JavaScript".', true),
        (42, 5, 'How do you insert COMMENTS in Java code?', true),
        (43, 5, 'Which data type is used to create a variable that should store text?', true),
        (47, 6, 'What is a correct syntax to output "Hello World" in Java?', true),
        (48, 6, 'Java is short for "JavaScript".', true),
        (49, 6, 'How do you insert COMMENTS in Java code?', true),
        (50, 6, 'Which data type is used to create a variable that should store text?', true),
        (54, 7, 'What is a correct syntax to output "Hello World" in Java?', true),
        (55, 7, 'Java is short for "JavaScript".', true),
        (56, 7, 'How do you insert COMMENTS in Java code?', true),
        (57, 7, 'Which data type is used to create a variable that should store text?', true),
        (61, 8, 'What is a correct syntax to output "Hello World" in Java?', true),
        (62, 8, 'Java is short for "JavaScript".', true),
        (63, 8, 'How do you insert COMMENTS in Java code?', true),
        (64, 8, 'Which data type is used to create a variable that should store text?', true),
        (68, 9, 'What is a correct syntax to output "Hello World" in Java?', true),
        (69, 9, 'Java is short for "JavaScript".', true),
        (70, 9, 'How do you insert COMMENTS in Java code?', true),
        (71, 9, 'Which data type is used to create a variable that should store text?', true),
        (75, 10, 'What is a correct syntax to output "Hello World" in Java?', true),
        (76, 10, 'Java is short for "JavaScript".', true),
        (77, 10, 'How do you insert COMMENTS in Java code?', true),
        (78, 10, 'Which data type is used to create a variable that should store text?', true),
        (82, 11, 'What is a correct syntax to output "Hello World" in Java?', true),
        (83, 11, 'Java is short for "JavaScript".', true),
        (84, 11, 'How do you insert COMMENTS in Java code?', true),
        (85, 11, 'Which data type is used to create a variable that should store text?', true),
        (89, 12, 'What is a correct syntax to output "Hello World" in Java?', true),
        (90, 12, 'Java is short for "JavaScript".', true),
        (91, 12, 'How do you insert COMMENTS in Java code?', true),
        (92, 12, 'Which data type is used to create a variable that should store text?', true);

create table answer
(
    id          int unsigned auto_increment
        primary key,
    question_id int unsigned     null,
    text        text             null,
    is_correct    bit default 0 null,
    constraint answer_ibfk_1
        foreign key (question_id) references question (id)
);

create index question_id
    on answer (question_id);
insert into epam2.answer (id, question_id, text, is_correct)
values  (15, 6, 'System.out.println("Hello world");', true),
        (16, 6, 'echo("Hello World");', false),
        (17, 6, 'Console.WriteLine("Hello World");', false),
        (18, 6, 'print ("Hello World");', false),
        (19, 7, 'True', false),
        (20, 7, 'False', true),
        (21, 8, '# This is a comment', false),
        (22, 8, '/* This is a comment', false),
        (23, 8, '// This is a comment', true),
        (24, 9, 'myString', false),
        (25, 9, 'String', true),
        (26, 9, 'txt', false),
        (30, 89, 'System.out.println("Hello world");', true),
        (31, 89, 'echo("Hello World");', false),
        (32, 89, 'Console.WriteLine("Hello World");', false),
        (33, 89, 'print ("Hello World");', false),
        (34, 82, 'System.out.println("Hello world");', true),
        (35, 82, 'echo("Hello World");', false),
        (36, 82, 'Console.WriteLine("Hello World");', false),
        (37, 82, 'print ("Hello World");', false),
        (38, 75, 'System.out.println("Hello world");', true),
        (39, 75, 'echo("Hello World");', false),
        (40, 75, 'Console.WriteLine("Hello World");', false),
        (41, 75, 'print ("Hello World");', false),
        (42, 68, 'System.out.println("Hello world");', true),
        (43, 68, 'echo("Hello World");', false),
        (44, 68, 'Console.WriteLine("Hello World");', false),
        (45, 68, 'print ("Hello World");', false),
        (46, 61, 'System.out.println("Hello world");', true),
        (47, 61, 'echo("Hello World");', false),
        (48, 61, 'Console.WriteLine("Hello World");', false),
        (49, 61, 'print ("Hello World");', false),
        (50, 54, 'System.out.println("Hello world");', true),
        (51, 54, 'echo("Hello World");', false),
        (52, 54, 'Console.WriteLine("Hello World");', false),
        (53, 54, 'print ("Hello World");', false),
        (54, 47, 'System.out.println("Hello world");', true),
        (55, 47, 'echo("Hello World");', false),
        (56, 47, 'Console.WriteLine("Hello World");', false),
        (57, 47, 'print ("Hello World");', false),
        (58, 40, 'System.out.println("Hello world");', true),
        (59, 40, 'echo("Hello World");', false),
        (60, 40, 'Console.WriteLine("Hello World");', false),
        (61, 40, 'print ("Hello World");', false),
        (62, 33, 'System.out.println("Hello world");', true),
        (63, 33, 'echo("Hello World");', false),
        (64, 33, 'Console.WriteLine("Hello World");', false),
        (65, 33, 'print ("Hello World");', false),
        (66, 26, 'System.out.println("Hello world");', true),
        (67, 26, 'echo("Hello World");', false),
        (68, 26, 'Console.WriteLine("Hello World");', false),
        (69, 26, 'print ("Hello World");', false),
        (70, 19, 'System.out.println("Hello world");', true),
        (71, 19, 'echo("Hello World");', false),
        (72, 19, 'Console.WriteLine("Hello World");', false),
        (73, 19, 'print ("Hello World");', false),
        (74, 90, 'True', false),
        (75, 90, 'False', true),
        (76, 83, 'True', false),
        (77, 83, 'False', true),
        (78, 76, 'True', false),
        (79, 76, 'False', true),
        (80, 69, 'True', false),
        (81, 69, 'False', true),
        (82, 62, 'True', false),
        (83, 62, 'False', true),
        (84, 55, 'True', false),
        (85, 55, 'False', true),
        (86, 48, 'True', false),
        (87, 48, 'False', true),
        (88, 41, 'True', false),
        (89, 41, 'False', true),
        (90, 34, 'True', false),
        (91, 34, 'False', true),
        (92, 27, 'True', false),
        (93, 27, 'False', true),
        (94, 20, 'True', false),
        (95, 20, 'False', true),
        (96, 91, '# This is a comment', false),
        (97, 91, '/* This is a comment', false),
        (98, 91, '// This is a comment', true),
        (99, 84, '# This is a comment', false),
        (100, 84, '/* This is a comment', false),
        (101, 84, '// This is a comment', true),
        (102, 77, '# This is a comment', false),
        (103, 77, '/* This is a comment', false),
        (104, 77, '// This is a comment', true),
        (105, 70, '# This is a comment', false),
        (106, 70, '/* This is a comment', false),
        (107, 70, '// This is a comment', true),
        (108, 63, '# This is a comment', false),
        (109, 63, '/* This is a comment', false),
        (110, 63, '// This is a comment', true),
        (111, 56, '# This is a comment', false),
        (112, 56, '/* This is a comment', false),
        (113, 56, '// This is a comment', true),
        (114, 49, '# This is a comment', false),
        (115, 49, '/* This is a comment', false),
        (116, 49, '// This is a comment', true),
        (117, 42, '# This is a comment', false),
        (118, 42, '/* This is a comment', false),
        (119, 42, '// This is a comment', true),
        (120, 35, '# This is a comment', false),
        (121, 35, '/* This is a comment', false),
        (122, 35, '// This is a comment', true),
        (123, 28, '# This is a comment', false),
        (124, 28, '/* This is a comment', false),
        (125, 28, '// This is a comment', true),
        (126, 21, '# This is a comment', false),
        (127, 21, '/* This is a comment', false),
        (128, 21, '// This is a comment', true),
        (129, 92, 'myString', false),
        (130, 92, 'String', true),
        (131, 92, 'txt', false),
        (132, 85, 'myString', false),
        (133, 85, 'String', true),
        (134, 85, 'txt', false),
        (135, 78, 'myString', false),
        (136, 78, 'String', true),
        (137, 78, 'txt', false),
        (138, 71, 'myString', false),
        (139, 71, 'String', true),
        (140, 71, 'txt', false),
        (141, 64, 'myString', false),
        (142, 64, 'String', true),
        (143, 64, 'txt', false),
        (144, 57, 'myString', false),
        (145, 57, 'String', true),
        (146, 57, 'txt', false),
        (147, 50, 'myString', false),
        (148, 50, 'String', true),
        (149, 50, 'txt', false),
        (150, 43, 'myString', false),
        (151, 43, 'String', true),
        (152, 43, 'txt', false),
        (153, 36, 'myString', false),
        (154, 36, 'String', true),
        (155, 36, 'txt', false),
        (156, 29, 'myString', false),
        (157, 29, 'String', true),
        (158, 29, 'txt', false),
        (159, 22, 'myString', false),
        (160, 22, 'String', true),
        (161, 22, 'txt', false);



create table user_test
(
    id          int unsigned auto_increment
        primary key,
    test_id     int unsigned             not null,
    started_at  datetime default (now()) null,
    finished_at datetime                 null,
    user_id     int unsigned             not null,
    score       double   default 0       not null,
    constraint user_test_ibfk_1
        foreign key (test_id) references test (id),
    constraint user_test_user_id_fk
        foreign key (user_id) references user (id)
);
insert into user_test (id, test_id, started_at, finished_at, user_id, score)
values  (83, 4, '2021-07-28 10:17:50', '2021-07-28 10:21:41', 1, 75),
        (84, 4, '2021-07-28 10:21:19', '2021-07-28 10:26:12', 1, 0),
        (86, 4, '2021-07-28 10:26:46', '2021-07-28 10:27:03', 1, 75),
        (87, 4, '2021-07-28 10:28:21', '2021-07-28 10:28:40', 1, 25),
        (88, 4, '2021-07-28 10:29:12', '2021-07-28 10:29:17', 1, 0),
        (89, 4, '2021-07-28 10:43:07', '2021-07-28 10:43:18', 1, 100),
        (90, 1, '2021-07-28 10:43:26', '2021-07-28 10:43:29', 1, 0),
        (91, 4, '2021-07-28 10:43:51', '2021-07-28 10:44:01', 1, 50),
        (92, 4, '2021-07-28 10:45:16', '2021-07-28 12:40:57', 1, 0),
        (93, 4, '2021-07-28 12:40:57', '2021-07-28 12:41:13', 1, 0),
        (94, 1, '2021-07-28 12:41:13', '2021-07-28 12:41:30', 1, 0),
        (95, 1, '2021-07-28 12:50:01', '2021-07-28 12:50:08', 1, 33.33333333333333),
        (96, 4, '2021-07-28 14:14:29', '2021-07-28 16:12:56', 1, 0),
        (97, 4, '2021-07-28 16:12:56', null, 1, 0);
