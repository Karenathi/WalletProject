package functions;;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CurrencyFunction {
    public static double calculateWeightedAverageExchangeRate(Connection connection, LocalDateTime date) {
        String sql = "SELECT rate, hour FROM ExchangeRate WHERE date = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, date);

            ResultSet resultSet = statement.executeQuery();

            double totalRate = 0.0;
            double totalWeight = 0.0;

            while (resultSet.next()) {
                double rate = resultSet.getDouble("rate");
                int hour = resultSet.getInt("hour");

                double weight = calculateWeight(hour);

                totalRate += rate * weight;
                totalWeight += weight;
            }

            if (totalWeight == 0.0) {
                throw new RuntimeException("Aucun taux de change disponible pour cette date.");
            }

            return totalRate / totalWeight;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du calcul du taux de change moyen pondéré.");
        }
    }

    private static double calculateWeight(int hour) {
        return 1.0;
    }
}