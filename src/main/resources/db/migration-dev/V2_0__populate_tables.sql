INSERT INTO team (id, "name", color, icon)
VALUES (gen_random_uuid(), 'Squirrels', 'e69138', '🐿️'),
       (gen_random_uuid(), 'Boxers', 'c90076', '🥊'),
       (gen_random_uuid(), 'Beauties', '6aa84f', '🌷'),
       (gen_random_uuid(), 'Fishermen', '2986cc', '🐟');


INSERT INTO "user" (id, username, first_name, last_name, email, user_role_id, "password")
VALUES (gen_random_uuid(), 'adi', 'Adam', 'Adamowski', 'admin@gmail.com', (SELECT id FROM user_role WHERE name = 'admin'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       (gen_random_uuid(), 'ursza', 'Urszula', 'Urszulska', 'user@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       (gen_random_uuid(), 'barb', 'Barbara', 'Barbarska', 'barbara.barbarska@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
        (gen_random_uuid(), 'celin', 'Celina', 'Celińska', 'celina.celinska@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
         '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6');


INSERT INTO event (id, "name", start_date, end_date, owner_id)
VALUES (gen_random_uuid(), 'Ziflow Meeting', '2024-03-12', '2024-06-13', (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (gen_random_uuid(), 'BMS Meeting', '2024-03-12', '2024-06-13', (SELECT id FROM "user" WHERE email = 'admin@gmail.com'));


INSERT INTO tag (id, "name")
VALUES (gen_random_uuid(), 'sport'),
       (gen_random_uuid(), 'openair'),
       (gen_random_uuid(), 'bob');


INSERT INTO challenge (id, "name", personal_points, team_points, event_id)
VALUES (gen_random_uuid(), 'joga', 10, 0,           (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       (gen_random_uuid(), 'basketball', 15, 30,    (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       (gen_random_uuid(), 'add bob photo', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       (gen_random_uuid(), 'beer', 5, 5,            (SELECT id FROM "event" WHERE name = 'BMS Meeting')),
       (gen_random_uuid(), 'vodka', 40, 40,         (SELECT id FROM "event" WHERE name = 'BMS Meeting'));


INSERT INTO challenge_tag (id, challenge_id, tag_id)
VALUES (gen_random_uuid(), (SELECT id FROM challenge WHERE name = 'joga'), (SELECT id FROM tag WHERE name = 'sport')),
       (gen_random_uuid(), (SELECT id FROM challenge WHERE name = 'basketball'), (SELECT id FROM tag WHERE name = 'sport')),
       (gen_random_uuid(), (SELECT id FROM challenge WHERE name = 'basketball'), (SELECT id FROM tag WHERE name = 'openair')),
       (gen_random_uuid(), (SELECT id FROM challenge WHERE name = 'add bob photo'), (SELECT id FROM tag WHERE name = 'bob'));


INSERT INTO participant (id, event_id, user_id)
VALUES (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'celina.celinska@gmail.com'));
