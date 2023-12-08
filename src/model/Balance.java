package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Balance {
    int id;
    private double value;
    private LocalDateTime datetime;

    // Constructeurs, getters et setters
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

    //ToString
    @Override
    public String toString() {
        return "Balance:" +
                "value=" + value +
                ", datetime=" + datetime;
    }

    //Equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance balance)) return false;
        return Double.compare(getValue(), balance.getValue()) == 0 && Objects.equals(getDatetime(), balance.getDatetime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getDatetime());
    }
}

