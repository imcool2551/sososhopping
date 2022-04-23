CREATE TABLE user_report (
  user_report_id BIGINT NOT NULL AUTO_INCREMENT,
  store_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  handled TINYINT NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  PRIMARY KEY (user_report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_report
ADD CONSTRAINT fk__user_report__owner_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

ALTER TABLE user_report
ADD CONSTRAINT fk__user_report__store_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

CREATE INDEX idx__user_report__created_at ON user_report (created_at);

