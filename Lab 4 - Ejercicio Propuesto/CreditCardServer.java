package Lab4;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CreditCardServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            CreditCardSystemImpl creditCardSystem = new CreditCardSystemImpl();
            Naming.rebind("rmi://localhost/CreditCardSystem", creditCardSystem);
            System.out.println("Credit Card System is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

