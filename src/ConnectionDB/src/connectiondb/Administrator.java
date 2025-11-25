public class Administrator extends BankTeller {

    public Administrator(String firstName, String lastName, String email, int branch) {
        super(firstName, lastName, email, branch);
    }

    // When the 'create Bank Teller' account is pressed, this function is called
    public void addBankTeller(String firstName, String lastName, String email, int branch) {
        BankTeller bankTeller = new BankTeller(firstName, lastName, email, branch);
    }

    public void deleteBankTeller() {

    }

    public void createBankBranch() {

    }

    public void deleteBankBranch() {

    }
}
