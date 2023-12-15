CREATE OR REPLACE FUNCTION get_category_sum(IN bank_account_id INT, IN start_date TIMESTAMP, IN end_date TIMESTAMP)
    RETURNS TABLE (category_name VARCHAR(100), category_amount_total DECIMAL) AS
    $$
    BEGIN
        RETURN QUERY
        SELECT
        category.name,
        COALESCE(SUM(CASE WHEN transactions.category_id = category.id THEN amount ELSE 0 END),0) AS category_amount_total
        FROM category
        LEFT JOIN transactions ON transactions.category_id = category.id
        WHERE account = bank_account_id
        AND transactions.date BETWEEN start_date AND end_date
        GROUP BY category.name;
    END;
    $$
    LANGUAGE plpgsql;