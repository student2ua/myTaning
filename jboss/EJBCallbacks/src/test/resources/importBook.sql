CREATE TABLE demo.books (
  id   INTEGER PRIMARY KEY,
  name VARCHAR(50)
);
-- see com.tor.entity.Publisher
ALTER TABLE demo.books ADD publisher VARCHAR(100);
ALTER TABLE demo.books ADD publisher_address VARCHAR(200);