INSERT INTO users (id, user_name, email, password, registered, last_activity, enabled, roles)
VALUES (1,'user1','test@gmail.com', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',
        '2020-01-31 21:00:00', '2021-01-31 21:00:00', true, 'ROLE_USER');

INSERT INTO messages (message_id, recipient_username, sender_username, topic, text, sent_time, is_read)
VALUES (1,'user2','user1', 'topic',
        'text', '2021-01-31 21:00:00', false);