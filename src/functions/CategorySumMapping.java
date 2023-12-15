package functions;

import repository.ConnectionConfiguration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CategorySumMapping {
    public Map<String, Double> mapTotalAmountByCategory(int bankAccountId, LocalDateTime startDate, LocalDateTime endDate){
        Connection connection = ConnectionConfiguration.getInstance().getConnection();
        Map<String, Double> result = new HashMap<>();
        String sql = "SELECT * FROM get_category_sum(?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, bankAccountId);
            preparedStatement.setDate(2, Date.valueOf(String.valueOf(startDate)));
            preparedStatement.setDate(3, Date.valueOf(String.valueOf(endDate)));

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.put(resultSet.getString("category_name"), resultSet.getDouble("category_amount_total"));
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return result;
    }
}
