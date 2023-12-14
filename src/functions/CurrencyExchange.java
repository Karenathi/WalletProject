package functions;

import java.util.Date;
import java.util.List;

public class CurrencyExchange {
    public static double calculateWeightedAverage(List<CurrencyValue> currencyValues) {
        double totalWeightedValue = 0.0;
        double totalWeight = 0.0;

        for (CurrencyValue currencyValue : currencyValues) {
            double weight = getWeight(currencyValue.getDate());
            totalWeightedValue += currencyValue.getExchangeRate() * weight;
            totalWeight += weight;
        }

        return totalWeightedValue / totalWeight;
    }

    private static double getWeight(Date date) {
        int hour = date.getHours();

        if (hour >= 0 && hour < 6) {
            return 2.0;  // Higher weight for morning hours
        } else {
            return 1.0;
        }
    }


    public static class CurrencyValue {
        private Date date;
        private double exchangeRate;

        public CurrencyValue(Date date, double exchangeRate) {
            this.date = date;
            this.exchangeRate = exchangeRate;
        }

        public Date getDate() {
            return date;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }
    }
}
