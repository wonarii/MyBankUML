//package ConnectionDB.src.connectiondb;
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private int accountId;
    private BankBranch branch;
    private Bank bank;
    private String password;

    // Constructor
    public User(String firstName, String lastName, String email, BankBranch branch, Bank bank, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.branch = branch;
        this.bank = bank;
        this.password = password;
    }

    // Constructor without password field
    public User(String firstName, String lastName, String email, int accountId, BankBranch branch, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountId = accountId;
        this.branch = branch;
        this.bank = bank;
    }

    // Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public BankBranch getBranch() {
        return this.branch;
    }

    public String getPassword() {
        return this.password;
    }

    public Bank getBank() {
        return this.bank;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBranch(BankBranch branch) {
        this.branch = branch;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getAccountId() {
        return accountId;
    }

}
