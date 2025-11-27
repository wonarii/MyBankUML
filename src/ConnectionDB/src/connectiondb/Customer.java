//package ConnectionDB.src.connectiondb;

import java.util.Date;

public class Customer extends User {
    private String phone;
    private String birthday;
    private double balance;
    final String ROLE = "user";

    // Constructor
    public Customer(String firstName, String lastName, String email, BankBranch branch, Bank bank, String password, String phone, String birthday, double balance) {
        super(firstName, lastName, email, branch, bank, password);
        this.phone = phone;
        this.birthday = birthday;
        this.balance = balance;
    }

    // Constructor without password
    public Customer(String firstName, String lastName, String email, int accountId, BankBranch branch, Bank bank, double balance) {
        super(firstName, lastName, email, accountId, branch, bank);
        this.balance = balance;
    }

    // Getters
    public String getPhone() {
        return this.phone;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public double getBalance() {
        return this.balance;
    }

    // Setters
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    // When the deposit button is pressed, this function is called
    public void deposit (double amount) {
        this.balance = this.balance + amount;
        
        // Update in database?
    }

    // When the withdraw button is pressed, this function is called
    public void withdraw (double amount) {
        this.balance = this.balance - amount;
        // Update in database?
    }

    /***
     * This function will get the balance from the database
     * IMPORTANT: This function does not modify/update the User object
     * @return
     */
    public static Double getBalanceFromDatabase(){
        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Authenticator auth = Authenticator.getAuthenticatorInstance();
            double balance;
            if (auth.getCurrentUser() != null) {
                balance = (double) db.getBalance((String) auth.getCurrentUser().get("user_email"));
            } else {
                balance = 0;
            }
            return balance;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Object[] display(){
        Object[] obj = new Object[7];
        obj[0] = this.getAccountId();
        obj[1] = this.getEmail();
        obj[2] = this.getFirstName();
        obj[3] = this.getLastName();
        obj[4] = this.getBank().getBankName();
        obj[5] = this.getBranch().getBranchName();
        obj[6] = this.getBalance();
        return obj;
    }
}
