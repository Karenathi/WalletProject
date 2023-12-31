import model.Currency;
import repository.ConnectionConfiguration;
import repository.CurrencyRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import functions.CurrencyFunction;

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



            double result = CurrencyFunction.calculateExchangeRate(connection, LocalDateTime.of(2023, 12, 6, 0, 0), 1, 2, CurrencyFunction.CalculationType.AVERAGE);
            System.out.println("Taux de change moyen pondéré : " + result);


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
