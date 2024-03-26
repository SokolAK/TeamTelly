CREATE TABLE IF NOT EXISTS user_role
(
    id     SERIAL PRIMARY KEY,
    "name" VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS "user"
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(127),
    first_name   VARCHAR(63),
    last_name    VARCHAR(63),
    email        VARCHAR(255) UNIQUE,
    "password"   VARCHAR(255),
    user_role_id INT references user_role (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event
(
    id         SERIAL PRIMARY KEY,
    "name"     VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    owner_id   INT references "user" (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tag
(
    id     SERIAL PRIMARY KEY,
    "name" VARCHAR(63)
);

CREATE TABLE IF NOT EXISTS team
(
    id       SERIAL PRIMARY KEY,
    "name"   VARCHAR(255),
    color    VARCHAR(7),
    icon     VARCHAR(4),
    event_id INT references event (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS challenge
(
    id              SERIAL PRIMARY KEY,
    "name"          VARCHAR(255),
    personal_points INTEGER,
    team_points     INTEGER,
    event_id        INT references event (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participant
(
    id       SERIAL PRIMARY KEY,
    active   BOOL,
    team_id  INT references team (id) ON DELETE CASCADE,
    event_id INT references event (id) ON DELETE CASCADE,
    user_id  INT references "user" (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS challenge_tag
(
    id           SERIAL PRIMARY KEY,
    challenge_id INT references challenge (id) ON DELETE CASCADE,
    tag_id       INT references tag (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participant_challenge
(
    id             SERIAL PRIMARY KEY,
    participant_id INT references participant (id) ON DELETE CASCADE,
    challenge_id   INT references challenge (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS code
(
    id           SERIAL PRIMARY KEY,
    code         VARCHAR(15),
    active       BOOLEAN,
    event_id     INT references event (id) ON DELETE CASCADE,
    challenge_id INT references challenge (id) ON DELETE CASCADE
);
