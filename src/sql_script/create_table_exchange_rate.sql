CREATE TABLE IF NOT EXISTS exchange_rate (
    id INT PRIMARY KEY,
    source_currency_id INT,
    target_currency_id INT,
    rate DOUBLE PRECISION,
    date TIMESTAMP,
    hour INT
);

ALTER TABLE exchange_rate
ADD FOREIGN KEY (source_currency_id) REFERENCES currency(id),
ADD FOREIGN KEY (target_currency_id) REFERENCES currency(id);

CREATE SEQUENCE exchange_rate_id_seq START 1;

ALTER TABLE exchange_rate ALTER COLUMN id SET DEFAULT nextval('exchange_rate_id_seq');


INSERT INTO exchange_rate (id, source_currency_id, target_currency_id, rate, date, hour)
VALUES
    (DEFAULT, 1, 2, 1.12345, '2023-12-06', 12),
    (DEFAULT, 1, 2, 1.23456, '2023-12-06', 14),
    (DEFAULT, 1, 2, 1.34567, '2023-12-06', 16);
