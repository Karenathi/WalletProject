CREATE TABLE IF NOT EXISTS "transactions" (
    id INT PRIMARY KEY,
    label VARCHAR(100) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('credit', 'debit')) NOT NULL,
    date TIMESTAMP NOT NULL,
    account INT REFERENCES "account"(id)
);

-- Inserting data for the first transaction
INSERT INTO "transactions" (id, label, amount, type, date, account)
VALUES (1, 'Salary Deposit', 3000.00, 'credit', '2023-12-08 14:00:00', 1);

-- Inserting data for the second transaction
INSERT INTO "transactions" (id, label, amount, type, date, account)
VALUES (2, 'Grocery Shopping', 150.75, 'debit', '2023-12-08 15:30:00', 2);

-- Inserting data for the third transaction
INSERT INTO "transactions" (id, label, amount, type, date, account)
VALUES (3, 'Mobile Recharge', 20.00, 'debit', '2023-12-08 16:45:00', 3);
