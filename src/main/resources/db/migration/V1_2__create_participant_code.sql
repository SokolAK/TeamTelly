CREATE TABLE IF NOT EXISTS participant_code
(
    id             SERIAL PRIMARY KEY,
    participant_id INT references participant (id) ON DELETE CASCADE,
    code_id        INT references code (id) ON DELETE CASCADE
);
