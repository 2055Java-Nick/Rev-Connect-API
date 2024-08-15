DROP TABLE IF EXISTS businessprofile;
CREATE TABLE businessprofile (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL,
    bio_text VARCHAR(500)
);

INSERT INTO businessprofile VALUES (999, 111, 'Test Bio 1');
INSERT INTO businessprofile VALUES (998, 112, 'Test Bio 2');
INSERT INTO businessprofile VALUES (997, 113, 'Test Bio 3');
INSERT INTO businessprofile VALUES (996, 114, 'Test Bio 4');
INSERT INTO businessprofile VALUES (995, 115, 'Test Bio 5');
INSERT INTO businessprofile VALUES (994, 116, 'Test Bio 6');