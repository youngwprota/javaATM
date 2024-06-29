import java.util.InputMismatchException;
import java.util.Scanner;


public class ATM {
    public static void start() {
        BankDatabase bankDatabase = new BankDatabase();
        bankDatabase.loadData();
        Card card;
        boolean enter;
        System.out.println("_______________________________Welcome!_______________________________");
        while (true) {
            try {
                card = CardNumberValidator.isValidCardNumber(bankDatabase.getCardData());
                break;
            } catch (CardException ex) {
                System.out.println(ex.getMessage());
                System.out.println("1) Yes\nClick any other number yo exit");
                try {
                    Scanner sc = new Scanner(System.in);
                    int sw;
                    sw = sc.nextInt();
                    if (sw == 1) {
                        continue;
                    }
                    bankDatabase.saveData();
                    System.exit(0);
                } catch (InputMismatchException e) {
                    System.out.println("Please, enter a number" + e.getMessage());
                }
            }
        }
        int counter = 0;
        while (true) {
            try {
                enter = PinCodeValidator.isValidPinCode(card, counter);
                break;
            } catch (PinException ex) {
                counter += 1;
                System.out.println(ex.getMessage());
                if (counter == 3) {
                    bankDatabase.saveData();
                    System.exit(0);
                }
            }
        }
        if (enter) {
            int sw;
            while (true) {
                try {
                    System.out.print("1) Check your balance\n2) Withdraw\n3) Replenish card balance\n4) Exit\n");
                    Scanner sc1 = new Scanner(System.in);
                    sw = sc1.nextInt();
                    switch (sw) {
                        case 1:
                            checkBalance(card);
                            break;
                        case 2:
                            withdraw(card, bankDatabase);
                            break;
                        case 3:
                            deposite(card, bankDatabase);
                            break;
                        case 4:
                            bankDatabase.saveData();
                            System.exit(0);
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please, enter 1, 2, 3 or 4");
                }
            }
        }
    }

    private static void checkBalance(Card card) {
        System.out.println("Your balance is: " + card.getBalance() + "$");
    }

    private static void withdraw(Card card, BankDatabase bankDatabase) {
        double withdraw;
        System.out.println("Available for withdrawal: " + (Math.min(bankDatabase.getCashATM(), card.getBalance())) + "$");
        System.out.println("How much do you want to withdraw?: ");
        Scanner sc1 = new Scanner(System.in);
        try {
            withdraw = sc1.nextDouble();
            if (withdraw > Math.min(bankDatabase.getCashATM(), card.getBalance())) {
                throw new InputMismatchException("Invalid amount");
            }
            card.setBalance(card.getBalance() - withdraw);
            bankDatabase.cashATM = bankDatabase.cashATM - withdraw;
            System.out.println("Withdraw successful: " + withdraw + "$");

        } catch (InputMismatchException ex) {
            System.out.println("Invalid amount: " + ex.getMessage());
        }
    }

    private static void deposite(Card card, BankDatabase bankDatabase) {
        double deposite;
        System.out.println("How much you want to deposit?: ");
        Scanner sc1 = new Scanner(System.in);
        try {
            deposite = sc1.nextDouble();
            card.setBalance(card.getBalance() + deposite);
            bankDatabase.cashATM = bankDatabase.cashATM + deposite;
            System.out.println("Deposite successful: " + deposite + "$");

        } catch (InputMismatchException ex) {
            System.out.println("Invalid amount: " + ex.getMessage());
        }
    }
}
