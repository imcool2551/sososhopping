CREATE TABLE store_business_day (
    store_business_day_id BIGINT NOT NULL AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    day VARCHAR(10) NOT NULL,
    is_open TINYINT NOT NULL,
    open_time CHAR(5),
    close_time CHAR(5),
    PRIMARY KEY (store_business_day_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store_business_day
ADD CONSTRAINT uk__store_business_day__store_id__day
UNIQUE (store_id, day);

ALTER TABLE store_business_day
ADD CONSTRAINT fk_store_business_day__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);