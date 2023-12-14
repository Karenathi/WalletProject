package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrencyFunction {

    // Enum pour définir les types de calcul possibles
    public enum CalculationType {
        AVERAGE,
        MINIMUM,
        MAXIMUM,
        MEDIAN
    }

    public static double calculateExchangeRate(Connection connection, LocalDateTime date, int sourceCurrencyId, int targetCurrencyId, CalculationType calculationType) {

        String sql = "SELECT rate, hour FROM exchange_rate WHERE date = ? AND source_currency_id = ? AND target_currency_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Remplacez les paramètres avec la date et les identifiants de devises que vous souhaitez
            statement.setObject(1, date);
            statement.setInt(2, sourceCurrencyId);
            statement.setInt(3, targetCurrencyId);

            ResultSet resultSet = statement.executeQuery();

            List<Double> rates = new ArrayList<>();

            while (resultSet.next()) {
                double rate = resultSet.getDouble("rate");
                rates.add(rate);
            }

            if (rates.isEmpty()) {
                throw new RuntimeException("Aucun taux de change disponible pour cette date et ces devises.");
            }

            switch (calculationType) {
                case AVERAGE:
                    return calculateAverage(rates);
                case MINIMUM:
                    return Collections.min(rates);
                case MAXIMUM:
                    return Collections.max(rates);
                case MEDIAN:
                    return calculateMedian(rates);
                default:
                    throw new IllegalArgumentException("Type de calcul non valide : " + calculationType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du calcul du taux de change.");
        }
    }

    private static double calculateAverage(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }


    private static double calculateMedian(List<Double> values) {
        Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }
}
