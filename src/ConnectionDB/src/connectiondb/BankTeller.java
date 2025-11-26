package ConnectionDB.src.connectiondb;
public class BankTeller extends User {
    final String ROLE = "teller";
    public BankTeller(String firstName, String lastName, String email, BankBranch branch, Bank bank, String password) {
        super(firstName, lastName, email, branch, bank, password);
    }

    // Issue: need a way to search by ID in the database.
    public void seachById(int id) {

    }

    // Issue: need a way to search by branch in the database/
    public void searchByBranch(String branch) {

    }
}
