ALTER TABLE code
ADD CONSTRAINT unique_event_code
UNIQUE (event_id, code)