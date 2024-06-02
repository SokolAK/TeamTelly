CREATE TABLE IF NOT EXISTS history
(
    id           SERIAL PRIMARY KEY,
    timestamp    TIMESTAMP,
    username     VARCHAR(127),
    operation    VARCHAR(31)
);
