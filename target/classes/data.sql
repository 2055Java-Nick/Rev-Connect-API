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



-- -- CREATE TABLE post (
-- --     post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
-- --     posted_by BIGINT,
-- --     post_text VARCHAR(255),
-- --     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
-- --     updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
-- --     FOREIGN KEY (posted_by) REFERENCES users(user_id)
-- -- );

INSERT INTO profiles VALUES (999, 'Test Bio 1', 'personal', null, null, null, null),
(998, 'Test Bio 2', 'personal', null, null, null, null),
(997, 'Test Bio 3', 'personal', null, null, null, null),
(996, 'Test Bio 4', 'business', 'light', 'pfpURL4.com', 'bannerURL4.com', 'Business4'),
(995, 'Test Bio 5', 'business', 'dark', 'pfpURL5.com', 'bannerURL5.com', 'Business5'),
(994, 'Test Bio 6', 'business', 'red', 'pfpURL6.com', 'bannerURL5.com', 'Business6');

-- Insert users
-- passwords are hashed from "hashed_password"
INSERT INTO users (user_id, username, user_password, email, first_name, last_name, is_business, created_at, updated_at, profile_id)
VALUES
(111, 'testuser1', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 998),
(112, 'testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 997),
(113, 'testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'test', 'tester', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 996),
(114, 'testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'test', 'tester', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 995);

-- Assign roles to users
INSERT INTO user_roles (user_id, role)
VALUES
(111, 'ROLE_USER'),
(111, 'ROLE_ADMIN'),
(112, 'ROLE_USER'),
(113, 'ROLE_USER'),
(114, 'ROLE_USER');

-- INSERT INTO personal_profiles (user_id, bio)
-- VALUES
-- (1, 'TestBio1!'),
-- (2, 'TestBio2!'),
-- (3, 'TestBio3!'),
-- (4, 'TestBio4!');

-- -- Insert posts
-- INSERT INTO post (posted_by, post_text, created_at, updated_at)
-- VALUES
-- (1, 'This is the first test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (1, 'This is the second test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (2, 'Another post for testing.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (1, 'Yet another test post.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- DROP TABLE IF EXISTS businessprofile;
-- DROP TABLE IF EXISTS users;

-- CREATE TABLE businessprofile (
--     profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
--     bio_text VARCHAR(500),
--     theme VARCHAR(255),
--     pfp_URL VARCHAR(255),
--     banner_URL VARCHAR(255)
-- );

-- INSERT INTO users VALUES (111, 'test1', 'password1', 'joe1', 'doe1', 'test1@email', true);
-- INSERT INTO users VALUES (112, 'test2', 'password2', 'joe2', 'doe2', 'test2@email', true);
-- INSERT INTO users VALUES (113, 'test3', 'password3', 'joe3', 'doe3', 'test3@email', true);
-- INSERT INTO users VALUES (114, 'test4', 'password4', 'joe4', 'doe4', 'test4@email', false);
-- INSERT INTO users VALUES (115, 'test5', 'password5', 'joe5', 'doe5', 'test5@email', false);
-- INSERT INTO users VALUES (116, 'test6', 'password6', 'joe6', 'doe6', 'test6@email', false);

-- INSERT INTO businessprofile VALUES (999, 111, 'Test Bio 1', null, null, null);
-- INSERT INTO businessprofile VALUES (998, 112, 'Test Bio 2', null, null, null);
-- INSERT INTO businessprofile VALUES (997, 113, 'Test Bio 3', null, null, null);
-- INSERT INTO businessprofile VALUES (996, 114, 'Test Bio 4', null, null, null);
-- INSERT INTO businessprofile VALUES (995, 115, 'Test Bio 5', null, null, null);
-- INSERT INTO businessprofile VALUES (994, 116, 'Test Bio 6', null, null, null);

-- DROP TABLE IF EXISTS endorsement_links;
-- CREATE TABLE endorsement_links (
--     id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     user_id BIGINT NOT NULL,
--     link VARCHAR(255) NOT NULL,
--     link_text VARCHAR(255),

--     FOREIGN KEY (user_id) REFERENCES businessprofile(user_id)
-- );

-- INSERT INTO endorsement_links VALUES (1, 111, 'Https://www.blahblablah.com', 'test_link');
