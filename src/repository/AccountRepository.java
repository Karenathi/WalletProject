package repository;

import model.Account;
import model.Balance;
import model.Currency;
import model.Transaction;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}

