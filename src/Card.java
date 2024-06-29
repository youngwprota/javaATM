import java.lang.String;
import java.util.Calendar;

public class Card {
    private final String pinCode;
    private final String cardNumber;
    private double balance;
    private boolean isBlocked;
    private long timeBlocked;

    public Card(String cardNumber, String pinCode, double balance, boolean isBlocked, long timeBlocked) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.isBlocked = isBlocked;
        this.timeBlocked = timeBlocked;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public long getTimeBlocked() {
        return timeBlocked;
    }

    public void setTimeBlocked(long timeBlocked) {
        this.timeBlocked = timeBlocked;
    }
}
