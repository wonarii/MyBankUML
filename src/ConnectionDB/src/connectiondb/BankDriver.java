import connectiondb.ConnectionDB;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class BankDriver
{

    public static void main(String[] args) {

        try {
            // --- Create new sample users ---
            ConnectionDB.createUser(
                    "Charlie", "Brown", "charlie.brown@gmail.com", "Charlie123!", 1200.0, "user", 1, 2, "1995-07-20", "100 Elm Street, Toronto"
            );

            ConnectionDB.createUser(
                    "Diana", "Prince", "diana.prince@gmail.com", "Wonder123!", 1500.0, "teller", 2, 5, "1988-03-10", "200 Queen Street, Toronto"
            );

            ConnectionDB.createUser(
                    "Ethan", "Hunt", "ethan.hunt@gmail.com", "Mission123!", 3000.0, "admin", 3, 1, "1980-11-12", "300 King Street, Toronto"
            );

            // --- Test login with Charlie ---
            if (ConnectionDB.loadUserData("charlie.brown@gmail.com", "Charlie123!")) {
                System.out.println("Welcome, " + ConnectionDB.getUserData("user_first_name"));
                System.out.println("--------------------------------------------");
                System.out.println("User session data:");
                System.out.println("ID: " + ConnectionDB.getUserData("id"));
                System.out.println("First Name: " + ConnectionDB.getUserData("user_first_name"));
                System.out.println("Last Name: " + ConnectionDB.getUserData("user_last_name"));
                System.out.println("Email: " + ConnectionDB.getUserData("user_email"));
                System.out.println("Birthday: " + ConnectionDB.getUserData("user_birthday"));
                System.out.println("Address: " + ConnectionDB.getUserData("user_address"));
                System.out.println("Balance: " + ConnectionDB.getUserData("user_balance"));
                System.out.println("Role: " + ConnectionDB.getUserData("user_role"));
                System.out.println("Bank: " + ConnectionDB.getUserData("user_bank"));
                System.out.println("Bank ID: " + ConnectionDB.getUserData("user_bank_id"));
                System.out.println("Branch: " + ConnectionDB.getUserData("user_branch"));
                System.out.println("Branch ID: " + ConnectionDB.getUserData("user_branch_id"));
            } else {
                System.out.println("Invalid credentials!");
            }

            // --- Test deposit and withdraw ---
            ConnectionDB.updateBalance("charlie.brown@gmail.com", "deposit", 500.0);
            ConnectionDB.updateBalance("charlie.brown@gmail.com", "withdraw", 200.0);

            // --- Delete Diana to test deleteUser ---
            ConnectionDB.deleteUser("diana.prince@gmail.com");

            // --- Load all users for admin search ---
            List<Map<String, Object>> allUsers = ConnectionDB.loadAllUsers();
            System.out.println("All users in system:");
            for (Map<String, Object> u : allUsers) {
                System.out.println(u.get("first_name") + " " + u.get("last_name") + " (" + u.get("email") + ")");
            }

            // --- Load Charlie's transactions for user search ---
            int charlieId = (int) ConnectionDB.getUserData("id");
            List<Map<String, Object>> transactions = ConnectionDB.loadTransactions(charlieId);
            System.out.println("Charlie's transactions:");
            for (Map<String, Object> t : transactions) {
                System.out.println(t.get("type") + ": $" + t.get("amount"));
            }

            // --- Admin search example ---
            List<Map<String, Object>> searchResults = ConnectionDB.searchUsers("Charlie");
            System.out.println("Search results for 'Charlie':");
            for (Map<String, Object> u : searchResults) {
                System.out.println(u.get("first_name") + " " + u.get("last_name") + " (" + u.get("email") + ")");
            }

            // --- User transaction search example ---
            List<Map<String, Object>> filteredTxns = ConnectionDB.searchTransactions(charlieId, "deposit", 100.0, null);
            System.out.println("Filtered deposits over $100 for Charlie:");
            for (Map<String, Object> t : filteredTxns) {
                System.out.println(t.get("type") + ": $" + t.get("amount"));
            }

        } catch (Exception e){

        }


    }
}
