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


INSERT INTO challenge (id, "event_id", "name", personal_points, team_points)
VALUES (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), 'joga', 10, 0),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), 'basketball', 15, 30),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), 'beer', 5, 5),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), 'vodka', 40, 40);


INSERT INTO participant (id, event_id, user_id)
VALUES (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), (SELECT id FROM "user" WHERE email = 'admin@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'BMS Meeting'), (SELECT id FROM "user" WHERE email = 'barbara.barbarska@gmail.com')),
       (gen_random_uuid(), (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'), (SELECT id FROM "user" WHERE email = 'celina.celinska@gmail.com'));
