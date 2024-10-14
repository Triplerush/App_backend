-- V3__create_stratum_table.sql

CREATE TABLE strata
(
    id_stratum   SERIAL PRIMARY KEY,
    stratum_name VARCHAR(255) NOT NULL,
    description  TEXT,
    active       BOOLEAN DEFAULT TRUE
);
