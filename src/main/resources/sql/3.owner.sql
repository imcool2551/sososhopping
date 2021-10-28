CREATE TABLE owner (
    owner_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(20) NOT NULL,
    phone CHAR(11) NOT NULL,
    active VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE owner
ADD CONSTRAINT uk__owner__email
UNIQUE (email);

ALTER TABLE owner
ADD CONSTRAINT uk__owner__phone
UNIQUE (phone);
