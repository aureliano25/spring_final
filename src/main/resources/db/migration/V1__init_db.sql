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
(2, 'student', '$2a$10$2dqFVmrkWmu64YamIgz0Lub4RksCSd0AEBbKKF9YLHbk5XO1zcaUW', 'Yaroslav', 'Pavliuk', false);



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
    answers   json             not null,
    constraint question_ibfk_1
        foreign key (test_id) references test (id)
);
insert into question (id, test_id, text, is_active, answers)
values  (2, 1, 'Question1', true, '[{"text": "sdfsdfsdfsd", "isCorrect": true}, {"text": "option2", "isCorrect": true}, {"text": "Option3", "isCorrect": false}, {"text": "Option4", "isCorrect": false}]'),
        (3, 1, 'Text', true, '[{"text": "First question", "isCorrect": true}, {"text": "Second question", "isCorrect": false}, {"text": "text", "isCorrect": true}, {"text": "test", "isCorrect": false}]'),
        (6, 4, 'What is a correct syntax to output "Hello World" in Java?', false, '[{"text": " System.out.println(\\"Hello World\\");", "isCorrect": true}, {"text": " echo(\\"Hello World\\");", "isCorrect": false}, {"text": " Console.WriteLine(\\"Hello World\\");", "isCorrect": false}, {"text": " print (\\"Hello World\\");", "isCorrect": false}]'),
        (7, 4, 'Java is short for "JavaScript".', false, '[{"text": "True", "isCorrect": false}, {"text": "False", "isCorrect": true}]'),
        (8, 4, 'How do you insert COMMENTS in Java code?', false, '[{"text": " # This is a comment", "isCorrect": false}, {"text": " /* This is a comment", "isCorrect": false}, {"text": " // This is a comment", "isCorrect": true}]'),
        (9, 4, 'Which data type is used to create a variable that should store text?', false, '[{"text": "myString", "isCorrect": false}, {"text": "String", "isCorrect": true}, {"text": "string", "isCorrect": false}, {"text": "txt", "isCorrect": false}]'),
        (10, 1, '<script>alert("hello");</script>', true, '[{"text": "sdf", "isCorrect": true}, {"text": "sdf", "isCorrect": false}]');


create table answer
(
    id          int unsigned auto_increment
        primary key,
    question_id int unsigned     null,
    text        text             null,
    is_right    bit default 0 null,
    constraint answer_ibfk_1
        foreign key (question_id) references question (id)
);

create index question_id
    on answer (question_id);
insert into answer (id, question_id, text, is_right)
values  (1, 2, 'true', true),
        (2, 2, 'false', false),
        (3, 3, 'true', true),
        (4, 3, 'false', false);



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
