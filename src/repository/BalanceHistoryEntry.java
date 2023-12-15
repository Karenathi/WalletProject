package repository;

import java.time.LocalDateTime;
import java.util.Objects;

public class BalanceHistoryEntry {
    private LocalDateTime dateTime;
    private double balance;

    public BalanceHistoryEntry(LocalDateTime dateTime, double balance) {
        this.dateTime = dateTime;
        this.balance = balance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "BalanceHistoryEntry{" +
                "dateTime=" + dateTime +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceHistoryEntry that)) return false;
        return Double.compare(getBalance(), that.getBalance()) == 0 && Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateTime(), getBalance());
    }
}
