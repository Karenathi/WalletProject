package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExchangeRate {
    private int id;
    private int sourceCurrencyId;
    private int targetCurrencyId;
    private double rate;
    private LocalDateTime date;
    private int hour;

    // Constructeurs, getters et setters
    public ExchangeRate() {}

    public ExchangeRate(int id, int sourceCurrencyId, int targetCurrencyId, double rate, LocalDateTime date, int hour) {
        this.id = id;
        this.sourceCurrencyId = sourceCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
        this.date = date;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(int sourceCurrencyId) {
        this.sourceCurrencyId = sourceCurrencyId;
    }

    public int getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(int targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate that)) return false;
        return getId() == that.getId() &&
                getSourceCurrencyId() == that.getSourceCurrencyId() &&
                getTargetCurrencyId() == that.getTargetCurrencyId() &&
                Double.compare(that.getRate(), getRate()) == 0 &&
                getHour() == that.getHour() &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSourceCurrencyId(), getTargetCurrencyId(), getRate(), getDate(), getHour());
    }

    // ToString
    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", sourceCurrencyId=" + sourceCurrencyId +
                ", targetCurrencyId=" + targetCurrencyId +
                ", rate=" + rate +
                ", date=" + date +
                ", hour=" + hour +
                '}';
    }
}
