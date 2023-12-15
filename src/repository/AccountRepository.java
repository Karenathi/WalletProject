package repository;

import model.Account;
import model.Balance;
import model.Currency;
import model.Transaction;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements CrudOperations<Account> {
    private Connection connection;
    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException{
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setCode(resultSet.getString("code"));
        account.setType((resultSet.getString("type")));

        Currency currency = new Currency();
        currency.setName(resultSet.getString("name"));

        Balance balance = new Balance();
        balance.setValue((resultSet.getDouble("value")));

        return account;
    }

    private Transaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException{
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt("id"));
        transaction.setLabel(resultSet.getString("label"));
        transaction.setAmount(resultSet.getDouble("amount"));
        transaction.setType(resultSet.getString("transaction_type"));
        transaction.setDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
        return transaction;
    }
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM \"account\"";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Account account = createAccountFromResultSet(resultSet);
                accounts.add(account);
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return accounts;
    }

    @Override
    public Account findById(int id) {
        String sql = "SELECT * FROM \"account\" WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return createAccountFromResultSet(resultSet);
            }
        }catch(Exception e){
            System.out.println("Error:"+e.getMessage());
        }
        return null;
    }

    @Override
    public void updateById(int id, Account updatedEntity) {
        String sql = "UPDATE \"account\" SET  name = ?, code = ?, type = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedEntity.getName());
            preparedStatement.setString(2, updatedEntity.getCode());
            preparedStatement.setString(3, updatedEntity.getType());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error:"+e.getMessage());
        }
    }

    public List<Transaction> getTransactionsForAccount(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = createTransactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }

        return transactions;
    }

    //Performing transaction in an account
    public Account performTransaction (int accountId, Transaction transaction) {
        Connection connection = null;
        try {
            connection = ConnectionConfiguration.getInstance().getConnection();
            connection.setAutoCommit(false);
            Account account = findById(accountId);
            double newBalance = account.getBalance().getValue();
            if (account == null) {
                throw new RuntimeException("The account with the id " + accountId + "does not exist");
            }
            if ("debit".equalsIgnoreCase(transaction.getType())) {
                if (account.getType().equalsIgnoreCase("bank account")) {
                    newBalance -= transaction.getAmount();
                } else if (newBalance < transaction.getAmount()) {
                    throw new RuntimeException("Insufficient balance to complete the transaction");
                }
                newBalance -= transaction.getAmount();
            } else if ("credit".equalsIgnoreCase(transaction.getType())) {
                newBalance += transaction.getAmount();
            } else {
                throw new RuntimeException("Transaction type not supported");
            }
            updateBalance(accountId, newBalance, connection);

            insertTransaction(accountId, transaction, connection);

            insertBalanceHistory(accountId, newBalance, transaction.getDate(), connection);

            connection.commit();

            Account updatedAccount = findById(accountId);

            return updatedAccount;


        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la transaction.", e);
        } finally {
            // Réactiver l'autocommit
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBalance(int accountId, double newBalance, Connection connection) throws SQLException {
        String sql = "UPDATE balance SET amount = ? WHERE account_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, accountId);

            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour insérer une nouvelle transaction dans la table 'transaction'
    private void insertTransaction(int accountId, Transaction transaction, Connection connection) throws SQLException {
        String sql = "INSERT INTO transaction (account_id, label, amount, transaction_type, transaction_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, transaction.getLabel());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));

            preparedStatement.executeUpdate();
        }
    }

    private void insertBalanceHistory(int accountId, double newBalance, LocalDateTime transactionDate, Connection connection) throws SQLException {
        String sql = "INSERT INTO balance_history (account_id, balance_amount, transaction_date) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setDouble(2, newBalance);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(transactionDate));

            preparedStatement.executeUpdate();
        }
    }

    //Select the balance of an account in given date and time
    public double getBalanceAtDateTime(int accountId, LocalDateTime dateTime) {
        Connection connection = null;

        try {
            connection = ConnectionConfiguration.getInstance().getConnection();
            List<Transaction> transactions = getTransactionsUntilDateTime(accountId, dateTime, connection);
            double balance = 0.0;
            for (Transaction transaction : transactions) {
                if ("credit".equalsIgnoreCase("transaction.getType")) {
                    balance += transaction.getAmount();
                } else if ("debit".equalsIgnoreCase(transaction.getType())) {
                    balance -= transaction.getAmount();
                }
            }
            return balance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du solde à la date et l'heure spécifiées.", e);
        } finally {
            // Fermer la connexion
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private List<Transaction> getTransactionsUntilDateTime(int accountId, LocalDateTime dateTime, Connection connection) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id = ? AND transaction_date <= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = createTransactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }

    //Method to get the history of an account's balance in a date and time range
    public List<BalanceHistoryEntry> getBalanceHistoryInDateTimeRange(int accountId, LocalDateTime startDateTime, LocalDateTime endDateTime){
        Connection connection = null;
        try{
            connection = ConnectionConfiguration.getInstance().getConnection();

            List<Transaction> transactions = getTransactionsInDateTimeRange(accountId, startDateTime, endDateTime, connection);

            List<BalanceHistoryEntry> balanceHistory = new ArrayList<>();
            double currentBalance = 0;

            for (Transaction transaction : transactions) {
                if ("credit".equalsIgnoreCase(transaction.getType())) {
                    currentBalance += transaction.getAmount();
                } else if ("debit".equalsIgnoreCase(transaction.getType())) {
                    currentBalance -= transaction.getAmount();
                }

                balanceHistory.add(new BalanceHistoryEntry(transaction.getDate(), currentBalance));
            }

            return balanceHistory;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de l'historique du solde dans la plage de dates spécifiée.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private List<Transaction> getTransactionsInDateTimeRange(int accountId, LocalDateTime startDateTime, LocalDateTime endDateTime, Connection connection) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id = ? AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = createTransactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }

}

