import org.mindrot.jbcrypt.BCrypt;

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

    public void signUp(String firstName, String lastName, String email, int branch, String phone, String birthday, double balance, String password) {
        try {
            // Get db connection
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            // Hash password
            String hashedPassword = createPassword(password);

            // Create new customer with hashed password
            Customer customer = new Customer(firstName, lastName, email, branch, phone, birthday, balance, hashedPassword);
            db.createCustomer(customer);
            System.out.println("Customer signed up successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBankTeller(BankTeller bankTeller) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            db.createBankTeller(bankTeller);
            System.out.println("Bank teller created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Take raw password and hash it before putting it into the database
    private String createPassword(String password) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            String hashedPassword = db.hashPassword(password);
            return hashedPassword;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
