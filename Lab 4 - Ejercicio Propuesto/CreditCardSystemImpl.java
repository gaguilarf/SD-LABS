package Lab4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CreditCardSystemImpl extends UnicastRemoteObject implements CreditCardSystem {
    private Map<String, CreditCard> cards;

    public CreditCardSystemImpl() throws RemoteException {
        super();
        cards = new HashMap<>();
    }

    @Override
    public void createCard(String cardNumber, String cardHolder, double creditLimit) throws RemoteException {
        cards.put(cardNumber, new CreditCard(cardNumber, cardHolder, creditLimit));
    }

    @Override
    public double getBalance(String cardNumber) throws RemoteException {
        CreditCard card = cards.get(cardNumber);
        return card != null ? card.getBalance() : 0.0;
    }

    @Override
    public boolean charge(String cardNumber, double amount) throws RemoteException {
        CreditCard card = cards.get(cardNumber);
        if (card != null) {
            return card.charge(amount);
        }
        return false;
    }

    @Override
    public boolean makePayment(String cardNumber, double amount) throws RemoteException {
        CreditCard card = cards.get(cardNumber);
        if (card != null) {
            return card.makePayment(amount);
        }
        return false;
    }
}

class CreditCard {
    private String cardNumber;
    private String cardHolder;
    private double creditLimit;
    private double balance;

    public CreditCard(String cardNumber, String cardHolder, double creditLimit) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.creditLimit = creditLimit;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public boolean charge(double amount) {
        if (balance + amount <= creditLimit) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean makePayment(double amount) {
        if (amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

