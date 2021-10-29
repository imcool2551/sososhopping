CREATE TABLE admin (
    admin_id BIGINT NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE admin
ADD CONSTRAINT uk__admin__nickname UNIQUE (nickname);