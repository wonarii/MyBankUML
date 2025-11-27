//package ConnectionDB.src.connectiondb;
public class BankTeller extends User {
    final String ROLE = "teller";

    public BankTeller(String firstName, String lastName, String email, BankBranch branch, Bank bank, String password) {
        super(firstName, lastName, email, branch, bank, password);
    }

    // Constructor without password
    public BankTeller(String firstName, String lastName, String email, int accountId, BankBranch branch, Bank bank) {
        super(firstName, lastName, email, accountId, branch, bank);
    }

    // Issue: need a way to search by ID in the database.
    public void seachById(int id) {

    }

    // Issue: need a way to search by branch in the database/
    public void searchByBranch(String branch) {

    }

    public Object[] display(){
        Object[] obj = new Object[7];
        obj[0] = this.getAccountId();
        obj[1] = this.getBranch().getBranchId();
        obj[2] = this.getEmail();
        obj[3] = this.getFirstName();
        obj[4] = this.getLastName();
        obj[5] = this.getBank().getBankName();
        obj[6] = this.getBranch().getBranchName();
        return obj;
    }
}
