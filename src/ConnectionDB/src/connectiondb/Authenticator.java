//package ConnectionDB.src.connectiondb;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class Authenticator {

    private static Authenticator authenticatorInstance;
    private Map<String, Object> currentUser;

    private Authenticator() {

    }

    // Make the Authenticator a singleton so that only one instance exists
    public static Authenticator getAuthenticatorInstance() {
        if (authenticatorInstance == null) {
            authenticatorInstance = new Authenticator();
        }
        return authenticatorInstance;
    }

    public Map<String, Object> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Map<String, Object> currentUser) {
        this.currentUser = currentUser;
    }

    private int changePassword(ConnectionDB db){

        return 0;
    }

    public int signUp(String firstName, String lastName, String email, Bank bank, String phone, String birthday, String password, BankBranch branch) {
        try {
            // Get db connection
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            // Create Customer object
            Customer customer = new Customer(firstName, lastName, email, branch, bank, password, phone, birthday, 0.0);

            // Add customer object to database
            int status = db.createCustomer(customer);
            if (status == 0) {
                System.out.println("Customer signed up successfully");
            } else {
                System.out.println("Customer signed up failed");
            }

            return status;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int createBankTeller(BankTeller bankTeller) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            int status = db.createBankTeller(bankTeller);

            if (status == 0) {
                System.out.println("BankTeller account creation successfully");
            } else {
                System.out.println("BankTeller account creation failed");
            }
            return status;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
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

    // Validate a user's credentials on login
    public boolean validateCredentials(String email, String password) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            if (db.loadUserData(email, password)) {
                setCurrentUser(db.getUserMap());
            }
             return db.loadUserData(email, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
