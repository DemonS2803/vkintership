
INSERT INTO users (id, login, password) VALUES (1, 'admin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
                                                (2, 'user', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');

INSERT INTO user_authority (user_id, authority) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_POSTS_VIEWER');