CREATE TABLE books (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(50)
);
-- see com.tor.entity.Publisher
ALTER TABLE books ADD publisher VARCHAR(100);
ALTER TABLE books ADD publisher_address VARCHAR(200);