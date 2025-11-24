import javax.swing.*;
import java.sql.SQLException;

public class Authenticator {

    private static Authenticator authenticatorInstance;

    private Authenticator() {

    }

    // Make the Authenticator a singleton so that only one instance exists
    public static Authenticator getAuthenticatorInstance() {
        if (authenticatorInstance == null) {
            authenticatorInstance = new Authenticator();
        }
        return authenticatorInstance;
    }

    private int changePassword(ConnectionDB db){

        return 0;
    }

    public void signUp(Customer customer) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            db.createCustomer(customer);
            System.out.println("Customer signed up successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
