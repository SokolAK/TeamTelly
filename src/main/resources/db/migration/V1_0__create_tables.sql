CREATE TABLE IF NOT EXISTS user_role
(
    id     UUID PRIMARY KEY,
    "name" VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS "user"
(
    id           UUID PRIMARY KEY,
    username     VARCHAR(127),
    first_name   VARCHAR(63),
    last_name    VARCHAR(63),
    email        VARCHAR(255) UNIQUE,
    "password"   VARCHAR(255),
    user_role_id UUID references user_role (id)
);

CREATE TABLE IF NOT EXISTS event
(
    id         UUID PRIMARY KEY,
    "name"     VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    owner_id   UUID references "user" (id)
);

CREATE TABLE IF NOT EXISTS tag
(
    id     UUID PRIMARY KEY,
    "name" VARCHAR(63)
);

CREATE TABLE IF NOT EXISTS challenge
(
    id                    UUID PRIMARY KEY,
    "name"                VARCHAR(255),
    personal_points       INTEGER,
    team_points           INTEGER,
    event_id              UUID references event (id)
);

CREATE TABLE IF NOT EXISTS challenge_tag
(
    id           UUID PRIMARY KEY,
    challenge_id UUID references challenge (id),
    tag_id       UUID references tag (id)
);

CREATE TABLE IF NOT EXISTS team
(
    id     UUID PRIMARY KEY,
    "name" VARCHAR(255) UNIQUE,
    color  VARCHAR(6) UNIQUE,
    icon   VARCHAR(4) UNIQUE
);

CREATE TABLE IF NOT EXISTS participant
(
    id       UUID PRIMARY KEY,
    event_id UUID references event (id),
    user_id  UUID references "user" (id)
);
