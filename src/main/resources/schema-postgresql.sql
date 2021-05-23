DROP TABLE IF EXISTS userr;
CREATE SEQUENCE IF NOT EXISTS USER_SEQ START 4;

CREATE TABLE IF NOT EXISTS userr
(
    id bigint PRIMARY KEY,
    document NUMERIC NOT NULL,
    name VARCHAR(255),
    username VARCHAR(255),
    active boolean NOT NULL,
    UNIQUE(document)
);