public class BankTeller extends User {

    public BankTeller(String firstName, String lastName, String email, int accountID, int branch) {
        super(firstName, lastName, email, accountID, branch);
    }
    // When the 'create Customer' button is called, this function is invoked.
    public void addCustomer(String firstName, String lastName, String email, String password, String birthday, String phoneNumber, String branch) {
        // Take fields from input and create new Customer object
        // Pass the Customer object
    }

    // When the 'delete Customer' button is called, this function is invoked.
    public void deleteCustomer(String email) {
        ConnectionDB.deleteUser(email);
    }

    public void searchByName(String name){
        ConnectionDB.searchUsers(name);
    }

    // Issue: need a way to search by ID in the database.
    public void seachById(int id) {

    }

    // Issue: need a way to search by branch in the database/
    public void searchByBranch(String branch) {

    }
}
