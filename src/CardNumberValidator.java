import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.Date;

public class CardNumberValidator {
    private static final String CARD_NUMBER_REGEX = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";

    public static Card isValidCardNumber(Map<String, Card> bankDatabase) throws CardException {
        System.out.println("Enter card number in format XXXX-XXXX-XXXX-XXXX");
        Scanner sc1 = new Scanner(System.in);
        String cardNumber = sc1.nextLine();
        Pattern pattern = Pattern.compile(CARD_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(cardNumber);
        if (!matcher.matches()) {
            throw new CardException("Invalid card number. Try again?");
        }
        if (bankDatabase.containsKey(cardNumber)) {
            Card card = bankDatabase.get(cardNumber);
            Date date = new Date();
            if (card.isBlocked()) {
                throw new CardException("Your card has been blocked. The card will be unblocked in " + (24 - (date.getTime() - card.getTimeBlocked()) / (1000 * 60 * 60)) + " hours. Would you like to try another card?");
            }
            System.out.println("Card number is valid");
            return card;
        } else {
            throw new CardException("No card with this card number. Try again?");
        }
    }
}