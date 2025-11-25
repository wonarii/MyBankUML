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

    public void signUp(String firstName, String lastName, String email, String bank, String phone, String birthday, String password, int branch) {
        try {
            // Get db connection
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            // Create Customer object
            Customer customer = new Customer(firstName, lastName, email, branch, bank, password, phone, birthday, 0.0);

            // Add customer object to database
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

    // Validate a user's credentials on login
    public boolean validateCredentials(String email, String password) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            if (db.loadUserData(email, password)) {
                setCurrentUser(db.getUserMap());
            }
             return db.loadUserData(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
