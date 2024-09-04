drop table if exists users cascade;
drop table if exists user_roles;
drop table if exists profiles;

drop table if exists post;

CREATE TABLE profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bio VARCHAR(255),
    profile_type VARCHAR(255),
    theme VARCHAR(255),
    pfp_URL VARCHAR(255),
    banner_URL VARCHAR(255),
    display_name VARCHAR(255)
);

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_business BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    profile_id BIGINT REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO profiles 
VALUES 
(999, 'Initial bio', 'personal', null, null, null, null);

-- Insert users
-- passwords are hashed from "hashed_password"
INSERT INTO users (user_id, username, user_password, email, first_name, last_name, is_business, created_at, updated_at, profile_id)
VALUES
(111, 'user', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'Test', 'User', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 999);

-- Assign roles to users
INSERT INTO user_roles (user_id, role)
VALUES
(111, 'ROLE_USER');
