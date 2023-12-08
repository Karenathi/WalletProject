CREATE TABLE IF NOT EXISTS account(
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    currency_id INT REFERENCES Currency(id),
    code VARCHAR(20) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.0,
    balance_date TIMESTAMP,
    type VARCHAR(20) CHECK (type IN ('bank', 'cash', 'mobile money'))
);

-- Inserting data for the first account
INSERT INTO "account" (id, name, currency_id, code, balance, balance_date, type)
VALUES (1, 'Savings Account', 1, 'SA001', 1000.00, '2023-12-08 12:00:00', 'bank');

-- Inserting data for the second account
INSERT INTO "account" (id, name, currency_id, code, balance, balance_date, type)
VALUES (2, 'Wallet', 2, 'W001', 50.50, '2023-12-08 12:30:00', 'cash');

-- Inserting data for the third account
INSERT INTO "account" (id, name, currency_id, code, balance, balance_date, type)
VALUES (3, 'Mobile Money Wallet', 3, 'MMW001', 200.25, '2023-12-08 13:00:00', 'mobile money');





