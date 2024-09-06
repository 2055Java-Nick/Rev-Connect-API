-- ===========================
-- Drop existing tables if they exist
-- ===========================
-- Drop child tables first to respect foreign key constraints
DROP TABLE IF EXISTS user_following;
DROP TABLE IF EXISTS user_liked_posts;
DROP TABLE IF EXISTS user_followed_tags;
DROP TABLE IF EXISTS tagged_users;
DROP TABLE IF EXISTS post_tags;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS personal_profiles;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;

-- ===========================
-- Create Tables
-- ===========================

-- Users Table
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

-- User Roles Table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Personal Profiles Table
CREATE TABLE personal_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    bio VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Tags Table
CREATE TABLE tags (
    tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

-- Posts Table
CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Post Tags Table
CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- Tagged Users Table
CREATE TABLE tagged_users (
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- User Liked Posts Table
CREATE TABLE user_liked_posts (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE
);

-- User Followed Tags Table
CREATE TABLE user_followed_tags (
    user_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, tag_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- User Following Table
CREATE TABLE user_following (
    user_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, following_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ===========================
-- Insert Data into Tables
-- ===========================

-- Insert Users
INSERT INTO users (username, user_password, email, first_name, last_name, is_business, created_at, updated_at)
VALUES
('testuser1', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test1@mail.com', 'Alice', 'Smith', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test2@mail.com', 'Bob', 'Johnson', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test3@mail.com', 'Carol', 'Williams', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser4', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test4@mail.com', 'Dave', 'Brown', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser5', '$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO', 'test5@mail.com', 'Eve', 'Davis', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign Roles to Users
INSERT INTO user_roles (user_id, role)
VALUES
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER');

-- Insert Personal Profiles
INSERT INTO personal_profiles (user_id, bio)
VALUES
(1, 'Passionate about technology and innovation.'),
(2, 'Avid traveler and food lover.'),
(3, 'Entrepreneur and business enthusiast.'),
(4, 'Love photography and outdoor adventures.'),
(5, 'Tech geek and coffee addict.');

-- Insert Tags
INSERT INTO tags (tag_name)
VALUES
('Technology'),
('Travel'),
('Food'),
('Business'),
('Photography'),
('Lifestyle'),
('Health'),
('Education'),
('Entertainment'),
('Sports');

-- Insert Posts
INSERT INTO posts (author_id, title, content, created_at, updated_at)
VALUES
(1, 'Exploring New Technologies', 'Today I delved into the world of AI and Machine Learning. It''s fascinating!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'My Trip to Japan', 'Just returned from an amazing trip to Japan. The culture and food were incredible!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Startup Tips', 'Sharing some insights on how to get your startup off the ground successfully.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Photography Hacks', 'Here are some quick tips to improve your landscape photography.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Healthy Eating', 'Discussing the benefits of maintaining a balanced diet for a healthy lifestyle.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Latest Tech Gadgets', 'Reviewed some of the latest gadgets in the market. Check out my thoughts!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Backpacking Europe', 'Planning my next backpacking adventure across Europe. Suggestions welcome!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Business Networking', 'The importance of networking in building successful business relationships.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Sunset Photography', 'Captured some stunning sunsets during my recent trip. Sharing them here.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Morning Workout Routine', 'Sharing my effective morning workout routine to kickstart the day.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Associate Posts with Tags
INSERT INTO post_tags (post_id, tag_id)
VALUES
(1, 1), -- Technology
(1, 6), -- Lifestyle
(2, 2), -- Travel
(2, 3), -- Food
(3, 4), -- Business
(3, 8), -- Education
(4, 5), -- Photography
(4, 6), -- Lifestyle
(5, 7), -- Health
(5, 6), -- Lifestyle
(6, 1), -- Technology
(6, 10), -- Sports (assuming tech in sports context)
(7, 2), -- Travel
(7, 6), -- Lifestyle
(8, 4), -- Business
(8, 9), -- Entertainment
(9, 5), -- Photography
(9, 2), -- Travel
(10, 7), -- Health
(10, 6); -- Lifestyle

-- Tag Users in Posts
INSERT INTO tagged_users (post_id, user_id)
VALUES
(2, 3), -- Carol tagged in Bob's travel post
(4, 1), -- Alice tagged in Dave's photography post
(5, 2), -- Bob tagged in Eve's health post
(7, 5); -- Eve tagged in Bob's travel post


-- User Followed Tags
INSERT INTO user_followed_tags (user_id, tag_id)
VALUES
(1, 1), -- Alice follows Technology
(1, 4), -- Alice follows Business
(2, 2), -- Bob follows Travel
(2, 3), -- Bob follows Food
(3, 4), -- Carol follows Business
(3, 8), -- Carol follows Education
(4, 5), -- Dave follows Photography
(4, 6), -- Dave follows Lifestyle
(5, 7), -- Eve follows Health
(5, 6); -- Eve follows Lifestyle

-- User Liked Posts
INSERT INTO user_liked_posts (user_id, post_id)
VALUES
(1, 2), -- Alice liked Bob's Japan trip post
(1, 3), -- Alice liked Carol's startup tips
(2, 1), -- Bob liked Alice's technology post
(2, 4), -- Bob liked Dave's photography hacks
(3, 5), -- Carol liked Eve's healthy eating post
(3, 1), -- Carol liked Alice's technology post
(4, 2), -- Dave liked Bob's Japan trip post
(4, 3), -- Dave liked Carol's startup tips
(5, 4), -- Eve liked Dave's photography hacks
(5, 3); -- Eve liked Carol's startup tips

-- User Following Relationships
INSERT INTO user_following (user_id, following_id)
VALUES
(1, 2), -- Alice follows Bob
(1, 3), -- Alice follows Carol
(2, 1), -- Bob follows Alice
(2, 4), -- Bob follows Dave
(3, 5), -- Carol follows Eve
(3, 1), -- Carol follows Alice
(4, 2), -- Dave follows Bob
(4, 3), -- Dave follows Carol
(5, 4), -- Eve follows Dave
(5, 1); -- Eve follows Alice

ALTER SEQUENCE user_sequence RESTART WITH (SELECT MAX(user_id) + 1 FROM users);
ALTER SEQUENCE post_sequence RESTART WITH 5;
ALTER SEQUENCE tag_sequence RESTART WITH 4;