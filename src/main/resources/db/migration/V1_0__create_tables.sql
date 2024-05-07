CREATE TABLE IF NOT EXISTS user_role
(
    id         SERIAL PRIMARY KEY,
    "name"     VARCHAR(15),
    is_default BOOL
);

CREATE TABLE IF NOT EXISTS "user"
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(127),
    first_name   VARCHAR(63),
    last_name    VARCHAR(63),
    job_title    VARCHAR(63),
    email        VARCHAR(255) UNIQUE,
    "password"   VARCHAR(255) DEFAULT '$2a$10$VaCLRT7rNO8LdWUpiw/rSue.MkW8EZS372zwFAwyCNzc9PcfKUPn6',
    user_role_id INT          DEFAULT 2 references user_role (id) ON DELETE CASCADE,
    photo        BYTEA,
    logged       BOOL DEFAULT false
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
    id                SERIAL PRIMARY KEY,
    "name"            VARCHAR(255),
    description       VARCHAR(255),
    individual_points INTEGER,
    team_points       INTEGER,
    event_id          INT references event (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participant
(
    id       SERIAL PRIMARY KEY,
    active   BOOL DEFAULT true,
    team_id  INT references team (id) ON DELETE SET NULL,
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
    active       BOOLEAN DEFAULT true,
    disposable   BOOLEAN DEFAULT true,
    event_id     INT references event (id) ON DELETE CASCADE,
    challenge_id INT references challenge (id) ON DELETE CASCADE
);
