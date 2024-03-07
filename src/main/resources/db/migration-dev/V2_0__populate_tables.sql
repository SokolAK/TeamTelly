INSERT INTO activity (id, "name", personal_points, team_points)
VALUES (gen_random_uuid(), 'joga', 10, 0),
       (gen_random_uuid(), 'basketball', 15, 30);


INSERT INTO team (id, "name", color, icon)
VALUES (gen_random_uuid(), 'Squirrels', 'e69138', '🐿️'),
       (gen_random_uuid(), 'Boxers', 'c90076', '🥊'),
       (gen_random_uuid(), 'Beauties', '6aa84f', '🌷'),
       (gen_random_uuid(), 'Fishermen', '2986cc', '🐟');


INSERT INTO "user" (id, username, first_name, last_name, email, user_role_id, "password")
VALUES (gen_random_uuid(), 'adi', 'Adam', 'Adamowski', 'admin@gmail.com', (SELECT id FROM user_role WHERE name = 'admin'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       (gen_random_uuid(), 'ursza', 'Urszula', 'Urszulska', 'user@gmail.com', (SELECT id FROM user_role WHERE name = 'user'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6');