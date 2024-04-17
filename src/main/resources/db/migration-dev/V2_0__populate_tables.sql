INSERT INTO "user" (username, first_name, last_name, email, user_role_id, "password")
VALUES ('adi', 'Adam', 'Adamowski', 'admin@gmail.com', (SELECT id FROM user_role WHERE name = 'admin'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('ursza', 'Urszula', 'Urszulska', 'user@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('barb', 'Barbara', 'Barbarska', 'barbara.barbarska@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
        ('celin', 'Celina', 'Celińska', 'celina.celinska@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
         '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6');


INSERT INTO event ("name", start_date, end_date, owner_id)
VALUES ('Ziflow Meeting', '2024-03-12', '2024-06-13', (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       ('BMS Meeting', '2024-03-12', '2024-06-13', (SELECT id FROM "user" WHERE email = 'admin@gmail.com'));


INSERT INTO team ("name", color, icon, event_id)
VALUES ('Squirrels', '#e69138', '🐿️', (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('Boxers', '#c90076', '🥊', (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('Beauties', '#6aa84f', '🌷', (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('Fishermen', '#2986cc', '🐟', (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'));


INSERT INTO tag ("name")
VALUES ('sport'),
       ('openair'),
       ('bob');


INSERT INTO challenge ("name", individual_points, team_points, event_id)
VALUES ('joga', 10, 0,           (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('basketball', 15, 30,    (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('add bob photo', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('beer', 5, 5,            (SELECT id FROM "event" WHERE name = 'BMS Meeting')),
       ('vodka', 40, 40,         (SELECT id FROM "event" WHERE name = 'BMS Meeting'));


INSERT INTO challenge_tag (challenge_id, tag_id)
VALUES ((SELECT id FROM challenge WHERE name = 'joga'), (SELECT id FROM tag WHERE name = 'sport')),
       ((SELECT id FROM challenge WHERE name = 'basketball'), (SELECT id FROM tag WHERE name = 'sport')),
       ((SELECT id FROM challenge WHERE name = 'basketball'), (SELECT id FROM tag WHERE name = 'openair')),
       ((SELECT id FROM challenge WHERE name = 'add bob photo'), (SELECT id FROM tag WHERE name = 'bob'));


INSERT INTO participant (active, event_id, user_id)
VALUES (true, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (true, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (true, (SELECT id FROM "event" WHERE name = 'BMS Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (false, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'celina.celinska@gmail.com'));


INSERT INTO code (active, code, event_id, challenge_id)
VALUES (true, '111', 1, (SELECT id FROM challenge WHERE name = 'joga')),
       (true, '1111', 1, (SELECT id FROM challenge WHERE name = 'joga')),
       (true, '11111', 1, (SELECT id FROM challenge WHERE name = 'joga')),
       (true, '222', 1, (SELECT id FROM challenge WHERE name = 'basketball')),
       (true, '2222', 1, (SELECT id FROM challenge WHERE name = 'basketball')),
       (true, '22222', 1, (SELECT id FROM challenge WHERE name = 'basketball')),
       (true, '333', 1, (SELECT id FROM challenge WHERE name = 'add bob photo')),
       (true, '3333', 1, (SELECT id FROM challenge WHERE name = 'add bob photo')),
       (true, '33333', 1, (SELECT id FROM challenge WHERE name = 'add bob photo'))
