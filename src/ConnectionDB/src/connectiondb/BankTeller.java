public class BankTeller extends User {

    public BankTeller(String firstName, String lastName, String email, int branch) {
        super(firstName, lastName, email, branch);
    }

    // When the 'create Customer' button is called, this function is invoked.
    public void addCustomer(String firstName, String lastName, String email, int accountId, int branch, String phone, String birthday, double balance) {
        // Take fields from input and create new Customer object
        Customer customer = new Customer(firstName, lastName, email, branch, phone, birthday, balance);
        // Pass the Customer object
        ConnectionDB.createCustomer(customer);
        System.out.println("Customer added");
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
