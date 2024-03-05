CREATE TABLE IF NOT EXISTS activity (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    personal_points SMALLINT,
    team_points SMALLINT
)