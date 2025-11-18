package connectiondb;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;

public class ConnectionDB {

    // Session storage
    private static final Map<String, Object> userData = new HashMap<>();

//    public static void main(String[] args) {
//
//        // --- Create new sample users ---
//        createUser(
//            "Charlie", "Brown", "charlie.brown@gmail.com", "Charlie123!", 1200.0, "user", 1, 2, "1995-07-20", "100 Elm Street, Toronto"
//        );
//
//        createUser(
//            "Diana", "Prince", "diana.prince@gmail.com", "Wonder123!", 1500.0, "teller", 2, 5, "1988-03-10", "200 Queen Street, Toronto"
//        );
//
//        createUser(
//            "Ethan", "Hunt", "ethan.hunt@gmail.com", "Mission123!", 3000.0, "admin", 3, 1, "1980-11-12", "300 King Street, Toronto"
//        );
//
//        // --- Test login with Charlie ---
//        if (loadUserData("charlie.brown@gmail.com", "Charlie123!")) {
//            System.out.println("Welcome, " + getUserData("user_first_name"));
//            System.out.println("--------------------------------------------");
//            System.out.println("User session data:");
//            System.out.println("ID: " + getUserData("id"));
//            System.out.println("First Name: " + getUserData("user_first_name"));
//            System.out.println("Last Name: " + getUserData("user_last_name"));
//            System.out.println("Email: " + getUserData("user_email"));
//            System.out.println("Birthday: " + getUserData("user_birthday"));
//            System.out.println("Address: " + getUserData("user_address"));
//            System.out.println("Balance: " + getUserData("user_balance"));
//            System.out.println("Role: " + getUserData("user_role"));
//            System.out.println("Bank: " + getUserData("user_bank"));
//            System.out.println("Bank ID: " + getUserData("user_bank_id"));
//            System.out.println("Branch: " + getUserData("user_branch"));
//            System.out.println("Branch ID: " + getUserData("user_branch_id"));
//        } else {
//            System.out.println("Invalid credentials!");
//        }
//
//        // --- Test deposit and withdraw ---
//        updateBalance("charlie.brown@gmail.com", "deposit", 500.0);
//        updateBalance("charlie.brown@gmail.com", "withdraw", 200.0);
//
//        // --- Delete Diana to test deleteUser ---
//        deleteUser("diana.prince@gmail.com");
//
//        // --- Load all users for admin search ---
//        List<Map<String, Object>> allUsers = loadAllUsers();
//        System.out.println("All users in system:");
//        for (Map<String, Object> u : allUsers) {
//            System.out.println(u.get("first_name") + " " + u.get("last_name") + " (" + u.get("email") + ")");
//        }
//
//        // --- Load Charlie's transactions for user search ---
//        int charlieId = (int) getUserData("id");
//        List<Map<String, Object>> transactions = loadTransactions(charlieId);
//        System.out.println("Charlie's transactions:");
//        for (Map<String, Object> t : transactions) {
//            System.out.println(t.get("type") + ": $" + t.get("amount"));
//        }
//
//        // --- Admin search example ---
//        List<Map<String, Object>> searchResults = searchUsers("Charlie");
//        System.out.println("Search results for 'Charlie':");
//        for (Map<String, Object> u : searchResults) {
//            System.out.println(u.get("first_name") + " " + u.get("last_name") + " (" + u.get("email") + ")");
//        }
//
//        // --- User transaction search example ---
//        List<Map<String, Object>> filteredTxns = searchTransactions(charlieId, "deposit", 100.0, null);
//        System.out.println("Filtered deposits over $100 for Charlie:");
//        for (Map<String, Object> t : filteredTxns) {
//            System.out.println(t.get("type") + ": $" + t.get("amount"));
//        }
//    }

    // --- Database connection ---
    public static Connection getConnection() throws SQLException {
        String dbName = "bankapp_db";
//        String dbName = "phpmyadmin";             // The database on my end was named this, this is used for testing
        String url = "jdbc:mysql://127.0.0.1:3306/" + dbName;
        String user = "root";
        String dbPassword = "";
        return DriverManager.getConnection(url, user, dbPassword);
    }

