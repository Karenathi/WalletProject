package model;

import java.util.List;
import java.util.Objects;


public class Account {
    private int id;
    private String name;
    private Currency currency;
    private String code;
    private Balance balance;
    private String type;
    private List<Transaction> transactions;

    // Constructeurs, getters et setters
    public Account(int id,String name, Currency currency, String code, Balance balance, String type, List<Transaction> transactions) {
        this.id= id;
        this.name = name;
        this.currency = currency;
        this.code = code;
        this.balance = balance;
        this.type = type;
        this.transactions = transactions;
    }

    public Account() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account that)) return false;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCurrency(), that.getCurrency()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getBalance(), that.getBalance()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCurrency(), getCode(), getBalance(), getType(), getTransactions());
    }

    // ToString
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                ", code='" + code + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", transactions=" + transactions +
                '}';
    }

}
