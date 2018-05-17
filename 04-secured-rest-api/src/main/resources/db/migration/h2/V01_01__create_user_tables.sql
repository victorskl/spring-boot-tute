CREATE TABLE users(
    id serial NOT NULL,
    username varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(128) NOT NULL,
    enabled bool NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_uq UNIQUE (username),
    CONSTRAINT email_uq UNIQUE (email)
);

CREATE TABLE roles(
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT roles_pk PRIMARY KEY (id),
    CONSTRAINT roles_uq UNIQUE (name)
);

CREATE TABLE roles_users(
    role_id integer,
    user_id integer
);

-- PostgreSQL
-- ALTER TABLE roles_users ADD CONSTRAINT users_fk FOREIGN KEY (user_id)
--   REFERENCES users (id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE roles_users ADD CONSTRAINT roles_fk FOREIGN KEY (role_id)
--   REFERENCES roles (id) MATCH FULL ON DELETE CASCADE ON UPDATE CASCADE;

-- H2
ALTER TABLE roles_users ADD FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE roles_users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

-- Populate some data

-- password is bcrypted, 'tommy' in plain text
INSERT INTO users(username, email, password, enabled) VALUES ('admin', 'admin@robot.me', '$2a$10$spGU0VO7NbzA8Uj/BzapceaTc4vIUFM8iiNyj1NGgQLQqwgZqKPuG', true);
INSERT INTO users(username, email, password, enabled) VALUES ('jesse', 'jesse@robot.me', '$2a$10$spGU0VO7NbzA8Uj/BzapceaTc4vIUFM8iiNyj1NGgQLQqwgZqKPuG', true);
INSERT INTO users(username, email, password, enabled) VALUES ('jane', 'jane@robot.me', '$2a$10$spGU0VO7NbzA8Uj/BzapceaTc4vIUFM8iiNyj1NGgQLQqwgZqKPuG', true);

INSERT INTO roles(name) VALUES ('ADMIN');
INSERT INTO roles(name) VALUES ('USER');

INSERT INTO roles_users(role_id, user_id) VALUES
((SELECT id FROM roles WHERE name='ADMIN'), (SELECT id FROM users WHERE username='admin'));
INSERT INTO roles_users(role_id, user_id) VALUES
((SELECT id FROM roles WHERE name='USER'), (SELECT id FROM users WHERE username='admin'));
INSERT INTO roles_users(role_id, user_id) VALUES
((SELECT id FROM roles WHERE name='USER'), (SELECT id FROM users WHERE username='jesse'));
INSERT INTO roles_users(role_id, user_id) VALUES
((SELECT id FROM roles WHERE name='USER'), (SELECT id FROM users WHERE username='jane'));