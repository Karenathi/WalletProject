package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Balance {
    private int id;
    private double value;
    private LocalDateTime datetime;

    // Constructeurs, getters et setters
    public Balance(double value, LocalDateTime datetime) {
        this.value = value;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance that)) return false;
        return getId() == that.getId() &&
                Double.compare(that.getValue(), getValue()) == 0 &&
                Objects.equals(getDatetime(), that.getDatetime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getDatetime());
    }

    // ToString
    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", value=" + value +
                ", datetime=" + datetime +
                '}';
    }
}
