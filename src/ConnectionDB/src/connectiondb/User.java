public class User {
    private String firstName;
    private String lastName;
    private String email;
    //private int accountId;
    private int branch;

    // Constructor
    public User(String firstName, String lastName, String email, int branch) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.branch = branch;
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

    public int getBranch() {
        return this.branch;
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

    public void setBranch(int branch) {
        this.branch = branch;
    }
}
