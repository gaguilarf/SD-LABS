package Lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCardSystem extends Remote {
    void createCard(String cardNumber, String cardHolder, double creditLimit) throws RemoteException;
    double getBalance(String cardNumber) throws RemoteException;
    boolean charge(String cardNumber, double amount) throws RemoteException;
    boolean makePayment(String cardNumber, double amount) throws RemoteException;
}
