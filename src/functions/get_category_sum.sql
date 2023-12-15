CREATE OR REPLACE FUNCTION get_category_sum(account_id INT, start_date TIMESTAMP, end_date TIMESTAMP)
RETURNS TABLE (restaurant DECIMAL, salaire DECIMAL) AS $$
BEGIN
    RETURN QUERY
    SELECT
        COALESCE(SUM(CASE WHEN category.name = 'Restaurant' THEN transaction.amount ELSE 0 END), 0) AS restaurant,
        COALESCE(SUM(CASE WHEN category.name = 'Salaire' THEN transaction.amount ELSE 0 END), 0) AS salaire
    FROM
        category
    LEFT JOIN transaction ON category.id = transaction.category_id AND transaction.account_id = account_id AND transaction.date BETWEEN start_date AND end_date;
END;
$$ LANGUAGE plpgsql;
