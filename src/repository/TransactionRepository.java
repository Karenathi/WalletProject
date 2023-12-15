package repository;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements CrudOperations<Transaction> {
    Connection connection;
    private Transaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt("id"));
        transaction.setLabel(resultSet.getString("label"));
        transaction.setAmount(resultSet.getDouble("amount"));
        transaction.setType(resultSet.getString("transaction_type"));
        transaction.setDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
        return transaction;
    }
    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Transaction transaction = createTransactionFromResultSet(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());;
        }

        return transactions;
    }

    @Override
    public Transaction findById(int id) {
        String sql = "SELECT * FROM transaction WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createTransactionFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());;
        }

        return null;

    }

    @Override
    public void updateById(int id, Transaction updatedEntity) {
        String sql = "UPDATE transaction SET label = ?, amount = ?, transaction_type = ?, transaction_date = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedEntity.getLabel());
            preparedStatement.setDouble(2, updatedEntity.getAmount());
            preparedStatement.setString(3, updatedEntity.getType());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(updatedEntity.getDate()));
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }

    }
}
