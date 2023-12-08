package repository;

import model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository implements CrudOperations {

    private Connection connection;
    private static final String UPDATE_CURRENCY_SQL = "UPDATE currency SET code = ?, name = ? WHERE id = ?";

    private static final String SELECT_CURRENCY_BY_ID_SQL = "SELECT * FROM currency WHERE id = ?";
    private static final String SELECT_ALL_CURRENCIES_SQL = "SELECT * FROM currency";

    public CurrencyRepository(Connection connection) {
        this.connection = connection;
    }


    //get all
    @Override
    public List findAll() {
        List<Currency> currencies = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENCIES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currencies.add(mapResultSetToCurrency(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    private Currency mapResultSetToCurrency(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String code = resultSet.getString("name");
        String name = resultSet.getString("code");
        return new Currency(id, name, code);
    }

    //get by id
    @Override
    public Object findById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENCY_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCurrency(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //update by id
    @Override
    public void updateById(int id, Object updatedEntity) {
        if (updatedEntity instanceof Currency) {
            Currency updatedCurrency = (Currency) updatedEntity;
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CURRENCY_SQL)) {
                preparedStatement.setString(1, updatedCurrency.getCode());
                preparedStatement.setString(2, updatedCurrency.getName());
                preparedStatement.setInt(3, id);

                preparedStatement.executeUpdate();
                System.out.println("Currency updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Updated entity must be an instance of Currency");
        }
    }


}
