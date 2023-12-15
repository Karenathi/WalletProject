CREATE OR REPLACE FUNCTION sum_entries_exits(IN bank_account_id INT, IN start_date TIMESTAMP, IN end_date TIMESTAMP)
    RETURNS TABLE(total_entry DECIMAL, total_exit DECIMAL) AS
    $$
    BEGIN
        RETURN QUERY
        SELECT
            COALESCE(SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END), 0) AS total_entry,
            COALESCE(SUM(CASE WHEN amount < 0 THEN amount ELSE 0 END), 0) AS total_exit
        FROM transactions
        WHERE account = bank_account_id AND transactions.date BETWEEN start_date AND end_date;
     END;
     $$
     LANGUAGE plpgsql;