DROP TABLE IF EXISTS statistics;

CREATE TABLE IF NOT EXISTS statistics
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(300) NOT NULL,
    uri VARCHAR(300) NOT NULL,
    ip VARCHAR(300) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL
);