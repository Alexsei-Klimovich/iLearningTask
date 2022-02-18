DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START WITH 1;

CREATE TABLE users
(
    id               SERIAL                        PRIMARY KEY,
    user_name        VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    last_activity    TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    roles            VARCHAR                           NOT NULL
);

DROP TABLE IF EXISTS messages;
CREATE TABLE messages
(
    message_id                serial         DEFAULT nextval('hibernate_sequence')         PRIMARY KEY ,
    recipient_username        VARCHAR                           NOT NULL,
    sender_username           VARCHAR                           NOT NULL,
    topic                     VARCHAR                           NOT NULL,
    text                      VARCHAR                           NOT NULL,
    sent_time                 TIMESTAMP           DEFAULT now() NOT NULL,
    is_read                   BOOL                DEFAULT FALSE  NOT NULL
);


