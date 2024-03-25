create table if not exists institutions (
    id varchar(32) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    institution_type VARCHAR(15) NOT NULL,
    created_at timestamp NOT NULL
);

create table if not exists events (
    id varchar(32) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    start_at timestamp NOT NULL,
    finish_at timestamp NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    deleted_at timestamp NULL,
    institution_id varchar(32) NOT NULL
);

ALTER TABLE events ADD CONSTRAINT fk_event_institution_id FOREIGN KEY (institution_id) REFERENCES institutions(id);