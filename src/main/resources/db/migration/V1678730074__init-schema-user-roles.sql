CREATE TABLE roles
(
    id   int IDENTITY (1, 1) NOT NULL,
    name varchar(20),
    CONSTRAINT pk_roles PRIMARY KEY (id)
)
    GO



CREATE TABLE users
(
    id       bigint IDENTITY (1, 1) NOT NULL,
    username varchar(20),
    email    varchar(50),
    password varchar(120),
    CONSTRAINT pk_users PRIMARY KEY (id)
)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email)
    GO

ALTER TABLE users
    ADD CONSTRAINT uc_77584fbe74cc86922be2a3560 UNIQUE (username)
    GO
