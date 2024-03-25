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

INSERT INTO institutions(id, "name", institution_type, created_at) VALUES('cd7249e7c79d400280e674d51b8dd675', 'Instituição 1', 'CENTRAL', now());
INSERT INTO institutions(id, "name", institution_type, created_at) VALUES('04a860b59aaa4b0ebc6701dc4a7d0cec', 'Instituição 2', 'SINGULAR', now());
INSERT INTO institutions(id, "name", institution_type, created_at) VALUES('4a75b96cb927435c8ddf1b4319df7510', 'Instituição 3', 'COOPERATIVE', now());

INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('f8595a03fd874de5ba4b3fe5e5f2f6', 'Contratação 1', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, 'cd7249e7c79d400280e674d51b8dd675');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('8c9937083964496087321d44f2e7043', 'Contratação 1', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, 'cd7249e7c79d400280e674d51b8dd675');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('23e174685af94634904cac83d330553', 'Contratação 1', true, '2024-03-21 12:28:46.000', '2024-03-30 12:28:46.000', now(), now(), NULL, 'cd7249e7c79d400280e674d51b8dd675');

INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('f8595a03fd874de5ba4b3fe5e5f2f6f9', 'Contratação 2', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, '04a860b59aaa4b0ebc6701dc4a7d0cec');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('8c9937083964496087321d44f2e70464', 'Contratação 2', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, '04a860b59aaa4b0ebc6701dc4a7d0cec');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('23e174685af94634904cac83d3305454', 'Contratação 2', true, '2024-03-21 12:28:46.000', '2024-03-30 12:28:46.000', now(), now(), NULL, '04a860b59aaa4b0ebc6701dc4a7d0cec');

INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('f8595a03fd874de5ba4b3fe5e5f2f6f7', 'Contratação 3', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, '4a75b96cb927435c8ddf1b4319df7510');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('8c9937083964496087321d44f2e70465', 'Contratação 3', false, '2024-03-21 12:28:46.000', '2024-03-23 12:28:46.000', now(), now(), NULL, '4a75b96cb927435c8ddf1b4319df7510');
INSERT INTO events(id, "name", active, start_at, finish_at, created_at, updated_at, deleted_at, institution_id) VALUES('23e174685af94634904cac83d3305455', 'Contratação 3', true, '2024-03-21 12:28:46.000', '2024-03-30 12:28:46.000', now(), now(), NULL, '4a75b96cb927435c8ddf1b4319df7510');