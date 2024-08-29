drop table if exists connections;
drop table if exists posts;
drop table if exists users cascade;

DROP SEQUENCE IF EXISTS post_sequence;
CREATE SEQUENCE post_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_business BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE connections (
    connection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_A BIGINT,
    user_B BIGINT,
    FOREIGN KEY (user_A) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_B) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE posts (
    post_id BIGINT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    is_private BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insert initial Users
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
VALUES
('A', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'A@gmail.com', 'TestUserA', 'A', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'B@gmail.com', 'TestUserB', 'B', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('C', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'C@gmail.com', 'TestUserC', 'C', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert connections
INSERT INTO connections (user_A, user_B)
VALUES
(1, 2),
(2, 1);

-- -- Insert posts
INSERT INTO posts (post_id, author_id, title, content, is_private, created_at, updated_at) VALUES 
(NEXT VALUE FOR post_sequence, 1, 'User A Private Post', 'Sample post content.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR post_sequence, 1, 'User A Public Post', 'Sample post content.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR post_sequence, 2, 'User B Private Post', 'Sample post content.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR post_sequence, 2, 'User B Public Post', 'Sample post content.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR post_sequence, 3, 'User C Private Post', 'Sample post content.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(NEXT VALUE FOR post_sequence, 3, 'User C Public Post', 'Sample post content.', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);