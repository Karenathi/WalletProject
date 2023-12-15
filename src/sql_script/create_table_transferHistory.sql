CREATE TABLE IF NOT EXISTS "TransferHistory"
(
    ID                    SERIAL PRIMARY KEY,
    debit_transaction_id  INT,
    credit_transaction_id INT,
    transfer_date         TIMESTAMP
);
