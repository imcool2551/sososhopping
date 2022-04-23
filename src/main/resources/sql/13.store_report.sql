CREATE TABLE store_report (
  store_report_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  store_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  handled TINYINT NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  PRIMARY KEY (store_report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store_report
ADD CONSTRAINT fk__store_report__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE store_report
ADD CONSTRAINT fk__store_report__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

CREATE INDEX idx__store_report__created_at ON store_report (created_at);

