import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class PinCodeValidator {

    private static final String PIN_REGEX = "^\\d{4}$";

    public static boolean isValidPinCode(Card card, int counter) throws PinException {
        System.out.println("Enter PIN in format XXXX. You have " + (3 - counter) + " attempts left.");
        Scanner sc1 = new Scanner(System.in);
        String pinCode = sc1.nextLine();
        Pattern pattern = Pattern.compile(PIN_REGEX);
        Matcher matcher = pattern.matcher(pinCode);
        if (counter == 2) {
            Date date = new Date();
            card.setBlocked(true);
            card.setTimeBlocked(date.getTime());
            throw new PinException("Your card has been blocked. The card will be unblocked in " + (24 - (date.getTime() - card.getTimeBlocked()) / (1000 * 60 * 60)) + " hours.");
        }
        if (!matcher.matches()) {
            throw new PinException("Invalid PIN. Try again?");
        }
        if (card.getPinCode().equals(pinCode)) {
            System.out.println("Valid PIN");
            return true;
        }
        // сделать 3 попытки
        else {
            throw new PinException("Wrong PIN. Try again?");
        }
    }
}