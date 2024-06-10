CREATE SEQUENCE review_id_seq;

CREATE TABLE review(
    id BIGINT DEFAULT nextval('review_id_seq') PRIMARY KEY,
    opinion TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    author_name TEXT NOT NULL,
    author_labour_link TEXT NOT NULL,
    author_company TEXT,
    publishable BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);