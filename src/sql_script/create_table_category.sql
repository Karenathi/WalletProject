CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL
);

INSERT INTO category (name, type) VALUES ('Restaurant', 'debit');
INSERT INTO category (name, type) VALUES ('Salaire', 'credit');
INSERT INTO category (name, type) VALUES ('Prêt', 'both');

---Wallet categories

-- Catégorie avec ID 5 (Shopping)
INSERT INTO category (id, name, type)
SELECT 5, 'Shopping', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 5);

-- Catégorie avec ID 6 (Housing)
INSERT INTO category (id, name, type)
SELECT 6, 'Housing', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 6);

-- Catégorie avec ID 4 (Transportation)
INSERT INTO category (id, name, type)
SELECT 4, 'Transportation', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 4);

-- Logement
INSERT INTO category (id, name, type)
SELECT 7, 'Logement', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 7);

-- Transport
INSERT INTO category (id, name, type)
SELECT 8, 'Transport', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 8);

-- Vie et divertissement
INSERT INTO category (id, name, type)
SELECT 9, 'Vie et divertissement', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 9);

-- Communication et informatique
INSERT INTO category (id, name, type)
SELECT 10, 'Communication et informatique', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 10);

-- Dépenses financières
INSERT INTO category (id, name, type)
SELECT 11, 'Dépenses financières', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 11);

-- Investissements
INSERT INTO category (id, name, type)
SELECT 12, 'Investissements', 'debit'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 12);

-- Income
INSERT INTO category (id, name)
SELECT 10, 'Revenus'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = 10);







