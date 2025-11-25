package ConnectionDB.src.connectiondb;
public class BankTeller extends User {

    public BankTeller(String firstName, String lastName, String email, int branch) {
        super(firstName, lastName, email, branch);
    }

    // Issue: need a way to search by ID in the database.
    public void seachById(int id) {

    }

    // Issue: need a way to search by branch in the database/
    public void searchByBranch(String branch) {

    }
}
