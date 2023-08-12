CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       password VARCHAR(120) NOT NULL,
                       mbti VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id INT,
                            PRIMARY KEY (user_id, role_id)
);
