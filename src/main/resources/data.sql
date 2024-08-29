drop table if exists user_roles;
drop table if exists posts;
drop table if exists users cascade;
drop table if exists connections;
drop table if exists personal_profiles;

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

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE personal_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    bio VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE connections (
    connection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_A BIGINT,
    user_B BIGINT,
    FOREIGN KEY (user_A) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_B) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    is_private BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert users
-- passwords are hashed from "hashed_password"
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
VALUES
('testuser1', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'test', 'tester', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign roles to users
INSERT INTO user_roles (user_id, role)
VALUES
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

INSERT INTO personal_profiles (user_id, bio)
VALUES
(1, 'TestBio1!'),
(2, 'TestBio2!'),
(3, 'TestBio3!'),
(4, 'TestBio4!');

INSERT INTO connections (user_A, user_B)
VALUES
(1, 2),
(2, 1);

-- Insert posts
INSERT INTO posts (author_id, title, content, is_private, created_at, updated_at)
VALUES
(1, 'testtitle1', 'This is the first test post.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle2', 'This is the second test post.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'testtitle3', 'Another post for testing.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle4', 'Yet another test post.', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER SEQUENCE user_sequence RESTART WITH 5;
ALTER SEQUENCE post_sequence RESTART WITH 5;
