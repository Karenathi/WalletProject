package functions;

import repository.ConnectionConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BalanceCalculator {

    public static double getBalanceChanges(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        Connection connection = null;

        try {
            connection = ConnectionConfiguration.getInstance().getConnection();

            String sql = "SELECT COALESCE(SUM(amount), 0) AS total_changes " +
                    "FROM transactions " +
                    "WHERE account = ? AND date BETWEEN ? AND ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountId);
                preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(startDate));
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(endDate));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDouble("total_changes");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calculating balance changes.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0.0;
    }

}
