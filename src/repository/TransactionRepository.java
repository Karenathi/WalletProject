package repository;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements CrudOperations<Transaction> {
    Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    private Transaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction(
                resultSet.getInt("id"),
                resultSet.getString("label"),
                resultSet.getDouble("amount"),
                resultSet.getString("type"),
                resultSet.getTimestamp("date").toLocalDateTime(),
                resultSet.getInt("account"),
                resultSet.getInt("category_id")
        );
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions"; // Modifier le nom de la table

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Transaction transaction = createTransactionFromResultSet(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return transactions;
    }

    @Override
    public Transaction findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createTransactionFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void updateById(int id, Transaction updatedEntity) {
        String sql = "UPDATE transactions SET label = ?, amount = ?, type = ?, date = ?, account_id = ?, category_id = ? WHERE id = ?"; // Modifier le nom de la table et les colonnes

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedEntity.getLabel());
            preparedStatement.setDouble(2, updatedEntity.getAmount());
            preparedStatement.setString(3, updatedEntity.getType());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(updatedEntity.getDate()));
            preparedStatement.setInt(5, updatedEntity.getAccountId());
            preparedStatement.setInt(6, updatedEntity.getCategoryId());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
