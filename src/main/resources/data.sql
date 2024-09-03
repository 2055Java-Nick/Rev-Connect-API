drop table if exists users cascade;
drop table if exists user_roles;
drop table if exists profiles;
drop table if exists posts cascade;
drop table if exists tags cascade;
drop table if exists post_tags cascade;
drop table if exists tagged_users cascade;


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


-- DROP TABLE IF EXISTS endorsement_links;
-- CREATE TABLE endorsement_links (
--     id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     user_id BIGINT NOT NULL,
--     link VARCHAR(255) NOT NULL,
--     link_text VARCHAR(255),

--     FOREIGN KEY (user_id) REFERENCES businessprofile(user_id)
-- );

-- INSERT INTO endorsement_links VALUES (1, 111, 'Https://www.blahblablah.com', 'test_link');

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

