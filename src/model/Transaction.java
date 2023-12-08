package model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String label;
    private double amount;
    private String type;
    private LocalDateTime date;
    private Account account;

    // Constructeurs, getters et setters
    public Transaction() {}

    public Transaction(int id, String label, double amount, String type, LocalDateTime date, Account account) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //ToString

    @Override
    public String toString() {
        return "Transaction:" +
                "id='" + id + '\'' +
                ", category='" + amount + '\'' +
                ", label ='" + label + '\'' +
                ", date=" + date +
                ", paymentId='" + type;
    }
}

