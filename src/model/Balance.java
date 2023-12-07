package model;

import java.time.LocalDateTime;

public class Balance {
    private double value;
    private LocalDateTime datetime;

    // Constructeurs, getters et setters
    public Balance() {}

    public Balance(double value, LocalDateTime datetime) {
        this.value = value;
        this.datetime = datetime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}

