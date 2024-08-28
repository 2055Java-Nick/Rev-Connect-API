-- Dropping tables if they exist in the correct order
DROP TABLE IF EXISTS endorsement_links;
DROP TABLE IF EXISTS partner_channels;
DROP TABLE IF EXISTS business_profile;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS personal_profiles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts cascade;

-- Creating the 'users' table
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

-- Creating the 'user_roles' table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Creating the 'business_profile' table
CREATE TABLE business_profile (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL,
    bio_text VARCHAR(500),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Creating the 'personal_profiles' table
CREATE TABLE personal_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    bio VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Creating the 'posts' table
CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Creating the 'endorsement_links' table
CREATE TABLE endorsement_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    link VARCHAR(255) NOT NULL,
    link_text VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES business_profile(user_id)
);

-- Creating the 'partner_channels' table
CREATE TABLE partner_channels (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES business_profile(user_id)
);

-- Inserting sample data into 'users'
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
VALUES
('testuser1', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'test', 'tester', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assigning roles to users
INSERT INTO user_roles (user_id, role)
VALUES
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

-- Inserting sample data into 'business_profile'
INSERT INTO business_profile (profile_id, user_id, bio_text)
VALUES
(999, 1, 'Test Bio 1'),
(998, 2, 'Test Bio 2'),
(997, 3, 'Test Bio 3'),
(996, 4, 'Test Bio 4');

-- Inserting sample data into 'personal_profiles'
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


ALTER SEQUENCE user_sequence RESTART WITH 5;
ALTER SEQUENCE post_sequence RESTART WITH 5;

-- Inserting sample data into 'endorsement_links'
INSERT INTO endorsement_links (user_id, link, link_text)
VALUES
(1, 'Https://www.blahblablah.com', 'test_link');

-- Inserting sample data into 'partner_channels'
INSERT INTO partner_channels (user_id, name, url)
VALUES 
(1, 'Partner Channel 1', 'https://www.partnerchannel1.com'),
(2, 'Partner Channel 2', 'https://www.partnerchannel2.com'),
(3, 'Partner Channel 3', 'https://www.partnerchannel3.com');