    // --- Bank/branch name lookup ---
    public static String getBankNameById(int bankId) {
        String query = "SELECT bank_name FROM bank_list WHERE bank_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bankId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("bank_name");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static String getBranchNameById(int branchId) {
        String query = "SELECT branch_name FROM branch_list WHERE branch_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("branch_name");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // --- Role & age validation ---
    private static boolean isValidRole(String role) {
        return role.equalsIgnoreCase("user") || role.equalsIgnoreCase("teller") || role.equalsIgnoreCase("admin");
    }

    private static boolean isAdult(String birthday) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            LocalDate today = LocalDate.now();
            int age = Period.between(birthDate, today).getYears();
            return age >= 18;
        } catch (Exception e) {
            System.out.println("Invalid birthday format! Use yyyy-MM-dd");
            return false;
        }
    }

    // --- Load user and verify password ---
    public static boolean loadUserData(String email, String password) {
        String query = "SELECT * FROM account_list WHERE user_email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("user_password");
                if (storedHash.startsWith("$2y$")) storedHash = "$2a$" + storedHash.substring(4);

                if (!BCrypt.checkpw(password, storedHash)) {
                    System.out.println("Invalid credentials!");
                    return false;
                }

                userData.clear();
                userData.put("id", rs.getInt("id"));
                userData.put("user_first_name", rs.getString("user_first_name"));
                userData.put("user_last_name", rs.getString("user_last_name"));
                userData.put("user_email", rs.getString("user_email"));
                userData.put("user_birthday", rs.getString("user_birthday"));
                userData.put("user_address", rs.getString("user_address"));
                userData.put("user_balance", rs.getDouble("user_balance"));
                userData.put("user_role", rs.getString("user_role"));
                userData.put("user_bank", rs.getString("user_bank"));
                userData.put("user_bank_id", rs.getInt("user_bank_id"));
                userData.put("user_branch", rs.getString("user_branch"));
                userData.put("user_branch_id", rs.getInt("user_branch_id"));

                System.out.println("User verified and session created!");
                return true;
            } else {
                System.out.println("Invalid credentials!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error loading user data!");
            e.printStackTrace();
            return false;
        }
    }

    // --- Create user ---
    public static boolean createUser(String firstName, String lastName, String email, String plainPassword,
                                     double balance, String role, int bankId, int branchId,
                                     String birthday, String address) {

        if (!isValidRole(role)) { System.out.println("Invalid role!"); return false; }
        if (!isAdult(birthday)) { System.out.println("User must be 18+!"); return false; }

        String bankName = getBankNameById(bankId);
        String branchName = getBranchNameById(branchId);
        if (bankName == null || branchName == null) { System.out.println("Invalid bank or branch ID!"); return false; }

        String query = "INSERT INTO account_list (user_first_name, user_last_name, user_email, user_password, " +
                       "user_balance, user_role, user_bank, user_bank_id, user_branch, user_branch_id, user_birthday, user_address) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, hashPassword(plainPassword));
            stmt.setDouble(5, balance);
            stmt.setString(6, role);
            stmt.setString(7, bankName);
            stmt.setInt(8, bankId);
            stmt.setString(9, branchName);
            stmt.setInt(10, branchId);
            stmt.setString(11, birthday);
            stmt.setString(12, address);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) { System.out.println("User created successfully: " + email); return true; }
        } catch (SQLException e) { System.out.println("Error creating user!"); e.printStackTrace(); }
        return false;
    }

    // --- Change password ---
    public static boolean changePassword(String email, String newPassword) {
        return updateField(email, "user_password", hashPassword(newPassword));
    }

    // --- Deposit/withdraw with transaction logging ---
    public static boolean updateBalance(String email, String transactionType, double amount) {
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return false; }
        try (Connection conn = getConnection()) {
            String selectQuery = "SELECT user_balance, id FROM account_list WHERE user_email = ?";
            double currentBalance = 0; int userId = -1;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, email);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) { currentBalance = rs.getDouble("user_balance"); userId = rs.getInt("id"); }
                else { System.out.println("User not found: " + email); return false; }
            }
            double newBalance = transactionType.equalsIgnoreCase("deposit") ? currentBalance + amount : currentBalance - amount;
            if (transactionType.equalsIgnoreCase("withdraw") && amount > currentBalance) {
                System.out.println("Insufficient balance!"); return false;
            }
            String updateQuery = "UPDATE account_list SET user_balance = ? WHERE user_email = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setDouble(1, newBalance);
                updateStmt.setString(2, email);
                updateStmt.executeUpdate();
            }
            String transactionQuery = "INSERT INTO transaction_list (user_id, type, amount) VALUES (?, ?, ?)";
            try (PreparedStatement transStmt = conn.prepareStatement(transactionQuery)) {
                transStmt.setInt(1, userId);
                transStmt.setString(2, transactionType.toLowerCase());
                transStmt.setDouble(3, amount);
                transStmt.executeUpdate();
            }
            System.out.println(transactionType + " of $" + amount + " completed. New balance: $" + newBalance);
            return true;
        } catch (SQLException e) { System.out.println("Error updating balance or recording transaction!"); e.printStackTrace(); return false; }
    }

    // --- Specific setters ---
    public static boolean updateFirstName(String email, String newFirstName) { return updateField(email, "user_first_name", newFirstName); }
    public static boolean updateLastName(String email, String newLastName) { return updateField(email, "user_last_name", newLastName); }
    public static boolean updateEmail(String email, String newEmail) { return updateField(email, "user_email", newEmail); }
    public static boolean updateBalanceField(String email, double newBalance) { return updateField(email, "user_balance", newBalance); }
    public static boolean updateRole(String email, String newRole) {
        if (!isValidRole(newRole)) { System.out.println("Invalid role!"); return false; }
        return updateField(email, "user_role", newRole);
    }
    public static boolean updateBank(String email, int newBankId) {
        String newBankName = getBankNameById(newBankId); if (newBankName == null) return false;
        return updateField(email, "user_bank", newBankName) && updateField(email, "user_bank_id", newBankId);
    }
    public static boolean updateBranch(String email, int newBranchId) {
        String newBranchName = getBranchNameById(newBranchId); if (newBranchName == null) return false;
        return updateField(email, "user_branch", newBranchName) && updateField(email, "user_branch_id", newBranchId);
    }
    public static boolean updateBirthday(String email, String newBirthday) {
        if (!isAdult(newBirthday)) { System.out.println("User must be 18+!"); return false; }
        return updateField(email, "user_birthday", newBirthday);
    }
    public static boolean updateAddress(String email, String newAddress) { return updateField(email, "user_address", newAddress); }

    // --- Delete user ---
    public static boolean deleteUser(String email) { return updateField(email, "DELETE", null); }

    // --- Generic update helper ---
    private static boolean updateField(String email, String fieldName, Object newValue) {
        String query = fieldName.equals("DELETE") ? "DELETE FROM account_list WHERE user_email = ?" 
                                                  : "UPDATE account_list SET " + fieldName + " = ? WHERE user_email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            if (fieldName.equals("DELETE")) stmt.setString(1, email);
            else { stmt.setObject(1, newValue); stmt.setString(2, email); }
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println((fieldName.equals("DELETE") ? "User deleted: " : fieldName + " updated: ") + email);
                return true;
            } else { System.out.println("No user found with email: " + email); return false; }
        } catch (SQLException e) { System.out.println("Error updating field: " + fieldName); e.printStackTrace(); return false; }
    }

    // --- Load all users (admin search) ---
    public static List<Map<String, Object>> loadAllUsers() {
        List<Map<String, Object>> userList = new ArrayList<>();
        String query = "SELECT * FROM account_list";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", rs.getInt("id"));
                userMap.put("first_name", rs.getString("user_first_name"));
                userMap.put("last_name", rs.getString("user_last_name"));
                userMap.put("email", rs.getString("user_email"));
                userMap.put("balance", rs.getDouble("user_balance"));
                userMap.put("role", rs.getString("user_role"));
                userMap.put("bank", rs.getString("user_bank"));
                userMap.put("branch", rs.getString("user_branch"));
                userMap.put("birthday", rs.getString("user_birthday"));
                userMap.put("address", rs.getString("user_address"));
                userList.add(userMap);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return userList;
    }

    // --- Load transactions for a user ---
    public static List<Map<String, Object>> loadTransactions(int userId) {
        List<Map<String, Object>> transactions = new ArrayList<>();
        String query = "SELECT * FROM transaction_list WHERE user_id = ? ORDER BY transaction_id DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> txn = new HashMap<>();
                txn.put("transaction_id", rs.getInt("transaction_id"));
                txn.put("type", rs.getString("type"));
                txn.put("amount", rs.getDouble("amount"));
                transactions.add(txn);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return transactions;
    }

    // --- Admin search users ---
    public static List<Map<String, Object>> searchUsers(String keyword) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT * FROM account_list WHERE user_first_name LIKE ? OR user_last_name LIKE ? OR user_email LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            stmt.setString(3, likeKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", rs.getInt("id"));
                userMap.put("first_name", rs.getString("user_first_name"));
                userMap.put("last_name", rs.getString("user_last_name"));
                userMap.put("email", rs.getString("user_email"));
                userMap.put("balance", rs.getDouble("user_balance"));
                userMap.put("role", rs.getString("user_role"));
                userMap.put("bank", rs.getString("user_bank"));
                userMap.put("branch", rs.getString("user_branch"));
                results.add(userMap);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    // --- User search transactions ---
    public static List<Map<String, Object>> searchTransactions(int userId, String type, Double minAmount, Double maxAmount) {
        List<Map<String, Object>> results = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM transaction_list WHERE user_id = ?");
        if (type != null && !type.isEmpty()) queryBuilder.append(" AND type = ?");
        if (minAmount != null) queryBuilder.append(" AND amount >= ?");
        if (maxAmount != null) queryBuilder.append(" AND amount <= ?");
        queryBuilder.append(" ORDER BY transaction_id DESC");

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
            int index = 1;
            stmt.setInt(index++, userId);
            if (type != null && !type.isEmpty()) stmt.setString(index++, type.toLowerCase());
            if (minAmount != null) stmt.setDouble(index++, minAmount);
            if (maxAmount != null) stmt.setDouble(index++, maxAmount);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> txn = new HashMap<>();
                txn.put("transaction_id", rs.getInt("transaction_id"));
                txn.put("type", rs.getString("type"));
                txn.put("amount", rs.getDouble("amount"));
                results.add(txn);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    // --- Hash password ---
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // --- Access session data ---
    public static Object getUserData(String key) { return userData.get(key); }

    // --- Clear session ---
    public static void clearUserData() { userData.clear(); System.out.println("User session cleared."); }

}








