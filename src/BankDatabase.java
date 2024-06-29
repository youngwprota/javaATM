import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

public class BankDatabase {
    private Map<String, Card> cardData;
    public double cashATM;

    public BankDatabase() {
        cardData = new HashMap<>();
    }

    public Map<String, Card> getCardData() {
        return cardData;
    }

    public double getCashATM() {
        return cashATM;
    }

    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/bankdata.txt"))) {
            String line = reader.readLine();
            cashATM = Double.parseDouble(line);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String cardNumber = parts[0];
                String pinCode = parts[1];
                double balance = Double.parseDouble(parts[2]);
                boolean isBlocked = Boolean.parseBoolean(parts[3]);
                long timeBlocked = Long.parseLong(parts[4]);
                if (isBlocked) {
                    Date date = new Date();
                    if ((date.getTime() - timeBlocked) / (1000 * 60 * 60) >= 24) {
                        isBlocked = false;
                        timeBlocked = 0;
                    }
                }
                cardData.put(cardNumber, new Card(cardNumber, pinCode, balance, isBlocked, timeBlocked));
            }
        } catch (IOException e) {
            System.out.println("Error while loading data: " + e.getMessage());
        }
//        for (String key : cardData.keySet()) {
//            Card value = cardData.get(key);
//            System.out.println(key + " " + value.getCardNumber() + " " + value.getPinCode() + " " + value.getBalance() + " " + value.isBlocked());
//        }
    }

    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/bankdata.txt"))) {
            writer.write(Double.toString(getCashATM()));
            writer.newLine();
            for (Card card : cardData.values()) {
                writer.write(card.getCardNumber() + " " + card.getPinCode() + " " + card.getBalance() + " " + card.isBlocked() + " " + card.getTimeBlocked());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error when saving data: " + e.getMessage());
        }
    }
}
