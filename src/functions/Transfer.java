package functions;

import model.Transaction;
import repository.ConnectionConfiguration;

import java.sql.*;
import java.time.LocalDateTime;

public class Transfer {
    public void performTransferSameCurrency(int debitAccountId, int creditAccountId, double amount) {
        Connection connection = null;

        try {
            connection = ConnectionConfiguration.getInstance().getConnection();

            if (debitAccountId == creditAccountId) {
                throw new RuntimeException("Un compte ne peut pas effectuer un transfert vers lui-même.");
            }

            Transaction debitTransaction = new Transaction(
                    0,
                    "Debit",
                    amount,
                    "debit",
                    LocalDateTime.now(),
                    debitAccountId,
                    0
            );
            debitTransaction.setAmount(amount);

            Transaction creditTransaction = new Transaction(
                    0,
                    "Credit",
                    amount,
                    "credit",
                    LocalDateTime.now(),
                    creditAccountId,
                    0
            );
            creditTransaction.setAmount(amount);



            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            throw new RuntimeException("Erreur lors du transfert entre comptes de même devise.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void recordTransferHistory(int debitTransactionId, int creditTransactionId, LocalDateTime transferDate, Connection connection) throws SQLException {
        String sql = "INSERT INTO TransferHistory (debit_transaction_id, credit_transaction_id, transfer_date) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, debitTransactionId);
            preparedStatement.setInt(2, creditTransactionId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(transferDate));

            preparedStatement.executeUpdate();
        }
    }
    public void performTransaction(int accountId, Transaction transaction, Connection connection) {
        try {
            insertTransaction(accountId, transaction, connection);

            updateBalance(accountId, transaction.getAmount(), connection);

            insertBalanceHistory(accountId, calculateNewBalance(accountId, connection), transaction.getDate(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la réalisation de la transaction.", e);
        }
    }

    private void insertTransaction(int accountId, Transaction transaction, Connection connection) throws SQLException {
        String sql = "INSERT INTO transaction (account_id, label, amount, type, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, transaction.getLabel());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de la transaction a échoué, aucun ID obtenu.");
                }
            }
        }
    }

    private void updateBalance(int accountId, double amount, Connection connection) throws SQLException {
        String sql = "UPDATE account SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
        }
    }

    private void insertBalanceHistory(int accountId, double newBalance, LocalDateTime date, Connection connection) throws SQLException {
        String sql = "INSERT INTO balance_history (account_id, balance, date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setDouble(2, newBalance);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(date));
            preparedStatement.executeUpdate();
        }
    }

    private double calculateNewBalance(int accountId, Connection connection) throws SQLException {
        String sql = "SELECT balance FROM account WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    throw new SQLException("Impossible de récupérer le solde mis à jour du compte.");
                }
            }
        }
    }
}
