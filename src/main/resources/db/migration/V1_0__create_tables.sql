CREATE TABLE IF NOT EXISTS activity
(
    id              UUID PRIMARY KEY,
    "name"          VARCHAR(255),
    personal_points INTEGER,
    team_points     INTEGER
);

CREATE TABLE IF NOT EXISTS team
(
    id     UUID PRIMARY KEY,
    "name" VARCHAR(255) UNIQUE,
    color  VARCHAR(6) UNIQUE,
    icon   VARCHAR(4) UNIQUE
);

CREATE TABLE IF NOT EXISTS "user"
(
    id           UUID PRIMARY KEY,
    username     VARCHAR(127),
    first_name   VARCHAR(63),
    last_name    VARCHAR(63),
    email        VARCHAR(255) UNIQUE,
    "password"   VARCHAR(255),
    user_role_id UUID
);

CREATE TABLE IF NOT EXISTS user_role
(
    id     UUID PRIMARY KEY,
    "name" VARCHAR(15)
);
