INSERT INTO "user" (username, first_name, last_name, email, user_role_id, "password")
VALUES ('igunia', 'Iga', 'Iga', 'iga@gmail.com', (SELECT id FROM user_role WHERE name = 'admin'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('adi', 'Adam', 'Adam', 'adam@gmail.com', (SELECT id FROM user_role WHERE name = 'admin'),
        '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user1@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user2@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user3@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user4@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user5@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user6@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user7@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user8@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user9@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user10@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user11@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user12@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user13@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user14@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user15@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user16@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user17@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user18@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user19@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user20@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user21@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user22@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user23@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user24@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user25@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user26@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user27@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user28@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user29@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user30@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user31@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user32@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user33@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user34@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user35@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user36@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user37@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user38@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user39@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user40@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user41@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user42@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user43@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user44@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user45@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user46@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user47@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user48@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user49@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user50@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user51@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user52@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user53@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user54@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user55@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user56@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user57@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user58@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user59@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user60@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user61@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user62@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user63@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user64@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user65@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user66@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user67@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user68@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user69@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user70@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user71@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user72@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user73@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user74@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user75@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user76@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user77@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user78@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user79@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user80@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user81@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user82@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user83@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user84@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user85@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user86@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user87@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user88@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user89@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user90@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user91@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user92@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user93@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user94@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user95@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user96@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user97@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user98@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user99@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),
       ('user1', 'user1', 'user1', 'user100@gmail.com', (SELECT id FROM user_role WHERE name = 'user'), '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6'),

INSERT INTO event ("name", start_date, end_date, owner_id)
VALUES ('Ziflow Meeting', '2024-03-12', '2024-06-13', (SELECT id FROM "user" WHERE email = 'admin@gmail.com'));


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
       ('activity 1', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 2', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 3', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 4', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 5', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 6', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 7', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 8', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 9', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 10', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 11', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 12', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 13', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 14', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 15', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 16', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 17', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 18', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 19', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting')),
       ('activity 20', 15, 30, (SELECT id FROM "event" WHERE name = 'Ziflow Meeting'));


INSERT INTO challenge_tag(challenge_id, tag_id)
VALUES ((SELECT id FROM challenge WHERE name = 'joga'), (SELECT id FROM tag WHERE name = 'sport')),
       ((SELECT id FROM challenge WHERE name = 'joga'), (SELECT id FROM tag WHERE name = 'openair'));


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
