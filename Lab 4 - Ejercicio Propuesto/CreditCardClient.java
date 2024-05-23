package Lab4;

import java.rmi.Naming;

public class CreditCardClient {
    public static void main(String[] args) {
        try {
            CreditCardSystem creditCardSystem = (CreditCardSystem) Naming.lookup("rmi://localhost/CreditCardSystem");

            creditCardSystem.createCard("1234-5678-9012-3456", "John Doe", 5000.0);
            System.out.println("Card created for John Doe with limit $5000.0");

            creditCardSystem.charge("1234-5678-9012-3456", 1500.0);
            System.out.println("Charged $1500.0");

            double balance = creditCardSystem.getBalance("1234-5678-9012-3456");
            System.out.println("Current balance: $" + balance);

            creditCardSystem.makePayment("1234-5678-9012-3456", 500.0);
            System.out.println("Payment of $500.0 made");

            balance = creditCardSystem.getBalance("1234-5678-9012-3456");
            System.out.println("Current balance: $" + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

