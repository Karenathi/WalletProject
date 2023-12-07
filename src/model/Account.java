package model;

import java.util.List;

public class Account {
    private String name;
    private Currency currency;
    private String code;
    private Balance balance;
    private String type;
    private List<Transaction> transactions;

    // Constructeurs, getters et setters
    public Account() {}

    public Account(String name, Currency currency, String code, Balance balance, String type, List<Transaction> transactions) {
        this.name = name;
        this.currency = currency;
        this.code = code;
        this.balance = balance;
        this.type = type;
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
