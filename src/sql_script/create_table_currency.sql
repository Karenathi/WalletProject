CREATE TABLE IF NOT EXISTS currency (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    code VARCHAR(100) NOT NULL
);

INSERT INTO currency (id, name, code)
VALUES
    (1, 'Euro', 'EUR'),
    (2, 'Dollar', 'USD'),
    (3, 'British Pound', 'GBP');
