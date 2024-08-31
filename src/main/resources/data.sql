-- Drop existing tables if they exist
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS user_liked_posts;
DROP TABLE IF EXISTS user_followed_hashtags;
DROP TABLE IF EXISTS user_following;
DROP TABLE IF EXISTS personal_profiles;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS hashtags CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

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

CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE tags (
    tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

CREATE TABLE tagged_users (
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create user_liked_posts table
CREATE TABLE user_liked_posts (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE
);

-- Create user_followed_tags table
CREATE TABLE user_followed_hashtags (
    user_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, tag_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- Create user_following table
CREATE TABLE user_following (
    user_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, following_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users(user_id) ON DELETE CASCADE
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

-- Insert posts
INSERT INTO posts (author_id, title, content, created_at, updated_at)
VALUES
(1, 'testtitle1', 'This is the first test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle2', 'This is the second test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'testtitle3', 'Another post for testing.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'testtitle4', 'Yet another test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tags (tag_name)
VALUES
('Announcement'),
('Personal'),
('Private');


INSERT INTO post_tags (post_id, tag_id)
VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3);

INSERT INTO tagged_users (post_id, user_id)
VALUES
(1, 1),
(1, 2);

ALTER SEQUENCE user_sequence RESTART WITH 5;
ALTER SEQUENCE post_sequence RESTART WITH 5;
ALTER SEQUENCE tag_sequence RESTART WITH 4;