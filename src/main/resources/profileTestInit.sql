-- drop table if exists personal_profiles;
-- drop table if exists users cascade;
-- drop table if exists user_roles;


-- CREATE TABLE users (
--     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     username VARCHAR(50) NOT NULL UNIQUE,
--     user_password VARCHAR(255) NOT NULL,
--     email VARCHAR(100) NOT NULL UNIQUE,
--     first_name VARCHAR(50) NOT NULL,
--     last_name VARCHAR(50) NOT NULL,
--     is_business BOOLEAN NOT NULL DEFAULT FALSE,
--     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );


-- CREATE TABLE personal_profiles (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     user_id BIGINT UNIQUE,
--     bio VARCHAR(255),
--     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
-- );

-- -- Insert initialUser 
-- INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
-- VALUES
-- ('user', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', '', 'Test', 'User', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -- Insert initialProfile

-- INSERT INTO personal_profiles (user_id, bio)
-- VALUES
-- (1, 'Initial bio');



-- CREATE TABLE user_roles (
--     user_id BIGINT NOT NULL,
--     role VARCHAR(50) NOT NULL,
--     PRIMARY KEY (user_id, role),
--     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
-- );

-- CREATE TABLE personal_profiles (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     user_id BIGINT UNIQUE,
--     bio VARCHAR(255),
--     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
-- );

-- -- Insert initialUser 
-- INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
-- VALUES
-- ('user', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', '', 'Test', 'User', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- ('testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- ('testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'test', 'tester', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- ('testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT INTO user_roles (user_id, role)
-- VALUES
-- (1, 'ROLE_USER'),
-- (1, 'ROLE_ADMIN');

-- -- Insert initialProfile
-- INSERT INTO personal_profiles (user_id, bio)
-- VALUES
-- (1, 'Initial bio');

