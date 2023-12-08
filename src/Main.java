import model.Currency;
import repository.ConnectionConfiguration;
import repository.CurrencyRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionConfiguration.getInstance().getConnection();

        try {
            CurrencyRepository currencyRepository = new CurrencyRepository(connection);

            // Get all currencies
            List<Currency> currencies = currencyRepository.findAll();
            for (Currency currency : currencies) {
                System.out.println(currency);
            }

            // Get currency by id
            int targetCurrencyId = 1;
            Currency specificCurrency = (Currency) currencyRepository.findById(targetCurrencyId);

            if (specificCurrency != null) {
                System.out.println("Currency found by ID " + targetCurrencyId + ": " + specificCurrency);

                // Update currency by id
                specificCurrency.setName("Updated Name");
                specificCurrency.setCode("USD");

                currencyRepository.updateById(targetCurrencyId, specificCurrency);

                // Print the updated currency
                Currency updatedCurrency = (Currency) currencyRepository.findById(targetCurrencyId);
                System.out.println("Currency after update: " + updatedCurrency);
            } else {
                System.out.println("Currency with ID " + targetCurrencyId + " not found.");
            }

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
}
