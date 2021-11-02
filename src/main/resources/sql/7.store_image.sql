CREATE TABLE store_image (
    store_image_id BIGINT NOT NULL AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    img_url VARCHAR(512) NOT NULL,
    PRIMARY KEY (store_image_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store_image
ADD CONSTRAINT fk__store_image__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

