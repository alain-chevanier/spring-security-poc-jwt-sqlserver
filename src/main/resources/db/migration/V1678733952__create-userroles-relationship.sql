CREATE TABLE user_roles
(
    role_id int    NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
)
    GO

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id)
    GO

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id)
    GO