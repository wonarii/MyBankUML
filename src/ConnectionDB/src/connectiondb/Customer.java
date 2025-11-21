public class Customer extends User {
    private String phone;
    private String birthday;
    private double balance;
    // Array of transactions?
    public Transaction transactions;

    // Constructor
    public Customer(String firstName, String lastName, String email, int accountId, int branch, String phone, String birthday, double balance) {
        super(firstName, lastName, email, accountId, branch);
        this.phone = phone;
        this.birthday = birthday;
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

    public Transaction getTransactions() {
        return this.transactions;
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
}
