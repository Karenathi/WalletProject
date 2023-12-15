package functions;

import repository.ConnectionConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CategoryFunction {

    public static CategorySum getCategorySum(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        try (Connection connection = ConnectionConfiguration.getInstance().getConnection()) {
            String sql = "SELECT * FROM get_category_sum(?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountId);
                preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(startDate));
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(endDate));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double restaurant = resultSet.getDouble("restaurant");
                        double salaire = resultSet.getDouble("salaire");

                        return new CategorySum(restaurant, salaire);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting category sum.", e);
        }

        return null;
    }

    public static class CategorySum {
        private final double restaurant;
        private final double salaire;

        public CategorySum(double restaurant, double salaire) {
            this.restaurant = restaurant;
            this.salaire = salaire;
        }

        public double getRestaurant() {
            return restaurant;
        }

        public double getSalaire() {
            return salaire;
        }
    }
}
