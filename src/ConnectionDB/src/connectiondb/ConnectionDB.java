// package connectiondb;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;

public class ConnectionDB {

    // Session storage
    private static final Map<String, Object> userData = new HashMap<>();
    // ConnectionDB singleton instance
    private static ConnectionDB databaseInstance;

    /***
     * Private constructor prevents multiple ConnectionDB objects
     * @throws SQLException
     */
    private ConnectionDB() throws SQLException { }

    /***
     * Get the singleton instance of ConnectionDB
     * @return ConnectionDB instance
     * @throws SQLException
     */
    public static ConnectionDB getDatabaseInstance() throws SQLException {
        if (databaseInstance == null) {
            databaseInstance = new ConnectionDB();
        }
        return databaseInstance;
    }

    public static void main(String[] args) {
        try {
            ConnectionDB connectionDB = ConnectionDB.getDatabaseInstance();

            // --- Create sample users (new people) ---
            // Charlie = regular user -> default balance 0.0 (if null passed)
            connectionDB.createUser(
                    "Charlie", "Brown", "charlie.brown@gmail.com", "Charlie123!",
                    null,               // balance (null means default for regular user -> becomes 0.0)
                    "user",             // role
                    1,                  // bankId
                    2,                  // branchId
                    "1995-07-20",       // birthday (yyyy-MM-dd)
                    "100 Elm Street, Toronto"
            );

            // Diana = teller -> balance must be NULL
            connectionDB.createUser(
                    "Diana", "Prince", "diana.prince@gmail.com", "Wonder123!",
                    null,               // ignored for tellers -> will be inserted as SQL NULL
                    "teller",
                    2,
                    5,
                    "1988-03-10",
                    "200 Queen Street, Toronto"
            );

            // Ethan = admin -> balance must be NULL
            connectionDB.createUser(
                    "Ethan", "Hunt", "ethan.hunt@gmail.com", "Mission123!",
                    null,
                    "admin",
                    3,
                    1,
                    "1980-11-12",
                    "300 King Street, Toronto"
            );

            // --- Test login with Charlie ---
            if (connectionDB.loadUserData("charlie.brown@gmail.com", "Charlie123!")) {
                System.out.println("Welcome, " + connectionDB.getUserData("user_first_name"));
                System.out.println("--------------------------------------------");
                System.out.println("User session data:");
                System.out.println("ID: " + connectionDB.getUserData("id"));
                System.out.println("First Name: " + connectionDB.getUserData("user_first_name"));
                System.out.println("Last Name: " + connectionDB.getUserData("user_last_name"));
                System.out.println("Email: " + connectionDB.getUserData("user_email"));
                System.out.println("Birthday: " + connectionDB.getUserData("user_birthday"));
                System.out.println("Address: " + connectionDB.getUserData("user_address"));
                System.out.println("Balance: " + connectionDB.getUserData("user_balance")); // may be Double or null
                System.out.println("Role: " + connectionDB.getUserData("user_role"));
                System.out.println("Bank: " + connectionDB.getUserData("user_bank"));
                System.out.println("Bank ID: " + connectionDB.getUserData("user_bank_id"));
                System.out.println("Branch: " + connectionDB.getUserData("user_branch"));
                System.out.println("Branch ID: " + connectionDB.getUserData("user_branch_id"));
            } else {
                System.out.println("Invalid credentials!");
            }

            // --- Test transactions using applyTransaction (actor performs action) ---
            // Charlie deposits to his own account
            connectionDB.applyTransaction(
                    "charlie.brown@gmail.com",
                    500.0,
                    "deposit",
                    "charlie.brown@gmail.com"
            );

            // Charlie withdraws from his own account
            connectionDB.applyTransaction(
                    "charlie.brown@gmail.com",
                    200.0,
                    "withdraw",
                    "charlie.brown@gmail.com"
            );

            // Teller (Diana) deposits into Charlie (allowed)
            connectionDB.applyTransaction(
                    "charlie.brown@gmail.com",
                    300.0,
                    "deposit",
                    "diana.prince@gmail.com"
            );

            // Teller trying to withdraw (should be blocked)
            connectionDB.applyTransaction(
                    "charlie.brown@gmail.com",
                    50.0,
                    "withdraw",
                    "diana.prince@gmail.com"
            );

            // Admin (Ethan) deposits into Charlie (allowed)
            connectionDB.applyTransaction(
                    "charlie.brown@gmail.com",
                    100.0,
                    "deposit",
                    "ethan.hunt@gmail.com"
            );

            // Admin trying to deposit into a teller (blocked: target not USER)
            connectionDB.applyTransaction(
                    "diana.prince@gmail.com",
                    50.0,
                    "deposit",
                    "ethan.hunt@gmail.com"
            );

            // --- Delete Diana to test deleteUser ---
            connectionDB.deleteUser("diana.prince@gmail.com");

            // --- Load all users for admin search ---
            List<Map<String, Object>> allUsers = connectionDB.loadAllUsers();
            System.out.println("All users in system:");
            for (Map<String, Object> u : allUsers) {
                System.out.println(u.get("first_name") + " " + u.get("last_name") + " (" + u.get("email") + ")");
            }

            // --- Load Charlie's transactions for user search ---
            Integer charlieId = (Integer) connectionDB.getUserData("id");
            if (charlieId != null) {
                List<Map<String, Object>> transactions = connectionDB.loadTransactions(charlieId);
                System.out.println("Charlie's transactions:");
                for (Map<String, Object> t : transactions) {
                    System.out.println(
                            t.get("transaction_date") + " | " +
                            t.get("type") + ": $" +
                            t.get("amount") + " - " +
                            t.get("description")
                    );
                }

                // --- User transaction search example ---
                List<Map<String, Object>> filteredTxns =
                        connectionDB.searchTransactions(charlieId, "deposit", 100.0, null);
                System.out.println("Filtered deposits over $100 for Charlie:");
                for (Map<String, Object> t : filteredTxns) {
                    System.out.println(
                            t.get("transaction_date") + " | " +
                            t.get("type") + ": $" +
                            t.get("amount") + " - " +
                            t.get("description")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- Database connection ---
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/bankapp_db";
        String user = "root";
        String dbPassword = "";
        return DriverManager.getConnection(url, user, dbPassword);
    }

    // --- Bank/branch name lookup ---
    public String getBankNameById(int bankId) {
        String query = "SELECT bank_name FROM bank_list WHERE bank_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bankId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("bank_name");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public String getBranchNameById(int branchId) {
        String query = "SELECT branch_name FROM branch_list WHERE branch_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("branch_name");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // --- Role & age validation ---
    private boolean isValidRole(String role) {
        return role != null &&
                (role.equalsIgnoreCase("user")
                        || role.equalsIgnoreCase("teller")
                        || role.equalsIgnoreCase("admin"));
    }

    private boolean isAdult(String birthday) {
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

    // --- Load user and verify password (for login) ---
    public boolean loadUserData(String email, String password) {
        String query = "SELECT * FROM account_list WHERE user_email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("user_password");
                if (storedHash != null && storedHash.startsWith("$2y$")) {
                    storedHash = "$2a$" + storedHash.substring(4);
                }

                if (storedHash == null || !BCrypt.checkpw(password, storedHash)) {
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

                Double balanceObj = (Double) rs.getObject("user_balance");
                userData.put("user_balance", balanceObj);

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
    // balanceParam: nullable Double.
    //  - For role 'user': if null -> 0.0
    //  - For role 'admin'/'teller': always inserted as NULL.
    public boolean createUser(String firstName, String lastName, String email, String plainPassword,
                              Double balanceParam, String role, int bankId, int branchId,
                              String birthday, String address) {

        if (!isValidRole(role)) {
            System.out.println("Invalid role! Must be 'user', 'teller', or 'admin'.");
            return false;
        }
        if (!isAdult(birthday)) {
            System.out.println("User must be at least 18 years old!");
            return false;
        }

        String bankName = getBankNameById(bankId);
        String branchName = getBranchNameById(branchId);
        if (bankName == null || branchName == null) {
            System.out.println("Invalid bank or branch ID!");
            return false;
        }

        String query = "INSERT INTO account_list " +
                "(user_first_name, user_last_name, user_email, user_password, user_balance, user_role, " +
                "user_bank, user_bank_id, user_branch, user_branch_id, user_birthday, user_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            boolean insertNullBalance = role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("teller");
            Double balanceToInsert = null;
            if (!insertNullBalance) {
                balanceToInsert = (balanceParam == null) ? 0.0 : balanceParam;
            }

            String hashedPassword = hashPassword(plainPassword);

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, hashedPassword);

            if (insertNullBalance) stmt.setNull(5, Types.DOUBLE);
            else stmt.setDouble(5, balanceToInsert);

            stmt.setString(6, role);
            stmt.setString(7, bankName);
            stmt.setInt(8, bankId);
            stmt.setString(9, branchName);
            stmt.setInt(10, branchId);
            stmt.setString(11, birthday);
            stmt.setString(12, address);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User created successfully: " + email);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error creating user!");
            e.printStackTrace();
        }
        return false;
    }

    // --- Change password ---
    public boolean changePassword(String email, String newPassword) {
        return updateField(email, "user_password", hashPassword(newPassword));
    }

    // --- Helper: get user id by email ---
    public Integer getUserId(String email) {
        String q = "SELECT id FROM account_list WHERE user_email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(q)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // --- Helper: get balance by email (may return null) ---
    public Double getBalance(String email) {
        String q = "SELECT user_balance FROM account_list WHERE user_email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(q)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Object obj = rs.getObject("user_balance");
                if (obj == null) return null;
                return ((Number) obj).doubleValue();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // --- Create transaction method (inserts date via NOW()) ---
    public boolean createTransaction(int userId, String type, double amount) {
        if (!"deposit".equalsIgnoreCase(type) && !"withdraw".equalsIgnoreCase(type)) {
            System.out.println("Invalid transaction type. Must be 'deposit' or 'withdraw'.");
            return false;
        }

        String q = "INSERT INTO transaction_list " +
                   "(user_id, type, amount, transaction_date) " +
                   "VALUES (?, ?, ?, NOW())";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(q)) {

            stmt.setInt(1, userId);
            stmt.setString(2, type.toLowerCase());
            stmt.setDouble(3, amount);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error creating transaction!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Apply a transaction with role rules:
     * - Target account must be a USER account (no admin/teller targets)
     * - User:
     *      - Can deposit/withdraw on THEIR OWN account only
     * - Teller:
     *      - Can DEPOSIT ONLY
     *      - Target must be a USER account
     * - Admin:
     *      - Can deposit/withdraw for USER accounts only
     */
    public boolean applyTransaction(String targetEmail,
                                    double amount,
                                    String type,
                                    String performerEmail) {

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return false;
        }
        if (!"deposit".equalsIgnoreCase(type) && !"withdraw".equalsIgnoreCase(type)) {
            System.out.println("Invalid transaction type. Must be 'deposit' or 'withdraw'.");
            return false;
        }

        try (Connection conn = getConnection()) {

            // --- Load target user (account that will be changed) ---
            String selTarget = "SELECT id, user_balance, user_role FROM account_list WHERE user_email = ?";
            Integer targetId = null;
            Double targetBalanceObj = null;
            String targetRole = null;

            try (PreparedStatement s = conn.prepareStatement(selTarget)) {
                s.setString(1, targetEmail);
                ResultSet rs = s.executeQuery();
                if (rs.next()) {
                    targetId = rs.getInt("id");
                    Object o = rs.getObject("user_balance");
                    targetBalanceObj = (o == null) ? null : ((Number) o).doubleValue();
                    targetRole = rs.getString("user_role");
                } else {
                    System.out.println("Target user not found: " + targetEmail);
                    return false;
                }
            }

            // Target must be a USER account
            if (!"user".equalsIgnoreCase(targetRole)) {
                System.out.println("Transactions can only be applied to USER accounts (no admin/teller targets).");
                return false;
            }

            // --- Load performer (who initiates the action) ---
            String selPerf = "SELECT id, user_role FROM account_list WHERE user_email = ?";
            Integer performerId = null;
            String performerRole = null;

            try (PreparedStatement s2 = conn.prepareStatement(selPerf)) {
                s2.setString(1, performerEmail);
                ResultSet rs2 = s2.executeQuery();
                if (rs2.next()) {
                    performerId = rs2.getInt("id");
                    performerRole = rs2.getString("user_role");
                } else {
                    System.out.println("Performer not found: " + performerEmail);
                    return false;
                }
            }

            // --- ENFORCE ROLE RULES ---

            // 1) User can only operate on their own account
            if ("user".equalsIgnoreCase(performerRole)) {
                if (!performerEmail.equalsIgnoreCase(targetEmail)) {
                    System.out.println("Users cannot modify another user's account.");
                    return false;
                }
            }

            // 2) Teller: deposit only, and only to user accounts (targetRole already checked)
            if ("teller".equalsIgnoreCase(performerRole)) {
                if ("withdraw".equalsIgnoreCase(type)) {
                    System.out.println("Tellers are not allowed to perform withdrawals.");
                    return false;
                }
                // deposit is allowed if we are here
            }

            // 3) Admin: can deposit/withdraw, but only for USER accounts (already ensured)
            if ("admin".equalsIgnoreCase(performerRole)) {
                // no extra rule beyond target must be 'user'
            }

            // Unknown role?
            if (!"user".equalsIgnoreCase(performerRole)
                    && !"teller".equalsIgnoreCase(performerRole)
                    && !"admin".equalsIgnoreCase(performerRole)) {
                System.out.println("Unknown performer role, transaction denied.");
                return false;
            }

            // --- BALANCE & TRANSACTION LOGIC ---

            if (targetBalanceObj == null && "withdraw".equalsIgnoreCase(type)) {
                System.out.println("Cannot withdraw: target user balance is NULL.");
                return false;
            }

            double currentBalance = (targetBalanceObj == null) ? 0.0 : targetBalanceObj;
            double newBalance = currentBalance;

            if ("deposit".equalsIgnoreCase(type)) {
                newBalance = currentBalance + amount;
            } else { // withdraw
                if (amount > currentBalance) {
                    System.out.println("Insufficient balance!");
                    return false;
                }
                newBalance = currentBalance - amount;
            }

            // Update balance
            String upd = "UPDATE account_list SET user_balance = ? WHERE id = ?";
            try (PreparedStatement up = conn.prepareStatement(upd)) {
                up.setDouble(1, newBalance);
                up.setInt(2, targetId);
                up.executeUpdate();
            }

            // Log transaction
            boolean transOk = createTransaction(
                    targetId,
                    type.toLowerCase(),
                    amount
            );
            if (!transOk) {
                System.out.println("Warning: balance updated but transaction write failed.");
            }

            System.out.println("Transaction successful! New balance: $" + newBalance);
            return true;

        } catch (SQLException e) {
            System.out.println("Transaction error!");
            e.printStackTrace();
            return false;
        }
    }

    // --- Specific setters ---
    public boolean updateFirstName(String email, String newFirstName) {
        return updateField(email, "user_first_name", newFirstName);
    }

    public boolean updateLastName(String email, String newLastName) {
        return updateField(email, "user_last_name", newLastName);
    }

    public boolean updateEmail(String email, String newEmail) {
        return updateField(email, "user_email", newEmail);
    }

    // updateBalanceField accepts nullable Double; if null -> sets SQL NULL
    public boolean updateBalanceField(String email, Double newBalance) {
        String query = "UPDATE account_list SET user_balance = ? WHERE user_email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (newBalance == null) stmt.setNull(1, Types.DOUBLE);
            else stmt.setDouble(1, newBalance);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("user_balance updated for " + email);
                return true;
            } else {
                System.out.println("No user found with email: " + email);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating user_balance!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(String email, String newRole) {
        if (!isValidRole(newRole)) {
            System.out.println("Invalid role!");
            return false;
        }
        // if role changes to teller/admin, set balance to NULL by policy
        boolean ok = updateField(email, "user_role", newRole);
        if (ok && (newRole.equalsIgnoreCase("admin") || newRole.equalsIgnoreCase("teller"))) {
            updateBalanceField(email, null);
        }
        return ok;
    }

    public boolean updateBank(String email, int newBankId) {
        String newBankName = getBankNameById(newBankId);
        if (newBankName == null) {
            System.out.println("Invalid bank id");
            return false;
        }
        boolean nameUpdated = updateField(email, "user_bank", newBankName);
        boolean idUpdated = updateField(email, "user_bank_id", newBankId);
        return nameUpdated && idUpdated;
    }

    public boolean updateBranch(String email, int newBranchId) {
        String newBranchName = getBranchNameById(newBranchId);
        if (newBranchName == null) {
            System.out.println("Invalid branch id");
            return false;
        }
        boolean nameUpdated = updateField(email, "user_branch", newBranchName);
        boolean idUpdated = updateField(email, "user_branch_id", newBranchId);
        return nameUpdated && idUpdated;
    }

    public boolean updateBirthday(String email, String newBirthday) {
        if (!isAdult(newBirthday)) {
            System.out.println("User must be 18+!");
            return false;
        }
        return updateField(email, "user_birthday", newBirthday);
    }

    public boolean updateAddress(String email, String newAddress) {
        return updateField(email, "user_address", newAddress);
    }

    // --- Delete user ---
    public boolean deleteUser(String email) {
        return updateField(email, "DELETE", null);
    }

    // --- Generic update helper (for most fields) ---
    private boolean updateField(String email, String fieldName, Object newValue) {
        String query = fieldName.equals("DELETE")
                ? "DELETE FROM account_list WHERE user_email = ?"
                : "UPDATE account_list SET " + fieldName + " = ? WHERE user_email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (fieldName.equals("DELETE")) {
                stmt.setString(1, email);
            } else {
                if (newValue == null) {
                    if ("user_balance".equals(fieldName)) stmt.setNull(1, Types.DOUBLE);
                    else stmt.setNull(1, Types.VARCHAR);
                } else {
                    stmt.setObject(1, newValue);
                }
                stmt.setString(2, email);
            }

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(
                        (fieldName.equals("DELETE") ? "User deleted: " : fieldName + " updated: ") + email
                );
                return true;
            } else {
                System.out.println("No user found with email: " + email);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating field: " + fieldName);
            e.printStackTrace();
            return false;
        }
    }

    // --- Load all users (for admin search) ---
    public List<Map<String, Object>> loadAllUsers() {
        List<Map<String, Object>> userList = new ArrayList<>();
        String query = "SELECT * FROM account_list";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", rs.getInt("id"));
                userMap.put("first_name", rs.getString("user_first_name"));
                userMap.put("last_name", rs.getString("user_last_name"));
                userMap.put("email", rs.getString("user_email"));
                userMap.put("balance", rs.getObject("user_balance")); // may be null
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

    // --- Load transactions for a user (cached for search) ---
    public List<Map<String, Object>> loadTransactions(int userId) {
        List<Map<String, Object>> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, user_id, type, amount, " +
                       "transaction_description, transaction_date " +
                       "FROM transaction_list WHERE user_id = ? ORDER BY transaction_id DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> txn = new HashMap<>();
                txn.put("transaction_id", rs.getInt("transaction_id"));
                txn.put("user_id", rs.getInt("user_id"));
                txn.put("type", rs.getString("type"));
                txn.put("amount", rs.getDouble("amount"));
                txn.put("description", rs.getString("transaction_description"));
                txn.put("transaction_date", rs.getTimestamp("transaction_date"));
                transactions.add(txn);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return transactions;
    }

    // --- Admin search users by name/email ---
    public List<Map<String, Object>> searchUsers(String keyword) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT * FROM account_list " +
                       "WHERE user_first_name LIKE ? OR user_last_name LIKE ? OR user_email LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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
                userMap.put("balance", rs.getObject("user_balance"));
                userMap.put("role", rs.getString("user_role"));
                userMap.put("bank", rs.getString("user_bank"));
                userMap.put("branch", rs.getString("user_branch"));
                results.add(userMap);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    // --- User search transactions by type and/or amount range ---
    public List<Map<String, Object>> searchTransactions(int userId,
                                                        String type,
                                                        Double minAmount,
                                                        Double maxAmount) {
        List<Map<String, Object>> results = new ArrayList<>();
        StringBuilder queryBuilder =
                new StringBuilder(
                        "SELECT transaction_id, user_id, type, amount, " +
                        "transaction_description, transaction_date " +
                        "FROM transaction_list WHERE user_id = ?"
                );
        if (type != null && !type.isEmpty()) queryBuilder.append(" AND type = ?");
        if (minAmount != null) queryBuilder.append(" AND amount >= ?");
        if (maxAmount != null) queryBuilder.append(" AND amount <= ?");
        queryBuilder.append(" ORDER BY transaction_id DESC");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

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
                txn.put("description", rs.getString("transaction_description"));
                txn.put("transaction_date", rs.getTimestamp("transaction_date"));
                results.add(txn);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    // --- Password hashing ---
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // --- Session accessor & logout ---
    public Object getUserData(String key) { return userData.get(key); }

    public void clearUserData() {
        userData.clear();
        System.out.println("User session cleared.");
    }
}











