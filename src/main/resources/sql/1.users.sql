CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    phone CHAR(11) NOT NULL,
    street_address VARCHAR(40) NOT NULL,
    detailed_address VARCHAR(20) NOT NULL,
    active VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE users
ADD CONSTRAINT uk__user__email
UNIQUE(email);

ALTER TABLE users
ADD CONSTRAINT uk__user__phone
UNIQUE(phone);
