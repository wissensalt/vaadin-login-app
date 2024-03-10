CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BYTEA NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

CREATE TABLE account
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_account PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

CREATE TABLE role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE privilege
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name   VARCHAR(255),
    CONSTRAINT pk_privilege PRIMARY KEY (id)
);

ALTER TABLE privilege
    ADD CONSTRAINT uc_privilege_name UNIQUE (name);

CREATE TABLE link_role_privilege
(
    privilege_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL
);

ALTER TABLE link_role_privilege
    ADD CONSTRAINT fk_linrolpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (id);

ALTER TABLE link_role_privilege
    ADD CONSTRAINT fk_linrolpri_on_role FOREIGN KEY (role_id) REFERENCES role (id);

CREATE TABLE link_account_role
(
    account_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL
);

ALTER TABLE link_account_role
    ADD CONSTRAINT fk_linaccrol_on_account FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE link_account_role
    ADD CONSTRAINT fk_linaccrol_on_role FOREIGN KEY (role_id) REFERENCES role (id);