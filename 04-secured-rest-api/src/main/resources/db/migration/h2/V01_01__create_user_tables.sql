CREATE TABLE user_account(
    id serial NOT NULL,
    username varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(128) NOT NULL,
    enabled bool NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_uq UNIQUE (username),
    CONSTRAINT email_uq UNIQUE (email)
);

CREATE TABLE user_role(
    id serial NOT NULL,
    role varchar(50) NOT NULL,
    user_account integer,
    CONSTRAINT user_role_pk PRIMARY KEY (id)
);

-- ALTER TABLE user_role ADD CONSTRAINT user_fk FOREIGN KEY (user_account)
-- REFERENCES user_account (id) MATCH FULL
-- ON DELETE SET NULL ON UPDATE CASCADE;

-- password is bcrypted, 'tommy' in plain text
INSERT INTO user_account(username, email, password, enabled) VALUES ('admin', 'admin@robot.me', '$2a$10$spGU0VO7NbzA8Uj/BzapceaTc4vIUFM8iiNyj1NGgQLQqwgZqKPuG', true);
INSERT INTO user_role(role, user_account) VALUES ('ADMIN', (SELECT id FROM user_account WHERE username='admin'));
INSERT INTO user_role(role, user_account) VALUES ('USER', (SELECT id FROM user_account WHERE username='admin'));
