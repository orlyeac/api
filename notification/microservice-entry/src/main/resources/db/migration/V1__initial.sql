CREATE SEQUENCE notification_id_seq;

CREATE TABLE notification(
    id BIGINT DEFAULT nextval('notification_id_seq') PRIMARY KEY,
    to_id BIGINT NOT NULL,
    to_email TEXT NOT NULL,
    to_name TEXT NOT NULL,
    kind TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
