package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private int id;
    private String label;
    private double amount;
    private String type;
    private LocalDateTime date;

    // Constructeurs, getters et setters
    public Transaction() {}

    public Transaction(int id, String label, double amount, String type, LocalDateTime date, Account account) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    //Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return getId() == that.getId() && Double.compare(getAmount(), that.getAmount()) == 0 && Objects.equals(getLabel(), that.getLabel()) && Objects.equals(getType(), that.getType()) && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLabel(), getAmount(), getType(), getDate());
    }


    //ToString

    @Override
    public String toString() {
        return "Transaction:" +
                "id='" + id + '\'' +
                ", category='" + amount + '\'' +
                ", label ='" + label + '\'' +
                ", date=" + date;
    }
}

