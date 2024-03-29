CREATE SEQUENCE customer_id_seq;

CREATE TABLE customer(
    id BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    labour_link TEXT NOT NULL,
    company TEXT,
    created_at TIMESTAMP NOT NULL
);