CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL
);

INSERT INTO category (name, type) VALUES ('Restaurant', 'debit');
INSERT INTO category (name, type) VALUES ('Salaire', 'credit');
INSERT INTO category (name, type) VALUES ('Prêt', 'both');

