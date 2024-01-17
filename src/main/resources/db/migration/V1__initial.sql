CREATE SEQUENCE customer_id_seq;

CREATE TABLE customer(
    id BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    year_of_birth INT NOT NULL
);