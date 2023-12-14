package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CurrencyFunction {

    public static double calculateWeightedAverageExchangeRate(Connection connection, LocalDateTime date, int sourceCurrencyId, int targetCurrencyId, String weightingMethod) {
        String sql = "SELECT rate, hour FROM exchange_rate WHERE date = ? AND source_currency_id = ? AND target_currency_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, date);
            statement.setInt(2, sourceCurrencyId);
            statement.setInt(3, targetCurrencyId);

            ResultSet resultSet = statement.executeQuery();

            double totalRate = 0.0;
            double totalWeight = 0.0;

            while (resultSet.next()) {
                double rate = resultSet.getDouble("rate");
                int hour = resultSet.getInt("hour");

                double weight = calculateWeight(hour, weightingMethod);

                totalRate += rate * weight;
                totalWeight += weight;
            }

            if (totalWeight == 0.0) {
                throw new RuntimeException("Aucun taux de change disponible pour cette date et ces devises.");
            }

            return totalRate / totalWeight;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du calcul du taux de change moyen pondéré.");
        }
    }

    private static double calculateWeight(int hour, String weightingMethod) {

        switch (weightingMethod) {
            case "average":
                return 1.0;
            case "minimum":
                // Logique pour le poids minimum
                return 0.8;
            case "maximum":
                // Logique pour le poids maximum
                return 1.2;
            default:
                throw new IllegalArgumentException("Méthode de pondération non valide : " + weightingMethod);
        }
    }
}
